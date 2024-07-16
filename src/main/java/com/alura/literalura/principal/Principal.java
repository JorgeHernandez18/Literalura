package com.alura.literalura.principal;

import com.alura.literalura.entity.AutorE;
import com.alura.literalura.entity.Idioma;
import com.alura.literalura.entity.LibroE;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.utils.ConsultaAPI;
import com.alura.literalura.utils.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsultaAPI consultaAPI = new ConsultaAPI();
    private ConvierteDatos convertir = new ConvierteDatos();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    private final String URL_BASE = "https://gutendex.com/books/";

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void mostrarMenu() {

        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    Elija la opción a traves de su número:
                    	1.- Buscar libroE por titulo
                    	2.- Listar libros registrados
                    	3.- Listar autores registrados
                    	4.- Listar autores vivos en un determinado año
                    	5.- Listar libros por idioma
                    	0 - Salir
                    	""";
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroAPI();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando aplicación");
                    break;
                default:
                    System.out.println("Opción invalida");
            }

        }


    }

    private void listarLibrosPorIdioma() {
        System.out.println("Selecciona el idioma que deseas buscar: ");
        String opciones = """
                    1. en - Inglés
                    2. es - Español
                    3. fr - Francés
                    4. pt - Portugués
                    """;
        var seleccion = sc.nextInt();
        sc.nextLine();

        switch (seleccion) {
            case 1 -> mostrarIdioma(Idioma.en);
            case 2 -> mostrarIdioma(Idioma.es);
            case 3 -> mostrarIdioma(Idioma.fr);
            case 4 -> mostrarIdioma(Idioma.pt);
            default -> System.out.println("Opción inválida");
        }
    }

    private void mostrarIdioma(Idioma idioma) {
        List<LibroE> librosPorIdioma = libroRepository.findByIdioma(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en " + idioma.getIdiomaEspanol());
        } else {
            System.out.printf("----- Libros en %s ----- %n", idioma.getIdiomaEspanol());
            librosPorIdioma.forEach(System.out::println);
            System.out.println("-----------------------------");
        }
    }

    private void listarAutoresVivos() {

        System.out.println("Introduce el año para listar los autores vivos:");
        var anio = sc.nextInt();
        sc.nextLine();

        List<AutorE> autoresVivos = autorRepository.findAutoresVivosEnAnio(anio);

        if (autoresVivos.isEmpty()){
            System.out.println("Autores vivos no encontrados en esa fecha");
        } else {
            System.out.println("Autores vivos: ");
            autoresVivos.forEach(System.out::println);
            System.out.println("");
        }
    }

    private void listarAutoresRegistrados() {
        List<AutorE> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados.");
            return;
        }

        System.out.println("----- Autores Registrados -----");
        autores.forEach(System.out::println);
        System.out.println("-------------------------------");
    }

    private void listarLibrosRegistrados() {
        List<LibroE> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
            return;
        }

        System.out.println("----- Libros Registrados -----");
        libros.forEach(System.out::println);
        System.out.println("-------------------------------");
    }

    private void buscarLibroAPI() {
        var libroBuscado = getDatosLibro();
        if(libroBuscado == null){
            System.out.println("Libro no encontrado");
        }

        LibroE libro = new LibroE(libroBuscado);
        Optional<LibroE> libroExiste = libroRepository.findByTitulo(libro.getTitulo());
        if (libroExiste.isPresent()){
            System.out.println("Libro existente");
        }

        Autor autor = libroBuscado.autores().get(0);
        AutorE autorE = new AutorE(autor);
        Optional<AutorE> autorPrevio = autorRepository.findByName(autorE.getName());

        AutorE autorExiste = autorPrevio.orElseGet(() -> autorRepository.save(autorE));
        libro.setAutor(autorExiste);
        libroRepository.save(libro);
        System.out.printf("""
                ---------- Libro ----------
                Título: %s
                Autor: %s
                Idioma: %s
                Descargas: %d
                ---------------------------
                """, libro.getTitulo(), autorE.getName(), libro.getIdioma(), libro.getDescargas());
    }

    private Libro getDatosLibro() {
        System.out.println("Escribir el nombre del libro que desea buscar: ");
        var nombreLibro = sc.nextLine();
        var response = consultaAPI.consultarApi(URL_BASE + "?search=" + nombreLibro);
        var libroBucado = convertir.convertirDatos(response, Datos.class);

        return libroBucado.libros().get(0);
    }

}

package com.alura.literalura.entity;

import com.alura.literalura.model.Libro;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "libros")
public class LibroE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private AutorE autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer descargas;

    public LibroE(){

    }

    public LibroE(Libro libro){
        this.titulo = libro.titulo();
        this.idioma = Idioma.fromString(libro.idiomas().toString().split(",")[0].trim());
        this.descargas = libro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public AutorE getAutor() {
        return autor;
    }

    public void setAutor(AutorE autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        String nombreAutor = Optional.ofNullable(autor).map(AutorE::getName).orElse("Autor desconocido");
        return String.format("""
                ---------- Libro ----------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Numero de Descargas: %d
                ---------------------------
                """, titulo, nombreAutor, idioma, Optional.ofNullable(descargas).orElse(0));
    }
}

package com.alura.literalura.entity;

import com.alura.literalura.model.Autor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "autores")
public class AutorE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LibroE> libros = new ArrayList<>();

    public AutorE(Autor autor) {
        this.name = autor.name();
        this.birthYear = autor.birthYear();
        if (autor.deathYear() != null){
            this.deathYear = autor.deathYear();
        }else {
            this.deathYear = 3000;
        }
    }

    public AutorE() {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<LibroE> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroE> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder librosStr = new StringBuilder();
        librosStr.append("Libros: ");
        for (int i = 0; i < libros.size(); i++) {
            librosStr.append(libros.get(i).getTitulo());
            if (i < libros.size() - 1) {
                librosStr.append(", ");
            }
        }
        return String.format("---------- Autor ----------%nNombre: %s%n%s%nAño de Nacimiento: %d%nAño de Fallecimiento: %d%n---------------------------%n",
                name, librosStr, birthYear, deathYear == null ? "Desconocido" : deathYear);
    }
}

package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(@JsonAlias("title") String titulo,
                    @JsonAlias("authors") List<Autor> autores,
                    @JsonAlias("languages") List<String> idiomas ,
                    @JsonAlias("download_count") Integer descargas) {

    @Override
    public String toString() {
        return "---------- LIBRO -------" +'\''+
                "titulo='" + titulo + '\'' +
                ", autores=" + autores + '\'' +
                ", idiomas=" + idiomas + '\'' +
                ", descargas=" + descargas + '\'' +
                "-------------------------";
    }
}

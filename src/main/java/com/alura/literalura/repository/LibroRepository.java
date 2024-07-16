package com.alura.literalura.repository;

import com.alura.literalura.entity.Idioma;
import com.alura.literalura.entity.LibroE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<LibroE, Long> {
    Optional<LibroE> findByTitulo(String titulo);

    List<LibroE> findByIdioma(Idioma idioma);
}

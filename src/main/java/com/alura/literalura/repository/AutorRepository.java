package com.alura.literalura.repository;

import com.alura.literalura.entity.AutorE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorE, Long> {
    Optional<AutorE> findByName(String nombre);

    @Query("SELECT a FROM AutorE a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear > :year)")
    List<AutorE> findAutoresVivosEnAnio(@Param("year") int year);

}

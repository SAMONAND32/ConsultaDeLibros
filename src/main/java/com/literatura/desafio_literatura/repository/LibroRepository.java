package com.literatura.desafio_literatura.repository;

import com.literatura.desafio_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query(value = "SELECT * FROM libros WHERE :idioma = ANY (idiomas)", nativeQuery = true)
    List<Libro> buscarPorIdioma(String idioma);

    Optional<Libro> findByTituloContainingIgnoreCase(String tituloDelLibro);;
}

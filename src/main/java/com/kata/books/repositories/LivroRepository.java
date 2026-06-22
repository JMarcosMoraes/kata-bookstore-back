package com.kata.books.repositories;

import com.kata.books.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByAssunto_Id(Integer assuntoId);
    boolean existsByAutores_Id(Integer autorId);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores")
    List<Livro> findAllWithAutores();

    @Query("SELECT l FROM Livro l JOIN FETCH l.autores WHERE l.id = :id")
    Optional<Livro> findByIdWithAutores(@Param("id") Integer id);
}
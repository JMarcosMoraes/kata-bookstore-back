package com.kata.books.repositories;

import com.kata.books.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByAssunto_Id(Integer assuntoId);
    boolean existsByAutor_Id(Integer autorId);
}
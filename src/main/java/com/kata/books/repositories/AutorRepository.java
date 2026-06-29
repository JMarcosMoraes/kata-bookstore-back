package com.kata.books.repositories;

import com.kata.books.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
    List<Autor> findAllByOrderByNomeAsc();
}
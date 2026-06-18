package com.kata.books.repositories;

import com.kata.books.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {}
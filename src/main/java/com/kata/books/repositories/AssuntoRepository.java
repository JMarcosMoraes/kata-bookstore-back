package com.kata.books.repositories;

import com.kata.books.domain.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {}
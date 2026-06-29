package com.kata.books.repositories;

import com.kata.books.domain.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {
    List<Assunto> findAllByOrderByDescricaoAsc();
}
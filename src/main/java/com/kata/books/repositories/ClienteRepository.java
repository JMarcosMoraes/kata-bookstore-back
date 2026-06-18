package com.kata.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kata.books.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}

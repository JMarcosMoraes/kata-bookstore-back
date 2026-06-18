package com.kata.books.domain.dtos;

import com.kata.books.domain.Assunto;
import com.kata.books.domain.Autor;

public class AutorDTO {

    private Integer id;
    private String nome;


    public AutorDTO(Autor autor){
        this.id = autor.getId();
        this.nome = autor.getNome();
    }

    public AutorDTO(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
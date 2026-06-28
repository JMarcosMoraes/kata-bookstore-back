package com.kata.books.domain;

import com.kata.books.domain.dtos.AutorDTO;

import javax.persistence.*;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40)
    private String nome;

    public Autor(){}

    public Autor(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Autor getAutor(AutorDTO autorDTO){
        return new Autor(autorDTO.getId(), autorDTO.getNome());
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
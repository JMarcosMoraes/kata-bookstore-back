package com.kata.books.domain;

import com.kata.books.domain.dtos.AssuntoDTO;

import javax.persistence.*;

@Entity
public class Assunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String descricao;

    public Assunto(){}

    public Assunto getAssunto(AssuntoDTO assuntoDTO){
        return new Assunto(assuntoDTO.getId(),  assuntoDTO.getDescricao());
    }

    public Assunto(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
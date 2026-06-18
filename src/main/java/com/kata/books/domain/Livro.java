package com.kata.books.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;

    @ManyToOne
    private Assunto assunto;

    @ManyToMany
    private List<Autor> listAutor;

    private String descricao;
    // getters e setters
}
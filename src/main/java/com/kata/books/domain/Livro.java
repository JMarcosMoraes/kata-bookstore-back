package com.kata.books.domain;

import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.domain.dtos.LivroDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40)
    private String titulo;

    @Column(length = 40)
    private String editora;

    private Integer edicao;

    @Column(length = 40)
    private String anoPublicacao;

    private BigDecimal valor;
    private Integer quantidade;

    @ManyToOne
    private Assunto assunto;

    @ManyToMany
    private List<Autor> autores;

    public Livro() { }

    public Livro(LivroDTO livroDTO){
        this.autores = new ArrayList<>();

        this.id = livroDTO.getId();
        this.titulo = livroDTO.getTitulo();
        this.editora = livroDTO.getEditora();
        this.edicao = livroDTO.getEdicao();
        this.anoPublicacao = livroDTO.getAnoPublicacao();
        this.valor = livroDTO.getValor();
        this.quantidade = livroDTO.getQuantidade();
        this.assunto = new Assunto().getAssunto(livroDTO.getAssunto());
        for (AutorDTO autorDto : livroDTO.getAutores()) {
            this.autores.add(new Autor().getAutor(autorDto));
        }
    }

    public Livro(Integer id, String titulo, String editora, Integer edicao, String anoPublicacao,
                 BigDecimal valor, Integer quantidade, Assunto assunto, List<Autor> listAutor) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.valor = valor;
        this.quantidade = quantidade;
        this.assunto = assunto;
        this.autores = listAutor;
    }

    public Livro getLivro(LivroDTO livroDTO){


        return new Livro(livroDTO);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Integer getEdicao() {
        return edicao;
    }

    public void setEdicao(Integer edicao) {
        this.edicao = edicao;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(String anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> listAutor) {
        this.autores = listAutor;
    }
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
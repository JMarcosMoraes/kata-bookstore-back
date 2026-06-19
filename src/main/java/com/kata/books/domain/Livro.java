package com.kata.books.domain;

import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.domain.dtos.LivroDTO;

import javax.persistence.*;
import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;

    @ManyToOne
    private Assunto assunto;

    @ManyToMany
    private List<Autor> listAutor;

    public Livro() { }

    public Livro(LivroDTO livroDTO){
        this.id = livroDTO.getId();
        this.titulo = livroDTO.getTitulo();
        this.editora = livroDTO.getEditora();
        this.edicao = livroDTO.getEdicao();
        this.anoPublicacao = livroDTO.getAnoPublicacao();
        this.assunto = new Assunto().getAssunto(livroDTO.getAssunto());
        for (AutorDTO autorDto : livroDTO.getAutores()) {
            this.listAutor.add(new Autor().getAutor(autorDto));
        }
    }

    public Livro(Integer id, String titulo, String editora, Integer edicao, String anoPublicacao, Assunto assunto, List<Autor> listAutor) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.assunto = assunto;
        this.listAutor = listAutor;
    }

    public Livro getLivro(LivroDTO livroDTO){
        return new Livro();
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

    public List<Autor> getListAutor() {
        return listAutor;
    }

    public void setListAutor(List<Autor> listAutor) {
        this.listAutor = listAutor;
    }
}
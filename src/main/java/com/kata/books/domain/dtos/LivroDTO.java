package com.kata.books.domain.dtos;

import com.kata.books.domain.Autor;
import com.kata.books.domain.Livro;

import java.util.ArrayList;
import java.util.List;


public class LivroDTO {

    private Integer id;
    private String titulo;
    private String editora;
    private Integer edicao;
    private String anoPublicacao;
    private AssuntoDTO assunto;
    private List<AutorDTO> autores = new ArrayList<>();

    public LivroDTO(Integer id, String titulo, String editora, Integer edicao, String anoPublicacao, AssuntoDTO assuntoDTO, List<AutorDTO> listAutor) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.assunto = assuntoDTO;
        this.autores = listAutor;
    }

    public LivroDTO() {}

    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.editora = livro.getEditora();
        this.edicao = livro.getEdicao();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.assunto = new AssuntoDTO(livro.getAssunto());
        for (Autor autor : livro.getAutores()) {
            this.autores.add(new AutorDTO(autor));
        }

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

    public AssuntoDTO getAssunto() {
        return assunto;
    }

    public void setAssunto(AssuntoDTO assunto) {
        this.assunto = assunto;
    }

    public List<AutorDTO> getAutores() {
        return this.autores;
    }

    public void setListAutor(List<AutorDTO> listAutor) {
        this.autores = listAutor;
    }
}
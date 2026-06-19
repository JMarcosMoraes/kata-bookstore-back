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
    private AssuntoDTO assuntoDTO;
    private List<AutorDTO> listAutor = new ArrayList<>();

    public LivroDTO(Integer id, String titulo, String editora, Integer edicao, String anoPublicacao, AssuntoDTO assuntoDTO, List<AutorDTO> listAutor) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.assuntoDTO = assuntoDTO;
        this.listAutor = listAutor;
    }

    public LivroDTO() {}

    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.editora = livro.getEditora();
        this.edicao = livro.getEdicao();
        this.anoPublicacao = livro.getAnoPublicacao();
        this.assuntoDTO = new AssuntoDTO(livro.getAssunto());
        for (Autor autor : livro.getListAutor()) {
            this.listAutor.add(new AutorDTO(autor));
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

    public AssuntoDTO getAssuntoDTO() {
        return assuntoDTO;
    }

    public void setAssuntoDTO(AssuntoDTO assuntoDTO) {
        this.assuntoDTO = assuntoDTO;
    }

    public List<AutorDTO> getListAutor() {
        return listAutor;
    }

    public void setListAutor(List<AutorDTO> listAutor) {
        this.listAutor = listAutor;
    }
}
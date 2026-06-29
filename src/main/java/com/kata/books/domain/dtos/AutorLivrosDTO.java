package com.kata.books.domain.dtos;

public class AutorLivrosDTO {

    private String autor;
    private String livros;
    private String assuntos;


    public AutorLivrosDTO() {
    }

    public AutorLivrosDTO(String autor, String livros, String assuntos) {
        this.autor = autor;
        this.livros = livros;
        this.assuntos = assuntos;

    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLivros() {
        return livros;
    }

    public void setLivros(String livros) {
        this.livros = livros;
    }

    public String getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(String assuntos) {
        this.assuntos = assuntos;
    }
}

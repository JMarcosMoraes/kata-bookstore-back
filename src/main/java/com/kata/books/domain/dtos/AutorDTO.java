package com.kata.books.domain.dtos;

import com.kata.books.domain.Autor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AutorDTO {

    private Integer id;

    @NotBlank(message = "O nome é obrigatório e não pode estar vazia")
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$",
            message = "O nome deve conter apenas letras, números e espaços")
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
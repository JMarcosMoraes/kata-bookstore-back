package com.kata.books.domain.dtos;

import com.kata.books.domain.Assunto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AssuntoDTO {

    private Integer id;

    @NotBlank(message = "A descrição é obrigatória e não pode estar vazia")
    @Size(max = 20, message = "A descrição deve ter no máximo 20 caracteres")
    @Pattern(regexp ="^[a-zA-ZÀ-ÖØ-öø-ÿ0-9 .,]*$",
            message = "A descrição deve conter apenas letras, números e espaços")
    private String descricao;


    public AssuntoDTO(Assunto assunto){
        this.id = assunto.getId();
        this.descricao = assunto.getDescricao();
    }

    public AssuntoDTO(Integer id, String descricao) {
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
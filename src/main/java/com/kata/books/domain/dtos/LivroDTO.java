package com.kata.books.domain.dtos;

import com.kata.books.domain.Autor;
import com.kata.books.domain.Livro;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class LivroDTO {

    private Integer id;

    @NotBlank(message = "O título é obrigatório e não pode estar vazia")
    @Size(max = 40, message = "O título deve ter no máximo 40 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$",
            message = "O título deve conter apenas letras, números e espaços")
    private String titulo;

    @NotBlank(message = "A editore é obrigatório e não pode estar vazia")
    @Size(max = 40, message = "A editora deve ter no máximo 40 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$",
            message = "A editora deve conter apenas letras, números e espaços")
    private String editora;

    @NotNull(message = "A edição é obrigatória e não pode ser nula")
    @Min(value = 1, message = "A edição deve ser maior ou igual a 1")
    @Max(value = 9999, message = "A edição deve ter no máximo 4 dígitos")
    private Integer edicao;

    @NotBlank(message = "O ano de publicação é obrigatório e não pode estar vazio")
    @Pattern(regexp = "^[0-9]{4}$",
            message = "O ano de publicação deve conter exatamente 4 dígitos numéricos")
    private String anoPublicacao;


    @NotNull(message = "O valor é obrigatório e não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 decimais")
    private BigDecimal valor;

    @NotNull(message = "A quantidade é obrigatória e não pode ser nula")
    @Min(value = 0, message = "A quantidade deve ser maior ou igual a zero")
    @Max(value = 9999, message = "A quantidade deve ter no máximo 4 dígitos")
    private Integer quantidade;

    private AssuntoDTO assunto;
    private List<AutorDTO> autores = new ArrayList<>();

    public LivroDTO(Integer id, String titulo, String editora, Integer edicao, String anoPublicacao,
                    BigDecimal valor, Integer quantidade,
                    AssuntoDTO assuntoDTO, List<AutorDTO> listAutor) {
        this.id = id;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.valor = valor;
        this.quantidade = quantidade;
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
        this.valor = livro.getValor();
        this.quantidade = livro.getQuantidade();
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
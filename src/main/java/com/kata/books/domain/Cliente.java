package com.kata.books.domain;

import com.kata.books.domain.dtos.ClienteDTO;
import com.kata.books.domain.enums.Perfil;

import javax.persistence.Entity;
import java.util.stream.Collectors;

@Entity
public class Cliente extends Pessoa {

	private static final long serialVersionUID = 6688772141873811731L;

	public Cliente() {
		super();
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha) {
		super(id, nome, cpf, email, senha);
		addPerfil(Perfil.CLIENTE);
	}
	
	public Cliente(ClienteDTO obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.perfis = obj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
		this.dataCriacao = obj.getDataCriacao();
		addPerfil(Perfil.CLIENTE);
	}
}

package com.kata.books.services;

import java.util.Arrays;

import com.kata.books.domain.*;
import com.kata.books.domain.dtos.AssuntoDTO;
import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.domain.dtos.LivroDTO;
import com.kata.books.repositories.AssuntoRepository;
import com.kata.books.repositories.AutorRepository;
import com.kata.books.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kata.books.domain.enums.Perfil;
import com.kata.books.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private AssuntoRepository assuntoRepository;

	@Autowired
	private AssuntoService assuntoService;

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private AutorService autorService;

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private LivroService livroService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;


	public void instanciaDB() {
		Tecnico tech1 = new Tecnico(null, "Marcos", "03648469681", "teste@teste.com", encoder.encode("123"));
		tech1.addPerfil(Perfil.ADMIN);
		tech1.addPerfil(Perfil.CLIENTE);

		Cliente cli1 = new Cliente(null, "Linux", "03648469682", "teste@teste", encoder.encode("123"));

		pessoaRepository.saveAll(Arrays.asList(tech1));
		pessoaRepository.saveAll(Arrays.asList(cli1));

		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "550.482.150-95", "valdir@mail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Richard Stallman", "903.347.070-56", "stallman@mail.com", encoder.encode("123"));
		Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannon", "271.068.470-54", "shannon@mail.com", encoder.encode("123"));
		Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "lee@mail.com", encoder.encode("123"));
		Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "linus@mail.com", encoder.encode("123"));

		Cliente cli6 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein@mail.com", encoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Marie Curie", "322.429.140-06", "curie@mail.com", encoder.encode("123"));
		Cliente cli3 = new Cliente(null, "Charles Darwin", "792.043.830-62", "darwin@mail.com",encoder.encode("123"));
		Cliente cli4 = new Cliente(null, "Stephen Hawking", "177.409.680-30", "hawking@mail.com", encoder.encode("123"));
		Cliente cli5 = new Cliente(null, "Max Planck", "081.399.300-83", "planck@mail.com", encoder.encode("123"));

		pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5, cli6));

		assuntoRepository.saveAll(Arrays.asList(new Assunto(null, "Ação"), new Assunto(null, "Suspense"),
				new Assunto(null,"Drama")));

		assuntoService.create(new AssuntoDTO(null, "Corrida"));

		autorRepository.saveAll(Arrays.asList(new Autor(null, "José de Azevedo"), new Autor(null, "Morais João"),
				new Autor(null,"Neuza Maria")));

		autorService.create(new AutorDTO(null, "Silvestre Albuquerque"));

		livroRepository.saveAll(Arrays.asList(new Livro(null, "Os Vencedores","Master",1,"2026",new Assunto(1,"Copa Do Mundo"),null)));
		livroService.create(new LivroDTO(null, "Lei da Atração","Steven",1,"2021",new AssuntoDTO(1,"Ficção Cientifica"),null));
	}

}

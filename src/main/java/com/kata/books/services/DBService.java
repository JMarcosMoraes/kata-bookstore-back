package com.kata.books.services;

import com.kata.books.domain.Assunto;
import com.kata.books.domain.Autor;
import com.kata.books.domain.Cliente;
import com.kata.books.domain.Livro;
import com.kata.books.domain.dtos.AssuntoDTO;
import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.domain.dtos.LivroDTO;
import com.kata.books.repositories.AssuntoRepository;
import com.kata.books.repositories.AutorRepository;
import com.kata.books.repositories.LivroRepository;
import com.kata.books.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

		Cliente cli1 = new Cliente(null, "Linux", "03648469682", "teste@teste", encoder.encode("123"));

		pessoaRepository.saveAll(Arrays.asList(cli1));

		Cliente cli6 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein@mail.com", encoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Marie Curie", "322.429.140-06", "curie@mail.com", encoder.encode("123"));
		Cliente cli3 = new Cliente(null, "Charles Darwin", "792.043.830-62", "darwin@mail.com",encoder.encode("123"));
		Cliente cli4 = new Cliente(null, "Stephen Hawking", "177.409.680-30", "hawking@mail.com", encoder.encode("123"));
		Cliente cli5 = new Cliente(null, "Max Planck", "081.399.300-83", "planck@mail.com", encoder.encode("123"));

		pessoaRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4, cli5, cli6));

		assuntoRepository.saveAll(Arrays.asList(new Assunto(null, "Ação"), new Assunto(null, "Suspense"),
				new Assunto(null,"Drama")));

		assuntoService.create(new AssuntoDTO(null, "Corrida"));

		autorRepository.saveAll(Arrays.asList(new Autor(null, "José de Azevedo"), new Autor(null, "Morais João"),
				new Autor(null,"Neuza Maria")));

		autorService.create(new AutorDTO(null, "Silvestre Albuquerque"));

		List<Autor> listAutor = new ArrayList<>();
		listAutor.add(new Autor(1,"Neuza Maria"));

		List<AutorDTO> listAutorDTO = new ArrayList<>();
		listAutorDTO.add(new AutorDTO(1,"Neuza Maria"));

		livroRepository.saveAll(Arrays.asList(new Livro(null, "Os Vencedores","Master",1,"2026",new Assunto(1,"Copa Do Mundo"),listAutor)));
		livroService.create(new LivroDTO(null, "Lei da Atração","Steven",1,"2021",new AssuntoDTO(1,"Ficção Cientifica"), listAutorDTO));
	}

}

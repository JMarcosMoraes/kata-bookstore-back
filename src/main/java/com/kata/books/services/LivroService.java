package com.kata.books.services;

import com.kata.books.domain.Assunto;
import com.kata.books.domain.Autor;
import com.kata.books.domain.Livro;
import com.kata.books.domain.dtos.AssuntoDTO;
import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.domain.dtos.AutorLivrosDTO;
import com.kata.books.domain.dtos.LivroDTO;
import com.kata.books.repositories.AssuntoRepository;
import com.kata.books.repositories.AutorRepository;
import com.kata.books.repositories.LivroRepository;
import com.kata.books.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class LivroService {

	@Autowired
	private LivroRepository repository;

	@Autowired
	private AssuntoRepository assuntoRepository;

	@Autowired
	private AutorRepository autorRepository;

	public Livro findById(Integer id) {
		Optional<Livro> obj = repository.findByIdWithAutores(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Livro não encontrado! Id: " + id));
	}

	public List<Livro> findAll() {
		return repository.findAllWithAutores();
	}

	public Livro create(LivroDTO objDto) {

		try {


			List<Assunto> assuntos = new ArrayList<>();
			if (objDto.getAssuntos() != null && !objDto.getAssuntos().isEmpty()) {
				for (AssuntoDTO asssuntoDTO : objDto.getAssuntos()) {
					Optional<Assunto> assunto = assuntoRepository.findById(asssuntoDTO.getId());
					if (assunto.isEmpty()) {
						throw new ObjectnotFoundException("Assunto não encontrado! Id: " + asssuntoDTO.getId());
					}
					assuntos.add(assunto.get());
				}
			}

			List<Autor> autores = new ArrayList<>();
			if (objDto.getAutores() != null && !objDto.getAutores().isEmpty()) {
				for (AutorDTO autorDTO : objDto.getAutores()) {
					Optional<Autor> autor = autorRepository.findById(autorDTO.getId());
					if (autor.isEmpty()) {
						throw new ObjectnotFoundException("Autor não encontrado! Id: " + autorDTO.getId());
					}
					autores.add(autor.get());
				}
			}

			Livro livro = new Livro(
					objDto.getId(),
					objDto.getTitulo(),
					objDto.getEditora(),
					objDto.getEdicao(),
					objDto.getAnoPublicacao(),
					objDto.getValor(),
					objDto.getQuantidade(),
					assuntos,
					autores);
			return repository.save(livro);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Livro update(Integer id, @Valid LivroDTO objDto) {
		objDto.setId(id);
		Livro oldObj = findById(id);
		oldObj = oldObj.getLivro(objDto);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Livro obj = findById(id);
		repository.delete(obj);
	}

	public List<AutorLivrosDTO> findAutorbyLivros() {
		List<Object[]> resultados = repository.findAutoresComLivrosAgrupados();

		List<AutorLivrosDTO> returnAutorLivro = resultados.stream()
				.map(obj -> new AutorLivrosDTO(
						(String) obj[0], // autor
						(String) obj[1], // livros concatenados
						(String) obj[2]  // assuntos
				))
				.collect(Collectors.toList());

		return returnAutorLivro;
	}
}
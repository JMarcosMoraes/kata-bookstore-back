package com.kata.books.services;

import com.kata.books.domain.Autor;
import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.repositories.AutorRepository;
import com.kata.books.repositories.LivroRepository;
import com.kata.books.services.exceptions.DataIntegrityViolationException;
import com.kata.books.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

	@Autowired
	private AutorRepository repository;

	@Autowired
	private LivroRepository livroRepository;

	public Autor findById(Integer id) {
		Optional<Autor> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Autor não encontrado! Id: " + id));
	}

	public List<Autor> findAll() {
		return repository.findAll();
	}

	public Autor create(AutorDTO objDto) {
		objDto.setId(null);
		Autor newObj = new Autor(objDto.getId(),objDto.getNome());
		return repository.save(newObj);
	}

	public Autor update(Integer id, @Valid AutorDTO objDto) {
		objDto.setId(id);
		Autor oldObj = findById(id);
		oldObj = oldObj.getAutor(objDto);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Autor obj = findById(id);

		boolean existeLivro = livroRepository.existsByListAutor_Id(id);
		if (existeLivro) {
			throw new DataIntegrityViolationException("Não é possível excluir: existem livros vinculados a este Autor! Id: " + id);
		}
		repository.delete(obj);
	}
}
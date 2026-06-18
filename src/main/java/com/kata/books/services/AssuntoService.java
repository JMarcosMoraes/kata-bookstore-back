package com.kata.books.services;

import com.kata.books.domain.Assunto;
import com.kata.books.domain.Cliente;
import com.kata.books.domain.Pessoa;
import com.kata.books.domain.dtos.AssuntoDTO;
import com.kata.books.domain.dtos.ClienteDTO;
import com.kata.books.repositories.AssuntoRepository;
import com.kata.books.repositories.ClienteRepository;
import com.kata.books.repositories.PessoaRepository;
import com.kata.books.services.exceptions.DataIntegrityViolationException;
import com.kata.books.services.exceptions.ObjectnotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class AssuntoService {

	@Autowired
	private AssuntoRepository repository;

	public Assunto findById(Integer id) {
		Optional<Assunto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Assunto não encontrado! Id: " + id));
	}

	public List<Assunto> findAll() {
		return repository.findAll();
	}

	public Assunto create(AssuntoDTO objDto) {
		objDto.setId(null);
		Assunto newObj = new Assunto(objDto.getId(),objDto.getDescricao());
		return repository.save(newObj);
	}

	public Assunto update(Integer id, @Valid AssuntoDTO objDto) {
		objDto.setId(id);
		Assunto oldObj = findById(id);
		oldObj = oldObj.getAssunto(objDto);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Assunto obj = findById(id);
		repository.delete(obj);
	}
}
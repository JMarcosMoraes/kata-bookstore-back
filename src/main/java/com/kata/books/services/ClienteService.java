package com.kata.books.services;

import com.kata.books.domain.Cliente;
import com.kata.books.domain.Pessoa;
import com.kata.books.domain.dtos.ClienteDTO;
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
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDto) {
		objDto.setId(null);
		validarPorCPfEmail(objDto);
		Cliente newObj = new Cliente(objDto);
		return repository.save(newObj);
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDto) {
		objDto.setId(id);
		Cliente oldObj = findById(id);
		validarPorCPfEmail(objDto);
		oldObj = new Cliente(objDto);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		repository.delete(obj);
	}

	private void validarPorCPfEmail(ClienteDTO objDto) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF Já cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDto.getEmail());

		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("E-mail Já cadastrado no sistema!");
		}

	}

}

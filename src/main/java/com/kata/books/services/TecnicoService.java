package com.kata.books.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kata.books.domain.Pessoa;
import com.kata.books.domain.Tecnico;
import com.kata.books.domain.dtos.TecnicoDTO;
import com.kata.books.repositories.PessoaRepository;
import com.kata.books.repositories.TecnicoRepository;
import com.kata.books.services.exceptions.DataIntegrityViolationException;
import com.kata.books.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		validarPorCPfEmail(objDto);
		Tecnico newObj = new Tecnico(objDto);
		return repository.save(newObj);
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		objDto.setId(id);
		Tecnico oldObj = findById(id);
		if(!objDto.getSenha().equals(oldObj.getSenha())) {
			objDto.setSenha(encoder.encode(objDto.getSenha()));
		}
		validarPorCPfEmail(objDto);
		oldObj = new Tecnico(objDto);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		repository.delete(obj);
	}

	private void validarPorCPfEmail(TecnicoDTO objDto) {
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

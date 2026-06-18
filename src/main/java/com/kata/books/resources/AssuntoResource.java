package com.kata.books.resources;

import com.kata.books.domain.Assunto;
import com.kata.books.domain.dtos.AssuntoDTO;
import com.kata.books.services.AssuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/assuntos")
public class AssuntoResource {

	@Autowired
	private AssuntoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Assunto> findById(@PathVariable Integer id) {
		Assunto obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<AssuntoDTO>> findAll(){
		List<Assunto> list = service.findAll();
		List<AssuntoDTO> listDto = list.stream().map(obj -> new AssuntoDTO(obj)).collect(Collectors.toList());
		return (ResponseEntity.ok().body(listDto));
	}
	
	@PostMapping
	public ResponseEntity<AssuntoDTO> create(@Valid @RequestBody AssuntoDTO objDto ){
		Assunto newObj = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AssuntoDTO> update(@PathVariable Integer id, @Valid @RequestBody AssuntoDTO objDto){

		Assunto obj = service.update(id, objDto);
		return ResponseEntity.ok().body(new AssuntoDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AssuntoDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

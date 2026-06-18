package com.kata.books.resources;

import com.kata.books.domain.Autor;
import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/autores")
public class AutorResource {

	@Autowired
	private AutorService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Autor> findById(@PathVariable Integer id) {
		Autor obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<AutorDTO>> findAll(){
		List<Autor> list = service.findAll();
		List<AutorDTO> listDto = list.stream().map(obj -> new AutorDTO(obj)).collect(Collectors.toList());
		return (ResponseEntity.ok().body(listDto));
	}
	
	@PostMapping
	public ResponseEntity<AutorDTO> create(@Valid @RequestBody AutorDTO objDto ){
		Autor newObj = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AutorDTO> update(@PathVariable Integer id, @Valid @RequestBody AutorDTO objDto){
		Autor obj = service.update(id, objDto);
		return ResponseEntity.ok().body(new AutorDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AutorDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

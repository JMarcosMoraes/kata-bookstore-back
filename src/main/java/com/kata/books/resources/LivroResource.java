package com.kata.books.resources;

import com.kata.books.domain.Livro;
import com.kata.books.domain.dtos.LivroDTO;
import com.kata.books.services.LivroPDFService;
import com.kata.books.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/livros")
public class LivroResource {

	@Autowired
	private LivroService service;

	@Autowired
	private LivroPDFService pdfService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Livro> findById(@PathVariable Integer id) {
		Livro obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<LivroDTO>> findAll(){
		List<Livro> list = service.findAll();
		List<LivroDTO> listDto = list.stream().map(obj -> new LivroDTO(obj)).collect(Collectors.toList());
		return (ResponseEntity.ok().body(listDto));
	}
	
	@PostMapping
	public ResponseEntity<LivroDTO> create(@Valid @RequestBody LivroDTO objDto ){
		Livro newObj = service.create(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<LivroDTO> update(@PathVariable Integer id, @Valid @RequestBody LivroDTO objDto){
		Livro obj = service.update(id, objDto);
		return ResponseEntity.ok().body(new LivroDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<LivroDTO> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/relatorio/pdf")
	public ResponseEntity<byte[]> gerarRelatorioPDF(){
		try {
			byte[] pdf = pdfService.gerarRelatorioPDF();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "relatorio_livros.pdf");
			headers.setContentLength(pdf.length);

			return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}

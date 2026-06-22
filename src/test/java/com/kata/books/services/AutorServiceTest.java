package com.kata.books.services;

import com.kata.books.domain.Autor;
import com.kata.books.domain.dtos.AutorDTO;
import com.kata.books.repositories.AutorRepository;
import com.kata.books.repositories.LivroRepository;
import com.kata.books.services.exceptions.DataIntegrityViolationException;
import com.kata.books.services.exceptions.ObjectnotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @InjectMocks
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private LivroRepository livroRepository;

    @Test
    void testFindByIdSuccess() {
        Autor autor = new Autor(1, "José");
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        Autor result = autorService.findById(1);

        assertNotNull(result);
        assertEquals("José", result.getNome());
    }

    @Test
    void testFindByIdNotFound() {
        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ObjectnotFoundException.class, () -> autorService.findById(1));
    }

    @Test
    void testFindAll() {
        Autor autor = new Autor(1, "Maria");
        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor));

        List<Autor> result = autorService.findAll();

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getNome());
    }

    @Test
    void testCreate() {
        AutorDTO dto = new AutorDTO(1, "Carlos");
        Autor novo = new Autor(null, "Carlos");

        when(autorRepository.save(any(Autor.class))).thenReturn(novo);

        Autor result = autorService.create(dto);

        assertNotNull(result);
        assertEquals("Carlos", result.getNome());
    }

    @Test
    void testUpdate() {
        Autor autor = new Autor(1, "Antigo");
        AutorDTO dto = new AutorDTO(1, "Novo");

        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        when(autorRepository.save(any(Autor.class))).thenReturn(new Autor(1, "Novo"));

        Autor result = autorService.update(1, dto);

        assertEquals("Novo", result.getNome());
    }

    @Test
    void testDeleteSuccess() {
        Autor autor = new Autor(1, "Pedro");
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        when(livroRepository.existsByAutores_Id(1)).thenReturn(false);

        assertDoesNotThrow(() -> autorService.delete(1));
        verify(autorRepository, times(1)).delete(autor);
    }

    @Test
    void testDeleteWithLinkedBooks() {
        Autor autor = new Autor(1, "Ana");
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        when(livroRepository.existsByAutores_Id(1)).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> autorService.delete(1));
        verify(autorRepository, never()).delete(any());
    }
}

package com.kata.books.services;

import com.kata.books.domain.Assunto;
import com.kata.books.domain.dtos.AssuntoDTO;
import com.kata.books.repositories.AssuntoRepository;
import com.kata.books.repositories.LivroRepository;
import com.kata.books.services.exceptions.DataIntegrityViolationException;
import com.kata.books.services.exceptions.ObjectnotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssuntoServiceTest {

    @InjectMocks
    private AssuntoService assuntoService;

    @Mock
    private AssuntoRepository assuntoRepository;

    @Mock
    private LivroRepository livroRepository;

    private Assunto assunto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assunto = new Assunto(1, "Tecnologia");
    }

    @Test
    void testFindByIdSuccess() {
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));

        Assunto result = assuntoService.findById(1);

        assertNotNull(result);
        assertEquals("Tecnologia", result.getDescricao());
    }

    @Test
    void testFindByIdNotFound() {
        when(assuntoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ObjectnotFoundException.class, () -> assuntoService.findById(1));
    }

    @Test
    void testFindAll() {
        when(assuntoRepository.findAll()).thenReturn(Arrays.asList(assunto));

        List<Assunto> result = assuntoService.findAll();

        assertEquals(1, result.size());
        assertEquals("Tecnologia", result.get(0).getDescricao());
    }

    @Test
    void testCreate() {
        AssuntoDTO dto = new AssuntoDTO(1, "História");
        Assunto novo = new Assunto(null, "História");

        when(assuntoRepository.save(any(Assunto.class))).thenReturn(novo);

        Assunto result = assuntoService.create(dto);

        assertNotNull(result);
        assertEquals("História", result.getDescricao());
    }

    @Test
    void testUpdate() {
        AssuntoDTO dto = new AssuntoDTO(1, "Ciência");
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));
        when(assuntoRepository.save(any(Assunto.class))).thenReturn(new Assunto(1, "Ciência"));

        Assunto result = assuntoService.update(1, dto);

        assertEquals("Ciência", result.getDescricao());
    }

    @Test
    void testDeleteSuccess() {
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.existsByAssunto_Id(1)).thenReturn(false);

        assertDoesNotThrow(() -> assuntoService.delete(1));
        verify(assuntoRepository, times(1)).delete(assunto);
    }

    @Test
    void testDeleteWithLinkedBooks() {
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));
        when(livroRepository.existsByAssunto_Id(1)).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> assuntoService.delete(1));
        verify(assuntoRepository, never()).delete(any());
    }
}

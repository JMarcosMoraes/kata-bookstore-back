package com.kata.books.repositories;

import com.kata.books.domain.Livro;
import com.kata.books.domain.dtos.AutorLivrosDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByAssuntos_Id(Integer assuntoId);
    boolean existsByAutores_Id(Integer autorId);

    @Query("SELECT DISTINCT l FROM Livro l JOIN FETCH l.autores")
    List<Livro> findAllWithAutores();

    @Query("SELECT l FROM Livro l JOIN FETCH l.autores WHERE l.id = :id")
    Optional<Livro> findByIdWithAutores(@Param("id") Integer id);

    @Query(value = "SELECT a.nome AS autor, l.titulo AS livro, " +
            "GROUP_CONCAT(s.descricao SEPARATOR ', ') AS assuntos " +
            "FROM livro l " +
            "JOIN livro_autores la ON l.id = la.livro_id " +
            "JOIN autor a ON a.id = la.autores_id " +
            "JOIN livro_assuntos ls ON l.id = ls.livro_id " +
            "JOIN assunto s ON s.id = ls.assuntos_id " +
            "GROUP BY a.nome, l.titulo " +
            "ORDER BY a.nome, l.titulo", nativeQuery = true)
    List<Object[]> findAutoresComLivrosAgrupados();
}
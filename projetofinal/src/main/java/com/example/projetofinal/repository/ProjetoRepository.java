package com.example.projetofinal.repository;

import com.example.projetofinal.model.Projeto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    List<Projeto> findByAvaliacoesIsNull();


    @Query("SELECT p FROM Projeto p WHERE p.avaliacoes IS EMPTY")
    List<Projeto> findNaoAvaliados();

    @Query("SELECT DISTINCT p FROM Projeto p JOIN p.avaliacoes a")
    List<Projeto> findProjetosAvaliados();

    @EntityGraph(attributePaths = {"autores", "premio", "cronograma", "avaliacoes"})
    List<Projeto> findAll();

    // 1. Listar projetos com autores (usando fetch join para evitar N+1)
    @Query("SELECT DISTINCT p FROM Projeto p LEFT JOIN FETCH p.autores")
    List<Projeto> findAllWithAutores();

    // 3. Listar projetos não avaliados
    @Query("SELECT p FROM Projeto p WHERE SIZE(p.avaliacoes) = 0")
    List<Projeto> findByAvaliacaoIsNull();

    // 4. Listar projetos avaliados com informações de avaliação
    @Query("SELECT p, a.nota, a.parecer, a.dataAvaliacao FROM Projeto p JOIN p.avaliacoes a")
    List<Object[]> findProjetosComAvaliacao();

    // 5. Listar projetos vencedores ordenados por nota
    @Query("SELECT p, a.nota, a.parecer FROM Projeto p JOIN p.avaliacoes a WHERE a.nota >= 7 ORDER BY a.nota DESC")
    List<Object[]> findProjetosVencedoresOrderByNotaDesc();
}

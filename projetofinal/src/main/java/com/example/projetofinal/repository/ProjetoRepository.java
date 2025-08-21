package com.example.projetofinal.repository;

import com.example.projetofinal.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    // Carrega projeto + autores + avaliacoes
    @Query("select p from Projeto p left join fetch p.autores left join fetch p.avaliacoes where p.id = :id")
    Optional<Projeto> findByIdCompleto(Long id);

    // Projetos sem avaliações
    List<Projeto> findByAvaliacoesIsEmpty();

    // Projetos com avaliações
    List<Projeto> findByAvaliacoesIsNotEmpty();

    // Top vencedores (ordem por média desc). Ajuste se quiser N itens.
    @Query("select p from Projeto p order by p.mediaNota desc")
    List<Projeto> findTopByOrderByMediaNotaDesc();

    // Carregar todos com autores (útil para listagem)
    @Query("select distinct p from Projeto p left join fetch p.autores")
    List<Projeto> findAllWithAutores();
}

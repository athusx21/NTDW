package com.example.projetofinal.repository;

import com.example.projetofinal.model.Avaliacao;
import com.example.projetofinal.model.Avaliador;
import com.example.projetofinal.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    boolean existsByAvaliadorAndProjeto(Avaliador avaliador, Projeto projeto);
    boolean existsByAvaliadorIdAndProjetoId(Long avaliadorId, Long projetoId);
}

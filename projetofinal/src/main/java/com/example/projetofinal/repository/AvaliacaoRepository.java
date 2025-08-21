package com.example.projetofinal.repository;

import com.example.projetofinal.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByProjetoId(Long projetoId);
}

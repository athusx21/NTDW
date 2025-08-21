package com.example.projetofinal.services;

import com.example.projetofinal.model.Avaliacao;
import com.example.projetofinal.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public Avaliacao criar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public Optional<Avaliacao> buscarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao atualizar(Long id, Avaliacao atualizado) {
        return avaliacaoRepository.findById(id)
                .map(avaliacao -> {
                    avaliacao.setNota(atualizado.getNota());
                    avaliacao.setParecer(atualizado.getParecer());
                    avaliacao.setDataAvaliacao(atualizado.getDataAvaliacao());
                    return avaliacaoRepository.save(avaliacao);
                })
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));
    }

    public void excluir(Long id) {
        avaliacaoRepository.deleteById(id);
    }

    public long countAvaliacao() {
        return avaliacaoRepository.count();
    }
}

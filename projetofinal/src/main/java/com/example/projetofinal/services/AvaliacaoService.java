package com.example.projetofinal.services;

import com.example.projetofinal.model.Avaliacao;
import com.example.projetofinal.model.Projeto;
import com.example.projetofinal.repository.AvaliacaoRepository;
import com.example.projetofinal.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private ProjetoRepository projetoRepository;

    public Avaliacao criar(Avaliacao avaliacao) {
        Avaliacao saved = avaliacaoRepository.save(avaliacao);
        Projeto projeto = saved.getProjeto();
        projeto.calcularMedia();
        projeto.setAvaliado(true);
        projetoRepository.save(projeto);
        return saved;
    }

    public Optional<Avaliacao> buscarPorId(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Avaliacao atualizar(Long id, Avaliacao atualizado) {
        return avaliacaoRepository.findById(id)
                .map(av -> {
                    av.setNota(atualizado.getNota());
                    av.setParecer(atualizado.getParecer());
                    av.setAvaliador(atualizado.getAvaliador());
                    av.setProjeto(atualizado.getProjeto());
                    return avaliacaoRepository.save(av);
                })
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada."));
    }

    public void excluir(Long id) {
        avaliacaoRepository.deleteById(id);
    }

    public long countAvaliacao() {
        return avaliacaoRepository.count();
    }

    public boolean isAvaliadorAutorDoProjeto(Long avaliadorId, Long projetoId) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        return projeto.getAutores().stream().anyMatch(autor -> autor.getId().equals(avaliadorId));
    }

    public boolean avaliadorJaAvaliouProjeto(Long avaliadorId, Long projetoId) {
        return avaliacaoRepository.existsByAvaliadorIdAndProjetoId(avaliadorId, projetoId);
    }
}

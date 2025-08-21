package com.example.projetofinal.services;

import com.example.projetofinal.model.Avaliador;
import com.example.projetofinal.repository.AvaliadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliadorService {

    @Autowired
    private AvaliadorRepository avaliadorRepository;

    public Avaliador criar(Avaliador avaliador) {
        return avaliadorRepository.save(avaliador);
    }

    public Optional<Avaliador> buscarPorId(Long id) {
        return avaliadorRepository.findById(id);
    }

    public List<Avaliador> listarTodos() {
        return avaliadorRepository.findAll();
    }

    public Avaliador atualizar(Long id, Avaliador atualizado) {
        return avaliadorRepository.findById(id)
                .map(avaliador -> {
                    avaliador.setNome(atualizado.getNome());
                    avaliador.setEspecialidade(atualizado.getEspecialidade());
                    avaliador.setEmail(atualizado.getEmail());
                    avaliador.setTelefone(atualizado.getTelefone());
                    avaliador.setInstituicao(atualizado.getInstituicao());
                    return avaliadorRepository.save(avaliador);
                })
                .orElseThrow(() -> new RuntimeException("Avaliador não encontrado."));
    }

    public void excluir(Long id) {
        avaliadorRepository.deleteById(id);
    }
    public long countAvaliador() {
        return avaliadorRepository.count();
    }
}

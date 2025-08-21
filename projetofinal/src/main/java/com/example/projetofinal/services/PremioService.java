package com.example.projetofinal.services;

import com.example.projetofinal.model.Premio;
import com.example.projetofinal.repository.PremioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PremioService {

    @Autowired
    private PremioRepository premioRepository;

    public List<Premio> listarTodos() {
        return premioRepository.findAll();
    }

    public Optional<Premio> buscarPorId(Long id) {
        return premioRepository.findById(id);
    }

    public Premio criar(Premio premio) {
        return premioRepository.save(premio);
    }

    public void deletar(Long id) {
        if (premioRepository.existsById(id)) {
            premioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Prêmio não encontrado");
        }
    }
    public long countPremios() {
        return premioRepository.count();
    }
}
package com.example.projetofinal.services;

import com.example.projetofinal.model.Projeto;
import com.example.projetofinal.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Projeto Service
@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    public Projeto criar(Projeto projeto) {
        projeto.setDataEnvio(LocalDate.now());
        return projetoRepository.save(projeto);
    }

    public void deletar(Long id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Projeto não encontrado");
        }
    }

    public long countProjetos() {
        return projetoRepository.count();
    }


    // 1. Listar projetos com autores
    public List<Projeto> listarProjetosComAutores() {
        return projetoRepository.findAllWithAutores();
    }

    // 3. Listar projetos não avaliados
    public List<Projeto> listarProjetosNaoAvaliados() {
        return projetoRepository.findByAvaliacaoIsNull();
    }

    // 4. Listar projetos avaliados
    public List<Object[]> listarProjetosAvaliados() {
        return projetoRepository.findProjetosComAvaliacao();
    }

    // 5. Listar projetos vencedores ordenados por nota
    public List<Object[]> listarProjetosVencedores() {
        return projetoRepository.findProjetosVencedoresOrderByNotaDesc();
    }
}
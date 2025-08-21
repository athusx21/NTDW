package com.example.projetofinal.services;

import com.example.projetofinal.model.Autor;
import com.example.projetofinal.model.Projeto;
import com.example.projetofinal.repository.AutorRepository;
import com.example.projetofinal.repository.ProjetoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private AutorRepository autorRepository;

    public List<Projeto> listarTodos() {
        return projetoRepository.findAll();
    }

    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id);
    }

    @Transactional
    public Projeto criar(Projeto projeto, List<Long> autoresIds) {
        projeto.setDataEnvio(LocalDate.now());
        projeto.setAvaliado(false);
        projeto.setMediaNota(0.0);

        if (autoresIds != null && !autoresIds.isEmpty()) {
            Set<Autor> autores = new HashSet<>();
            for (Long autorId : autoresIds) {
                autorRepository.findById(autorId).ifPresent(autores::add);
            }
            projeto.setAutores(autores);
        }

        return projetoRepository.save(projeto);
    }

    public void deletar(Long id) {
        if (projetoRepository.existsById(id)) {
            projetoRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Projeto não encontrado");
        }
    }

    public long countProjetos() {
        return projetoRepository.count();
    }

    @Transactional
    public void atualizarMediaAposAvaliacao(Long projetoId) {
        Projeto projeto = projetoRepository.findByIdCompleto(projetoId)
                .orElseThrow(() -> new NoSuchElementException("Projeto não encontrado"));
        projeto.calcularMedia(); // presume que seta avaliado=true quando houver avaliacoes
        projetoRepository.save(projeto);
    }

    public List<Projeto> listarNaoAvaliados() {
        return projetoRepository.findByAvaliacoesIsEmpty();
    }

    public List<Projeto> listarAvaliados() {
        return projetoRepository.findByAvaliacoesIsNotEmpty();
    }

    public List<Projeto> listarVencedores() {
        return projetoRepository.findTopByOrderByMediaNotaDesc();
    }
}

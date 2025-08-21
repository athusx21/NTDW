package com.example.projetofinal.Controller;

import com.example.projetofinal.dto.AvaliacaoDTO;
import com.example.projetofinal.model.Avaliacao;
import com.example.projetofinal.model.Avaliador;
import com.example.projetofinal.model.Projeto;
import com.example.projetofinal.repository.AvaliadorRepository;
import com.example.projetofinal.repository.ProjetoRepository;
import com.example.projetofinal.services.AvaliacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final AvaliadorRepository avaliadorRepository;
    private final ProjetoRepository projetoRepository;

    public AvaliacaoController(AvaliacaoService avaliacaoService,
                               AvaliadorRepository avaliadorRepository,
                               ProjetoRepository projetoRepository) {
        this.avaliacaoService = avaliacaoService;
        this.avaliadorRepository = avaliadorRepository;
        this.projetoRepository = projetoRepository;
    }

    @GetMapping
    public List<Avaliacao> listarTodos() {
        return avaliacaoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody AvaliacaoDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        Avaliador avaliador = avaliadorRepository.findById(dto.getAvaliadorId())
                .orElseThrow(() -> new EntityNotFoundException("Avaliador não encontrado"));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setProjeto(projeto);
        avaliacao.setAvaliador(avaliador);
        avaliacao.setNota(dto.getNota());
        avaliacao.setParecer(dto.getParecer());

        if (avaliacaoService.isAvaliadorAutorDoProjeto(avaliador.getId(), projeto.getId())) {
            return ResponseEntity.badRequest().body("Avaliador não pode avaliar seu próprio projeto");
        }
        if (avaliacaoService.avaliadorJaAvaliouProjeto(avaliador.getId(), projeto.getId())) {
            return ResponseEntity.badRequest().body("Este avaliador já avaliou este projeto");
        }

        return ResponseEntity.ok(avaliacaoService.criar(avaliacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> buscarPorId(@PathVariable Long id) {
        return avaliacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> atualizar(@PathVariable Long id, @RequestBody AvaliacaoDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        Avaliador avaliador = avaliadorRepository.findById(dto.getAvaliadorId())
                .orElseThrow(() -> new EntityNotFoundException("Avaliador não encontrado"));

        Avaliacao atualizado = new Avaliacao();
        atualizado.setProjeto(projeto);
        atualizado.setAvaliador(avaliador);
        atualizado.setNota(dto.getNota());
        atualizado.setParecer(dto.getParecer());

        return ResponseEntity.ok(avaliacaoService.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

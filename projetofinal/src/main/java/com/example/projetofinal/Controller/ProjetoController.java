package com.example.projetofinal.Controller;

import com.example.projetofinal.dto.AvaliacaoDTO;
import com.example.projetofinal.dto.ProjetoRequestDTO;
import com.example.projetofinal.model.Avaliacao;
import com.example.projetofinal.model.Avaliador;
import com.example.projetofinal.model.Autor;
import com.example.projetofinal.model.Projeto;
import com.example.projetofinal.repository.AvaliacaoRepository;
import com.example.projetofinal.repository.AvaliadorRepository;
import com.example.projetofinal.repository.ProjetoRepository;
import com.example.projetofinal.services.AutorService;
import com.example.projetofinal.services.AvaliacaoService;
import com.example.projetofinal.services.AvaliadorService;
import com.example.projetofinal.services.PremioService;
import com.example.projetofinal.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProjetoController {

    private final AutorService autorService;
    private final AvaliacaoService avaliacaoService;
    private final AvaliadorService avaliadorService;
    private final PremioService premioService;
    private final ProjetoService projetoService;
    private final ProjetoRepository projetoRepository;
    private final AvaliadorRepository avaliadorRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Autowired
    public ProjetoController(AutorService autorService,
                             AvaliacaoService avaliacaoService,
                             AvaliadorService avaliadorService,
                             PremioService premioService,
                             ProjetoService projetoService,
                             ProjetoRepository projetoRepository,
                             AvaliadorRepository avaliadorRepository,
                             AvaliacaoRepository avaliacaoRepository) {
        this.autorService = autorService;
        this.avaliacaoService = avaliacaoService;
        this.avaliadorService = avaliadorService;
        this.premioService = premioService;
        this.projetoService = projetoService;
        this.projetoRepository = projetoRepository;
        this.avaliadorRepository = avaliadorRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // -------- PROJETOS CRUD + LISTAS --------
    @GetMapping("/projetos")
    public List<Projeto> listarTodos() {
        return projetoService.listarTodos();
    }

    @GetMapping("/projetos/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/projetos")
    public ResponseEntity<?> criar(@RequestBody ProjetoRequestDTO dto) {
        try {
            Projeto projeto = new Projeto();
            projeto.setTitulo(dto.getTitulo());
            projeto.setResumo(dto.getResumo());
            projeto.setAreaTematica(dto.getAreaTematica());
            Projeto saved = projetoService.criar(projeto, dto.getAutoresIds());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/projetos/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // -------- DASHBOARD RESUMO --------
    @GetMapping("/projetos/resumo")
    public ResponseEntity<Map<String, Object>> getResumo() {
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("totalProjetos", projetoService.countProjetos());
        resumo.put("totalAutores", autorService.countAutor());
        resumo.put("totalAvaliadores", avaliadorService.countAvaliador());
        resumo.put("totalPremios", premioService.countPremios());
        return ResponseEntity.ok(resumo);
    }

    // -------- FILTROS --------
    @GetMapping("/projetos/nao-avaliados")
    public ResponseEntity<List<Projeto>> listarProjetosNaoAvaliados() {
        return ResponseEntity.ok(projetoService.listarNaoAvaliados());
    }

    @GetMapping("/projetos/avaliados")
    public ResponseEntity<List<Projeto>> listarProjetosAvaliados() {
        return ResponseEntity.ok(projetoService.listarAvaliados());
    }

    @GetMapping("/projetos/vencedores")
    public ResponseEntity<List<Projeto>> listarVencedores() {
        return ResponseEntity.ok(projetoService.listarVencedores());
    }

    // -------- AVALIAÇÃO PELO PROJETO --------
    @PostMapping("/projetos/{projetoId}/avaliacao")
    public ResponseEntity<?> avaliarProjeto(@PathVariable Long projetoId, @RequestBody AvaliacaoDTO dto) {
        try {
            Projeto projeto = projetoRepository.findById(projetoId)
                    .orElseThrow(() -> new NoSuchElementException("Projeto não encontrado"));

            Avaliador avaliador = avaliadorRepository.findById(dto.getAvaliadorId())
                    .orElseThrow(() -> new NoSuchElementException("Avaliador não encontrado"));

            // validação: impedir avaliações duplicadas
            if (avaliacaoRepository.existsByAvaliadorAndProjeto(avaliador, projeto)) {
                return ResponseEntity.badRequest().body(Map.of("message", "Este avaliador já avaliou este projeto"));
            }

            Avaliacao av = new Avaliacao();
            av.setProjeto(projeto);
            av.setAvaliador(avaliador);
            av.setNota(dto.getNota());
            av.setParecer(dto.getParecer());

            Avaliacao salva = avaliacaoRepository.save(av);

            projetoService.atualizarMediaAposAvaliacao(projetoId);

            return ResponseEntity.ok(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Erro ao avaliar projeto: " + e.getMessage()));
        }
    }

}

package com.example.projetofinal.Controller;

import com.example.projetofinal.model.Projeto;
import com.example.projetofinal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projetos")
@CrossOrigin(origins = "*")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private AvaliacaoService avaliacaoService;
    @Autowired
    private AvaliadorService avaliadorService;
    @Autowired
    private PremioService premioService;

    @GetMapping
    public List<Projeto> listarTodos() {
        return projetoService.listarTodos();
    }

    @PostMapping
    public Projeto criar(@RequestBody Projeto projeto) {
        return projetoService.criar(projeto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Long id) {
        return projetoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumo")
    public ResponseEntity<Map<String, Object>> getResumo() {
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("totalProjetos", projetoService.countProjetos());
        resumo.put("totalAutores", autorService.countAutor());
        resumo.put("totalAvaliadores", avaliadorService.countAvaliador());
        resumo.put("totalPremios", premioService.countPremios());

        return ResponseEntity.ok(resumo);
    }

    // 1. Listagem geral de projetos com seus respectivos autores
    @GetMapping("/com-autores")
    public List<Projeto> listarProjetosComAutores() {
        return projetoService.listarProjetosComAutores();
    }



    // 3. Listagem de projetos enviados que ainda não foram avaliados
    @GetMapping("/nao-avaliados")
    public List<Projeto> listarProjetosNaoAvaliados() {
        return projetoService.listarProjetosNaoAvaliados();
    }

    // 4. Listagem de projetos já avaliados
    @GetMapping("/avaliados")
    public List<Object[]> listarProjetosAvaliados() {
        return projetoService.listarProjetosAvaliados();
    }

    // 5. Listagem de projetos vencedores em ordem decrescente de nota
    @GetMapping("/vencedores")
    public List<Object[]> listarProjetosVencedores() {
        return projetoService.listarProjetosVencedores();
    }
}
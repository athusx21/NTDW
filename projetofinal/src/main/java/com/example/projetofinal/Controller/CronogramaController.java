package com.example.projetofinal.Controller;

import com.example.projetofinal.model.Cronograma;
import com.example.projetofinal.services.CronogramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cronogramas")
@CrossOrigin(origins = "*")
public class CronogramaController {

    @Autowired
    private CronogramaService cronogramaService;

    // Listar todos os cronogramas
    @GetMapping
    public List<Cronograma> listarTodos() {
        return cronogramaService.listarTodos();
    }

    // Criar um novo cronograma
    @PostMapping
    public Cronograma criar(@RequestBody Cronograma cronograma) {
        return cronogramaService.criar(cronograma);
    }

    // Buscar cronograma por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cronograma> buscarPorId(@PathVariable Long id) {
        return cronogramaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar um cronograma existente
    @PutMapping("/{id}")
    public ResponseEntity<Cronograma> atualizar(@PathVariable Long id, @RequestBody Cronograma atualizado) {
        return ResponseEntity.ok(cronogramaService.atualizar(id, atualizado));
    }

    // Deletar um cronograma
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cronogramaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

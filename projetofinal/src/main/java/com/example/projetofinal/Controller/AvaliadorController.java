package com.example.projetofinal.Controller;

import com.example.projetofinal.model.Avaliador;
import com.example.projetofinal.services.AvaliadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avaliadores")
@CrossOrigin(origins = "*")
public class AvaliadorController {

    @Autowired
    private AvaliadorService avaliadorService;

    @GetMapping
    public List<Avaliador> listarTodos() {
        return avaliadorService.listarTodos();
    }

    @PostMapping
    public Avaliador criar(@RequestBody Avaliador avaliador) {
        return avaliadorService.criar(avaliador);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Avaliador> buscarPorId(@PathVariable Long id) {
        return avaliadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avaliador> atualizar(@PathVariable Long id, @RequestBody Avaliador atualizado) {
        return ResponseEntity.ok(avaliadorService.atualizar(id, atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
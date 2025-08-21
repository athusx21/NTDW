package com.example.projetofinal.Controller;

import com.example.projetofinal.model.Premio;
import com.example.projetofinal.services.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/premios")
@CrossOrigin(origins = "*")
public class PremioController {

    @Autowired
    private PremioService premioService;

    @GetMapping
    public List<Premio> listarTodos() {
        return premioService.listarTodos();
    }

    @PostMapping
    public Premio criar(@RequestBody Premio premio) {
        return premioService.criar(premio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Premio> buscarPorId(@PathVariable Long id) {
        return premioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        premioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
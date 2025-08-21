package com.example.projetofinal.services;

import com.example.projetofinal.model.Autor;
import com.example.projetofinal.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Autor Service
@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }

    public Autor criar(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor atualizar(Long id, Autor atualizado) {
        return autorRepository.findById(id).map(autor -> {
            autor.setNome(atualizado.getNome());
            autor.setCpf(atualizado.getCpf());
            autor.setEmail(atualizado.getEmail());
            autor.setTelefone(atualizado.getTelefone());
            autor.setDataNascimento(atualizado.getDataNascimento());
            return autorRepository.save(autor);
        }).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
    }

    public void deletar(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Autor não encontrado");
        }
    }

    // 2. Listar autores com projetos
    public List<Object[]> listarAutoresComProjetos() {
        return autorRepository.findAutoresComProjetos();
    }

    public long countAutor() {
        return autorRepository.count();
    }
}
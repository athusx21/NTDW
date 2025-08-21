package com.example.projetofinal.services;

import com.example.projetofinal.model.Cronograma;
import com.example.projetofinal.repository.CronogramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CronogramaService {

    @Autowired
    private CronogramaRepository cronogramaRepository;

    // Listar todos os cronogramas
    public List<Cronograma> listarTodos() {
        return cronogramaRepository.findAll();
    }

    // Criar um novo cronograma
    public Cronograma criar(Cronograma cronograma) {
        return cronogramaRepository.save(cronograma);
    }

    // Buscar cronograma por ID
    public Optional<Cronograma> buscarPorId(Long id) {
        return cronogramaRepository.findById(id);
    }

    // Atualizar um cronograma existente
    public Cronograma atualizar(Long id, Cronograma atualizado) {
        Optional<Cronograma> cronogramaExistente = cronogramaRepository.findById(id);

        if (cronogramaExistente.isPresent()) {
            Cronograma cronograma = cronogramaExistente.get();
            cronograma.setDescricao(atualizado.getDescricao());
            cronograma.setDataInicio(atualizado.getDataInicio());
            cronograma.setDataFim(atualizado.getDataFim());
            cronograma.setPremio(atualizado.getPremio());

            return cronogramaRepository.save(cronograma);
        } else {
            throw new RuntimeException("Cronograma não encontrado para o id " + id);
        }
    }

    // Deletar um cronograma
    public void excluir(Long id) {
        cronogramaRepository.deleteById(id);
    }

    public long countCronograma() {
        return cronogramaRepository.count();
    }
}

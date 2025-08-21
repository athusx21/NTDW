package com.example.projetofinal.dto;

import com.example.projetofinal.model.Projeto;

import java.util.List;

public class ProjetoRequestDTO {
    private Long id;
    private String titulo;
    private String resumo;
    private String areaTematica;
    private List<Long> autoresIds;
    private String dataEnvio;
    private Double mediaNota;
    private Boolean avaliado;

    // 🔑 Construtor vazio (necessário para o Jackson)
    public ProjetoRequestDTO() {
    }

    // Construtor que mapeia a partir da entidade Projeto
    public ProjetoRequestDTO(Projeto projeto) {
        this.id = projeto.getId();
        this.titulo = projeto.getTitulo();
        this.resumo = projeto.getResumo();
        this.areaTematica = projeto.getAreaTematica();
        this.dataEnvio = String.valueOf(projeto.getDataEnvio());
        this.mediaNota = projeto.getMediaNota();
        // regra: se tiver avaliações, está avaliado
        this.avaliado = projeto.getAvaliacoes() != null && !projeto.getAvaliacoes().isEmpty();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getAreaTematica() { return areaTematica; }
    public void setAreaTematica(String areaTematica) { this.areaTematica = areaTematica; }

    public List<Long> getAutoresIds() { return autoresIds; }
    public void setAutoresIds(List<Long> autoresIds) { this.autoresIds = autoresIds; }

    public String getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(String dataEnvio) { this.dataEnvio = dataEnvio; }

    public Double getMediaNota() { return mediaNota; }
    public void setMediaNota(Double mediaNota) { this.mediaNota = mediaNota; }

    public Boolean getAvaliado() { return avaliado; }
    public void setAvaliado(Boolean avaliado) { this.avaliado = avaliado; }
}

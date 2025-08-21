package com.example.projetofinal.dto;

public class AvaliacaoDTO {
    private Long projetoId;
    private Long avaliadorId;
    private Double nota;
    private String parecer;

    public Long getProjetoId() { return projetoId; }
    public void setProjetoId(Long projetoId) { this.projetoId = projetoId; }

    public Long getAvaliadorId() { return avaliadorId; }
    public void setAvaliadorId(Long avaliadorId) { this.avaliadorId = avaliadorId; }

    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

    public String getParecer() { return parecer; }
    public void setParecer(String parecer) { this.parecer = parecer; }
}

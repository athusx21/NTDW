package com.example.projetofinal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;


@Entity
public class  Premio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private long ano;

    @OneToMany(mappedBy = "premio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Cronograma> cronograma;

    public Premio(String nome, String descricao, Long ano, List<Cronograma> cronograma) {
        this.nome = nome;
        this.descricao = descricao;
        this.ano = ano;
        this.cronograma = cronograma;
    }

    public Premio() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Cronograma> getCronograma() {
        return cronograma;
    }

    public void setCronograma(List<Cronograma> cronograma) {
        this.cronograma = cronograma;
    }

    public long getAno() {
        return ano;
    }

    public void setAno(long ano) {
        this.ano = ano;
    }


}

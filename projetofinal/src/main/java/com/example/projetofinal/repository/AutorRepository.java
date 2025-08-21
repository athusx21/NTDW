package com.example.projetofinal.repository;



import com.example.projetofinal.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a, p FROM Autor a LEFT JOIN a.projetos p")
    List<Object[]> findAutoresComProjetos();
}
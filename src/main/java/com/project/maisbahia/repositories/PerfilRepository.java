package com.project.maisbahia.repositories;

import com.project.maisbahia.entities.usuarios.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Perfil findByNome(String nome);
}

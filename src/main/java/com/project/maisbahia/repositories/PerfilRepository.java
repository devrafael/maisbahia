package com.project.maisbahia.repositories;

import com.project.maisbahia.entities.enums.PerfilEnum;
import com.project.maisbahia.entities.usuarios.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Set<Perfil> findByNome(PerfilEnum nome);
}

package com.project.maisbahia.repositories;

import java.util.Optional;

import com.project.maisbahia.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TokenRepository extends JpaRepository<Token, Long>{
    Optional<Token> findByToken(String token);
}

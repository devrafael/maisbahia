package com.project.maisbahia.repositories;



import com.project.maisbahia.controllers.dtos.responses.ProdutoResponseRecord;
import com.project.maisbahia.entities.produtos.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    List<ProdutoResponseRecord> findByNomeProduto(String nomeProduto);

}
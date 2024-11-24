package com.project.maisbahia.repositories;



import com.project.maisbahia.controllers.records.responses.ProdutoResponseRecord;
import com.project.maisbahia.entities.produtos.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {


    @Query("SELECT DISTINCT p.nomeProduto " +
            "FROM Produto p " +
            "WHERE LOWER(p.nomeProduto) " +
            "LIKE LOWER(CONCAT('%', :nome, '%'))"+
            "ORDER BY p.nomeProduto ASC")
    List<String> findDistinctByNomeProdutoContaining(@Param("nome") String nome);

    @Query("SELECT p FROM Produto p WHERE p.nomeProduto = :nomeProduto")
    List<Produto> findByNomeProduto(@Param("nomeProduto")String nomeProduto);
}

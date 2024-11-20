package com.project.maisbahia.controllers.records.requests;

import com.project.maisbahia.entities.produtos.CategoriaProduto;

import java.time.LocalDate;


public record ProdutoRequestRecord
(
    String nomeProduto,
    CategoriaProduto categoria,
    Integer quantidade,
    String lote,
    LocalDate dataValidade,
    LocalDate dataFabricacao,
    String fabricante
) {}

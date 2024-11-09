package com.project.maisbahia.controllers.dtos.requests;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
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

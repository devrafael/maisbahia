package com.project.maisbahia.controllers.dtos.requests;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;

import java.time.LocalDate;


public record ProdutoRequestRecord
(
    String nomeProduto,
    CategoriaProdutoEnum categoria,
    Integer quantidade,
    String lote,
    LocalDate dataValidade,
    LocalDate dataFabricacao,
    String fabricante
) {}

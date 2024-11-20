package com.project.maisbahia.controllers.records.responses;

import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProdutoResponseRecord
(
    UUID idProduto,
    String nomeProduto,
    CategoriaProdutoEnum categoria,
    Integer quantidade,
    String lote,
    LocalDate dataValidade,
    LocalDate dataFabricacao,
    String fabricante,
    LocalDateTime dataCadastro
) {}
package com.project.maisbahia.unit.controllers.produtos;

import com.project.maisbahia.controllers.ProdutoController;
import com.project.maisbahia.controllers.records.requests.ProdutoRequestRecord;
import com.project.maisbahia.controllers.records.responses.ProdutoResponseRecord;
import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import com.project.maisbahia.entities.produtos.CategoriaProduto;
import com.project.maisbahia.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    private ProdutoRequestRecord produtoRequest;
    private ProdutoResponseRecord produtoResponse;

    @BeforeEach
    void setUp() {
        CategoriaProduto categoria = new CategoriaProduto(1, CategoriaProdutoEnum.MASSA);

        produtoRequest = new ProdutoRequestRecord(
                "Macarrão",
                categoria,
                100,
                "L123",
                LocalDate.now().plusMonths(6),
                LocalDate.now().minusMonths(1),
                "Fábrica de Massas"
        );

        produtoResponse = new ProdutoResponseRecord(
                UUID.randomUUID(),
                "Macarrão",
                CategoriaProdutoEnum.MASSA,
                100,
                "L123",
                LocalDate.now().plusMonths(6),
                LocalDate.now().minusMonths(1),
                "Fábrica de Massas",
                LocalDate.now().atStartOfDay()
        );
    }

    @Test
    void testCriarProduto() throws Exception {
        when(produtoService.criarProduto(any(ProdutoRequestRecord.class))).thenReturn(produtoResponse);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomeProduto": "Macarrão",
                                  "categoria": {
                                    "idCategoria": 1,
                                    "categoriaEnum": "MASSA"
                                  },
                                  "quantidade": 100,
                                  "lote": "L123",
                                  "dataValidade": "2025-06-01",
                                  "dataFabricacao": "2024-01-01",
                                  "fabricante": "Fábrica de Massas"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeProduto").value("Macarrão"))
                .andExpect(jsonPath("$.categoria").value("MASSA"))
                .andExpect(jsonPath("$.quantidade").value(100))
                .andExpect(jsonPath("$.lote").value("L123"));
    }

    @Test
    void testListarProdutos() throws Exception {
        when(produtoService.listarProdutos()).thenReturn(List.of(produtoResponse));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nomeProduto").value("Macarrão"));
    }

    @Test
    void testBuscarProdutoPorId() throws Exception {
        UUID id = produtoResponse.idProduto();
        when(produtoService.buscarProdutoPorId(id)).thenReturn(produtoResponse);

        mockMvc.perform(get("/produtos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProduto").value(id.toString()))
                .andExpect(jsonPath("$.nomeProduto").value("Macarrão"));
    }

    @Test
    void testAtualizarProduto() throws Exception {
        UUID id = produtoResponse.idProduto();
        when(produtoService.atualizarProduto(any(String.class), any(ProdutoRequestRecord.class)))
                .thenReturn(produtoResponse);

        mockMvc.perform(put("/produtos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nomeProduto": "Macarrão Atualizado",
                                  "categoria": {
                                    "idCategoria": 1,
                                    "categoriaEnum": "MASSA"
                                  },
                                  "quantidade": 120,
                                  "lote": "L456",
                                  "dataValidade": "2025-12-01",
                                  "dataFabricacao": "2024-06-01",
                                  "fabricante": "Nova Fábrica de Massas"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeProduto").value("Macarrão"))
                .andExpect(jsonPath("$.quantidade").value(100));
    }

    @Test
    void testDeletarProduto() throws Exception {
        UUID id = produtoResponse.idProduto();

        mockMvc.perform(delete("/produtos/" + id))
                .andExpect(status().isNoContent());
    }
}

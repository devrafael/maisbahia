package com.project.maisbahia.unit.services;

import com.project.maisbahia.controllers.records.requests.ProdutoRequestRecord;
import com.project.maisbahia.controllers.records.responses.ProdutoResponseRecord;
import com.project.maisbahia.entities.enums.CategoriaProdutoEnum;
import com.project.maisbahia.entities.produtos.CategoriaProduto;
import com.project.maisbahia.entities.produtos.Produto;
import com.project.maisbahia.repositories.ProdutoRepository;
import com.project.maisbahia.services.ProdutoService;
import com.project.maisbahia.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Produto produto;
    private ProdutoRequestRecord produtoRequest;
    private ProdutoResponseRecord produtoResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        CategoriaProduto categoria = new CategoriaProduto(1, CategoriaProdutoEnum.MASSA);

        produto = new Produto(
                UUID.randomUUID(),
                categoria,
                100,
                "L123",
                LocalDate.now().plusMonths(6),
                LocalDate.now().minusMonths(1),
                "Fábrica de Massas",
                "Macarrão",
                LocalDateTime.now()
        );

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
                produto.getIdProduto(),
                produto.getNomeProduto(),
                CategoriaProdutoEnum.MASSA,
                produto.getQuantidade(),
                produto.getLote(),
                produto.getDataValidade(),
                produto.getDataFabricacao(),
                produto.getFabricante(),
                produto.getDataCadastro()
        );
    }

    @Test
    void testCriarProduto() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseRecord response = produtoService.criarProduto(produtoRequest);

        assertNotNull(response, "A resposta não deveria ser nula.");
        assertEquals("Macarrão", response.nomeProduto(), "O nome do produto deveria ser 'Macarrão'.");
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void testListarProdutos() {
        when(produtoRepository.findAll()).thenReturn(List.of(produto));

        List<ProdutoResponseRecord> produtos = produtoService.listarProdutos();

        assertNotNull(produtos, "A lista de produtos não deveria ser nula.");
        assertFalse(produtos.isEmpty(), "A lista de produtos não deveria estar vazia.");
        assertEquals(1, produtos.size(), "A lista de produtos deveria conter 1 item.");
        assertEquals("Macarrão", produtos.get(0).nomeProduto(), "O nome do produto deveria ser 'Macarrão'.");
    }

    @Test
    void testBuscarProdutoPorId() {
        when(produtoRepository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));

        ProdutoResponseRecord response = produtoService.buscarProdutoPorId(produto.getIdProduto());

        assertNotNull(response, "A resposta não deveria ser nula.");
        assertEquals(produto.getIdProduto(), response.idProduto(), "O ID do produto deveria ser igual ao ID buscado.");
        verify(produtoRepository, times(1)).findById(produto.getIdProduto());
    }

    @Test
    void testBuscarProdutoPorIdNaoEncontrado() {
        UUID idInexistente = UUID.randomUUID();
        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produtoService.buscarProdutoPorId(idInexistente),
                "Deveria lançar ResourceNotFoundException quando o produto não é encontrado.");
    }

    @Test
    void testAtualizarProduto() {
        when(produtoRepository.findById(produto.getIdProduto())).thenReturn(Optional.of(produto));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoResponseRecord response = produtoService.atualizarProduto(produto.getNomeProduto(), produtoRequest);

        assertNotNull(response, "A resposta não deveria ser nula.");
        assertEquals("Macarrão", response.nomeProduto(), "O nome do produto deveria ser 'Macarrão'.");
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void testExcluirProduto() {
        UUID id = produto.getIdProduto();
        when(produtoRepository.existsById(id)).thenReturn(true);
        doNothing().when(produtoRepository).deleteById(id);

        assertDoesNotThrow(() -> produtoService.exluirProduto(id), "Não deveria lançar exceção ao excluir um produto.");
        verify(produtoRepository, times(1)).deleteById(id);
    }

    @Test
    void testExcluirProdutoNaoEncontrado() {
        UUID idInexistente = UUID.randomUUID();
        when(produtoRepository.existsById(idInexistente)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> produtoService.exluirProduto(idInexistente),
                "Deveria lançar RuntimeException quando o produto não é encontrado.");
    }
}

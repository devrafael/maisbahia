package com.project.maisbahia.controllers;

import com.project.maisbahia.controllers.records.requests.ProdutoRequestRecord;
import com.project.maisbahia.controllers.records.responses.ProdutoResponseRecord;
import com.project.maisbahia.entities.produtos.CategoriaProduto;
import com.project.maisbahia.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/produtos")
public class ProdutoController
{
    @Autowired
    private ProdutoService produtoService;


    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<ProdutoResponseRecord> criarProduto(@RequestBody ProdutoRequestRecord request)
    {
        ProdutoResponseRecord response = produtoService.criarProduto(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseRecord>> listarProdutos()
    {
        List<ProdutoResponseRecord> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }


    @GetMapping("/buscar")
    public ResponseEntity<Page<String>> filtro(
            @RequestParam String nomeProduto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<String> nomes = produtoService.filtro(nomeProduto, page, size);
        return ResponseEntity.ok(nomes);
    }

    @GetMapping("/buscar/detalhes")
    public ResponseEntity<List<ProdutoResponseRecord>> detalhesProduto(@RequestParam String nomeProduto){
        List<ProdutoResponseRecord> produtos = produtoService.detalhesProduto(nomeProduto);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseRecord> buscarProdutoPorId (@PathVariable UUID id)
    {
        ProdutoResponseRecord response = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{nomeProduto}")
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<ProdutoResponseRecord> atualizarProduto (@PathVariable String nomeProduto, @RequestBody ProdutoRequestRecord request)
    {
        ProdutoResponseRecord response = produtoService.atualizarProduto(nomeProduto, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<Void> deletarProduto (@PathVariable UUID id)
    {
        produtoService.exluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<String>> listarCategorias(){
        List<CategoriaProduto> categorias = produtoService.listarCategorias();
        List<String> nomesCategorias = categorias.stream()
                .map(categoria -> categoria.getCategoriaEnum().getNomeCategoria()) // Pega o nome da categoria de cada enum
                .collect(Collectors.toList());
        return ResponseEntity.ok(nomesCategorias);
    }
}

package com.project.maisbahia.controllers;

import com.project.maisbahia.controllers.records.requests.ProdutoRequestRecord;
import com.project.maisbahia.controllers.records.responses.ProdutoResponseRecord;
import com.project.maisbahia.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


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

//    @GetMapping
//    public ResponseEntity<List<ProdutoResponseRecord>> buscarProdutoPorNome(@RequestParam(required = false) String filtro){
//        List<ProdutoResponseRecord> produtos = produtoService.buscarProdutoNome(filtro);
//        if(filtro != null || !filtro.isEmpty()){
//            String filtroProduto = filtro.toLowerCase();
//            produtos = produtos.stream()
//                    .filter(produto -> produto.nomeProduto().toLowerCase().contains(filtroProduto))
//                    .toList();
//        }
//        return ResponseEntity.ok(produtos);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseRecord> buscarProdutoPorId (@PathVariable UUID id)
    {
        ProdutoResponseRecord response = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<ProdutoResponseRecord> atualizarProduto (@PathVariable UUID id, @RequestBody ProdutoRequestRecord request)
    {
        ProdutoResponseRecord response = produtoService.atualizarProduto(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_Gerente')")
    public ResponseEntity<Void> deletarProduto (@PathVariable UUID id)
    {
        produtoService.exluirProduto(id);
        return ResponseEntity.noContent().build();
    }
}

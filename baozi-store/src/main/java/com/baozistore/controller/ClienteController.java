
package com.baozistore.controller;

import com.baozistore.model.Cliente;
import com.baozistore.repository.ClienteRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    // Cadastrar cliente
    @PostMapping
    public ResponseEntity<Cliente> criar(
            @RequestBody Cliente cliente) {

        cliente.setId(null);
        Cliente salvo = repository.save(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(salvo);
    }

    // Listar todos os clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return repository.findAll();
    }

    // Consultar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Excluir cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
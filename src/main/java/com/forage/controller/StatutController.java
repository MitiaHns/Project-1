package com.forage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forage.model.Statut;
import com.forage.service.StatutService;

import java.util.List;

@RestController
@RequestMapping("/api/statuts")
public class StatutController {

    private final StatutService statutService;

    public StatutController(StatutService statutService) {
        this.statutService = statutService;
    }

    @GetMapping
    public List<Statut> getAllStatuts() {
        return statutService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statut> getStatutById(@PathVariable Long id) {
        return statutService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
package com.forage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forage.model.Devis;
import com.forage.model.DetailDevis;
import com.forage.model.Demande;
import com.forage.service.DevisServiceImpl;
import com.forage.service.TypeDevisService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DevisController {

    private final DevisServiceImpl devisService;
    private final TypeDevisService typeDevisService;

    public DevisController(DevisServiceImpl devisService, TypeDevisService typeDevisService) {
        this.devisService = devisService;
        this.typeDevisService = typeDevisService;
    }

    /**
     * GET /api/devis - Liste tous les devis
     */
    @GetMapping("/devis")
    public List<Devis> getAllDevis() {
        return devisService.findAll();
    }

    /**
     * GET /api/devis/{id} - Get a specific devis
     */
    @GetMapping("/devis/{id}")
    public ResponseEntity<Devis> getDevisById(@PathVariable Long id) {
        return devisService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /api/type-devis - Liste tous les types de devis
     */
    @GetMapping("/type-devis")
    public List<com.forage.model.TypeDevis> getAllTypeDevis() {
        return typeDevisService.findAll();
    }

    /**
     * GET /api/demandes/last - Dernière demande créée
     */
    @GetMapping("/demandes/last")
    public ResponseEntity<Demande> getLastDemande() {
        return devisService.getLastDemande()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/devis - Créer un devis avec ses détails (transaction)
     */
    @PostMapping("/devis")
    public ResponseEntity<Devis> createDevis(@RequestBody DevisRequest request) {
        try {
            Devis devis = new Devis();
            devis.setDateDevis(request.getDateDevis());
            devis.setTypeDevis(request.getTypeDevis());
            
            // Set the demande from the ID
            if (request.getDemandeId() != null) {
                Demande demande = new Demande();
                demande.setId(request.getDemandeId());
                devis.setDemande(demande);
            }
            
            // Convert DetailDevisDTO to DetailDevis
            List<DetailDevis> details = null;
            if (request.getDetails() != null) {
                details = request.getDetails().stream()
                        .map(dto -> {
                            DetailDevis detail = new DetailDevis();
                            detail.setLibelle(dto.getLibelle());
                            detail.setQuantite(dto.getQuantite());
                            detail.setPrixUnitaire(dto.getPrixUnitaire());
                            return detail;
                        })
                        .toList();
            }
            
            Devis savedDevis = devisService.createDevisWithDetails(devis, details);
            return ResponseEntity.ok(savedDevis);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * DELETE /api/devis/{id} - Supprimer un devis
     */
    @DeleteMapping("/devis/{id}")
    public ResponseEntity<Void> deleteDevis(@PathVariable Long id) {
        if (devisService.deleteDevis(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
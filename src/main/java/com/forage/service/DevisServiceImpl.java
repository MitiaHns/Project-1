package com.forage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forage.model.Devis;
import com.forage.model.DetailDevis;
import com.forage.repository.DevisRepository;
import com.forage.repository.DemandeRepository;
import com.forage.repository.StatutRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DevisServiceImpl {

    private final DevisRepository devisRepository;
    private final DemandeRepository demandeRepository;
    private final StatutRepository statutRepository;

    public DevisServiceImpl(DevisRepository devisRepository, 
                            DemandeRepository demandeRepository,
                            StatutRepository statutRepository) {
        this.devisRepository = devisRepository;
        this.demandeRepository = demandeRepository;
        this.statutRepository = statutRepository;
    }

    public List<Devis> findAll() {
        return devisRepository.findAll();
    }

    public Optional<Devis> findById(Long id) {
        return devisRepository.findById(id);
    }

    /**
     * Crée un devis avec ses détails dans une transaction
     * Si une étape échoue, rien n'est inséré en base
     */
    @Transactional
    public Devis createDevisWithDetails(Devis devis, List<DetailDevis> details) {
        // Calculer le montant total
        BigDecimal montantTotal = BigDecimal.ZERO;
        if (details != null) {
            for (DetailDevis detail : details) {
                BigDecimal montant = detail.getPrixUnitaire().multiply(new BigDecimal(detail.getQuantite()));
                montantTotal = montantTotal.add(montant);
            }
        }
        devis.setMontantTotal(montantTotal);
        
        if (devis.getDateDevis() == null) {
            devis.setDateDevis(LocalDate.now());
        }

        // Définir le statut par défaut "Devis crée" (chargé depuis la table statut)
        statutRepository.findByLibelle("Devis crée").ifPresent(devis::setStatut);
        
        // Sauvegarder le devis (sans les détails encore)
        Devis savedDevis = devisRepository.save(devis);
        
        // Ajouter les détails avec la référence au devis
        if (details != null && !details.isEmpty()) {
            for (DetailDevis detail : details) {
                detail.setDevis(savedDevis);
            }
            savedDevis.setDetails(details);
            savedDevis = devisRepository.save(savedDevis);
        }
        
        return savedDevis;
    }

    public Devis createDevis(Devis devis) {
        if (devis.getDateDevis() == null) {
            devis.setDateDevis(LocalDate.now());
        }
        statutRepository.findByLibelle("Devis crée").ifPresent(devis::setStatut);
        return devisRepository.save(devis);
    }

    public Optional<Devis> updateDevis(Long id, Devis details) {
        return devisRepository.findById(id).map(devis -> {
            devis.setDateDevis(details.getDateDevis());
            devis.setTypeDevis(details.getTypeDevis());
            devis.setDemande(details.getDemande());
            devis.setStatut(details.getStatut());
            devis.setDetails(details.getDetails());
            return devisRepository.save(devis);
        });
    }

    public boolean deleteDevis(Long id) {
        if (devisRepository.existsById(id)) {
            devisRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Récupère la dernière demande créée
     */
    public Optional<com.forage.model.Demande> getLastDemande() {
        return demandeRepository.findTopByOrderByIdDesc();
    }
}

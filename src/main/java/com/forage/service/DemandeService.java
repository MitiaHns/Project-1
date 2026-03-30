package com.forage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forage.model.Demande;
import com.forage.model.DemandeStatut;
import com.forage.model.Statut;
import com.forage.repository.DemandeRepository;
import com.forage.repository.DemandeStatutRepository;
import com.forage.repository.StatutRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    private final DemandeRepository demandeRepository;
    private final DemandeStatutRepository demandeStatutRepository;
    private final StatutRepository statutRepository;

    public DemandeService(DemandeRepository demandeRepository, 
                           DemandeStatutRepository demandeStatutRepository,
                           StatutRepository statutRepository) {
        this.demandeRepository = demandeRepository;
        this.demandeStatutRepository = demandeStatutRepository;
        this.statutRepository = statutRepository;
    }

    public List<Demande> findAll() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> findById(Long id) {
        return demandeRepository.findById(id);
    }

    @Transactional
    public Demande createDemande(Demande demande) {
        if (demande.getDateDemande() == null) {
            demande.setDateDemande(LocalDate.now());
        }
        
        statutRepository.findByLibelle("Demande creee").ifPresent(demande::setStatut);

        Demande savedDemande = demandeRepository.save(demande);
        
        Optional<Statut> statutCree = statutRepository.findByLibelle("Demande creee");
        
        if (statutCree.isPresent()) {
            DemandeStatut demandeStatut = new DemandeStatut();
            demandeStatut.setDemande(savedDemande);
            demandeStatut.setStatut(statutCree.get());
            demandeStatut.setDateStatut(LocalDateTime.now());
            demandeStatutRepository.save(demandeStatut);
            
            savedDemande.setStatut(statutCree.get());
            savedDemande = demandeRepository.save(savedDemande);
        }
        
        return savedDemande;
    }

    public Optional<Demande> updateDemande(Long id, Demande details) {
        return demandeRepository.findById(id).map(demande -> {
            demande.setDateDemande(details.getDateDemande());
            demande.setDescription(details.getDescription());
            demande.setLieu(details.getLieu());
            demande.setClient(details.getClient());

            if (details.getStatus() != null && !details.getStatus().isBlank()) {
                statutRepository.findByLibelle(details.getStatus()).ifPresent(demande::setStatut);
            } else if (details.getStatut() != null && details.getStatut().getId() != null) {
                demande.setStatut(details.getStatut());
            }

            return demandeRepository.save(demande);
        });
    }

    public boolean deleteDemande(Long id) {
        if (demandeRepository.existsById(id)) {
            demandeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

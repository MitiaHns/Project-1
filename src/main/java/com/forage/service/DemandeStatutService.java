package com.forage.service;

import org.springframework.stereotype.Service;

import com.forage.model.DemandeStatut;
import com.forage.repository.DemandeStatutRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeStatutService {

    private final DemandeStatutRepository demandeStatutRepository;

    public DemandeStatutService(DemandeStatutRepository demandeStatutRepository) {
        this.demandeStatutRepository = demandeStatutRepository;
    }

    public List<DemandeStatut> findAll() {
        return demandeStatutRepository.findAll();
    }

    public Optional<DemandeStatut> findById(Long id) {
        return demandeStatutRepository.findById(id);
    }

    public DemandeStatut createDemandeStatut(DemandeStatut demandeStatut) {
        return demandeStatutRepository.save(demandeStatut);
    }

    public Optional<DemandeStatut> updateDemandeStatut(Long id, DemandeStatut details) {
        return demandeStatutRepository.findById(id).map(demandeStatut -> {
            demandeStatut.setDateStatut(details.getDateStatut());
            demandeStatut.setStatut(details.getStatut());
            demandeStatut.setDemande(details.getDemande());
            return demandeStatutRepository.save(demandeStatut);
        });
    }

    public boolean deleteDemandeStatut(Long id) {
        if (demandeStatutRepository.existsById(id)) {
            demandeStatutRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

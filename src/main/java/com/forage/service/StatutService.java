package com.forage.service;

import org.springframework.stereotype.Service;

import com.forage.model.Statut;
import com.forage.repository.StatutRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StatutService {

    private final StatutRepository statutRepository;

    public StatutService(StatutRepository statutRepository) {
        this.statutRepository = statutRepository;
    }

    public List<Statut> findAll() {
        return statutRepository.findAll();
    }

    public Optional<Statut> findById(Long id) {
        return statutRepository.findById(id);
    }

    public Optional<Statut> findByLibelle(String libelle) {
        return statutRepository.findByLibelle(libelle);
    }

    public Statut createStatut(Statut statut) {
        return statutRepository.save(statut);
    }

    public Optional<Statut> updateStatut(Long id, Statut details) {
        return statutRepository.findById(id).map(statut -> {
            statut.setLibelle(details.getLibelle());
            return statutRepository.save(statut);
        });
    }

    public boolean deleteStatut(Long id) {
        if (statutRepository.existsById(id)) {
            statutRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

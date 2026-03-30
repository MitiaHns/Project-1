package com.forage.service;

import org.springframework.stereotype.Service;

import com.forage.model.DetailDevis;
import com.forage.repository.DetailDevisRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DetailDevisService {

    private final DetailDevisRepository detailDevisRepository;

    public DetailDevisService(DetailDevisRepository detailDevisRepository) {
        this.detailDevisRepository = detailDevisRepository;
    }

    public List<DetailDevis> findAll() {
        return detailDevisRepository.findAll();
    }

    public Optional<DetailDevis> findById(Long id) {
        return detailDevisRepository.findById(id);
    }

    public DetailDevis createDetailDevis(DetailDevis detailDevis) {
        return detailDevisRepository.save(detailDevis);
    }

    public Optional<DetailDevis> updateDetailDevis(Long id, DetailDevis details) {
        return detailDevisRepository.findById(id).map(detailDevis -> {
            detailDevis.setDevis(details.getDevis());
            detailDevis.setLibelle(details.getLibelle());
            detailDevis.setQuantite(details.getQuantite());
            detailDevis.setPrixUnitaire(details.getPrixUnitaire());
            return detailDevisRepository.save(detailDevis);
        });
    }

    public boolean deleteDetailDevis(Long id) {
        if (detailDevisRepository.existsById(id)) {
            detailDevisRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

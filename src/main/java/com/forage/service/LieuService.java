package com.forage.service;

import org.springframework.stereotype.Service;

import com.forage.model.Lieu;
import com.forage.repository.LieuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LieuService {

    private final LieuRepository lieuRepository;

    public LieuService(LieuRepository lieuRepository) {
        this.lieuRepository = lieuRepository;
    }

    public List<Lieu> findAll() {
        return lieuRepository.findAll();
    }

    public Optional<Lieu> findById(Long id) {
        return lieuRepository.findById(id);
    }

    public Lieu createLieu(Lieu lieu) {
        return lieuRepository.save(lieu);
    }

    public Optional<Lieu> updateLieu(Long id, Lieu details) {
        return lieuRepository.findById(id).map(lieu -> {
            lieu.setAdresse(details.getAdresse());
            lieu.setDistrict(details.getDistrict());
            return lieuRepository.save(lieu);
        });
    }

    public boolean deleteLieu(Long id) {
        if (lieuRepository.existsById(id)) {
            lieuRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

package com.forage.service;

import org.springframework.stereotype.Service;

import com.forage.model.TypeDevis;
import com.forage.repository.TypeDevisRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TypeDevisService {

    private final TypeDevisRepository typeDevisRepository;

    public TypeDevisService(TypeDevisRepository typeDevisRepository) {
        this.typeDevisRepository = typeDevisRepository;
    }

    public List<TypeDevis> findAll() {
        return typeDevisRepository.findAll();
    }

    public Optional<TypeDevis> findById(Long id) {
        return typeDevisRepository.findById(id);
    }

    public TypeDevis createTypeDevis(TypeDevis typeDevis) {
        return typeDevisRepository.save(typeDevis);
    }

    public Optional<TypeDevis> updateTypeDevis(Long id, TypeDevis details) {
        return typeDevisRepository.findById(id).map(typeDevis -> {
            typeDevis.setLibelle(details.getLibelle());
            return typeDevisRepository.save(typeDevis);
        });
    }

    public boolean deleteTypeDevis(Long id) {
        if (typeDevisRepository.existsById(id)) {
            typeDevisRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

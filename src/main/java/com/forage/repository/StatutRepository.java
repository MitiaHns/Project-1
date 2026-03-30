package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forage.model.Statut;

import java.util.Optional;

@Repository
public interface StatutRepository extends JpaRepository<Statut, Long> {
    Optional<Statut> findByLibelle(String libelle);
}

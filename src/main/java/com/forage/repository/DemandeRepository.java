package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.forage.model.Demande;

// import java.util.Optional;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    Optional<Demande> findTopByOrderByIdDesc();
}
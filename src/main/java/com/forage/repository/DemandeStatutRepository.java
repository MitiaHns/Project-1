package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forage.model.DemandeStatut;

@Repository
public interface DemandeStatutRepository extends JpaRepository<DemandeStatut, Long> {
}

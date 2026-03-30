package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forage.model.Devis;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
}

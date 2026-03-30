package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forage.model.DetailDevis;

@Repository
public interface DetailDevisRepository extends JpaRepository<DetailDevis, Long> {
}

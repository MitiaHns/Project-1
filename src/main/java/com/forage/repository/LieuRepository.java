package com.forage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forage.model.Lieu;

@Repository
public interface LieuRepository extends JpaRepository<Lieu, Long> {
}

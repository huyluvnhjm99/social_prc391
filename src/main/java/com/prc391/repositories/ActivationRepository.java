package com.prc391.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prc391.models.Activation;

@Repository
public interface ActivationRepository extends JpaRepository<Activation, String> {

}

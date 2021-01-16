package com.prc391.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prc391.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {}

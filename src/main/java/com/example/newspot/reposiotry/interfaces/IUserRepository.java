package com.example.newspot.reposiotry.interfaces;

import com.example.newspot.models.Db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> { }

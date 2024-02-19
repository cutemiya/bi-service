package com.example.newspot.reposiotry.interfaces;

import com.example.newspot.models.Db.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Integer> { }

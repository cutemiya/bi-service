package com.example.newspot.service;

import com.example.newspot.models.Db.Liked;
import com.example.newspot.models.Db.Post;
import com.example.newspot.reposiotry.interfaces.ILikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class LikeService {
    @Autowired
    private ILikeRepository likeRepository;

    @PersistenceContext
    private EntityManager entityManager;
    public void insert(Liked post) {
        likeRepository.insert(post.getAccountId(), post.getPostId(), post.getType());
    }

    public void delete(int accountId, int postId) {
        likeRepository.delete(accountId, postId);
    }

    public List<Liked> get(int id) {
        return likeRepository.get(id);
    }

    public Post readById(int id) {
        String hql = "SELECT e FROM Post e WHERE e.id = :id";
        TypedQuery<Post> query = entityManager.createQuery(hql, Post.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}

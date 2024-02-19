package com.example.newspot.service;

import com.example.newspot.models.Db.Post;
import com.example.newspot.reposiotry.interfaces.IPostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class PostService {
    @Autowired
    private IPostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Post post) {
        postRepository.save(post);
    }

    public List<Post> readAll() {
        return postRepository.findAll();
    }

    public Post read(int id) {
        return postRepository.getOne(id);
    }


    public boolean update(Post post, int id) {
        if (postRepository.existsById(id)) {
            post.setId(id);
            postRepository.save(post);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Post readById(int id) {
        String hql = "SELECT e FROM Post e WHERE e.id = :id";
        TypedQuery<Post> query = entityManager.createQuery(hql, Post.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<Post> getAllPosts() {
        String hql = "select p from Post p left join Liked l on p.id = l.postCommentId where l.postCommentId is null";
        TypedQuery<Post> q = entityManager.createQuery(hql, Post.class);
        return q.getResultList();
    }
}

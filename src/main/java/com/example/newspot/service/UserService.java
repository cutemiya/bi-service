package com.example.newspot.service;

import com.example.newspot.models.Db.User;
import com.example.newspot.reposiotry.interfaces.ILikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserService {
    @Autowired
    private ILikeRepository likeRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public User getUser(int id) {
        String hql = "SELECT u from User u where u.id = :id";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}

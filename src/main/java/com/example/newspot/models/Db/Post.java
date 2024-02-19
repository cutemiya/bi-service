package com.example.newspot.models.Db;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "likes")
    private int likes;

//    @ForeignKey(name = "accounts(id)")
    @Column(name = "account_id")
    private int accountId;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setAccountId(int id) {
        this.accountId = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getLikes() {
        return likes;
    }

    public com.example.newspot.models.Dto.Post MapToDomain() {
        var post = new com.example.newspot.models.Dto.Post();
        post.id = this.id;
        post.title = this.title;
        post.description = this.description;
        post.likes = this.likes;
        post.accountId = this.accountId;

        return post;
    }

    public int getAccountId() {
        return accountId;
    }
}

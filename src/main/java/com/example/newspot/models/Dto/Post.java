package com.example.newspot.models.Dto;

import jakarta.persistence.Column;

public class Post {
    public Integer id;

    @Column(name = "title")
    public String title;

    public String description;

    public int likes;

    public int accountId;

    public com.example.newspot.models.Db.Post MapToDb(int accountId) {
        var dbPost = new com.example.newspot.models.Db.Post();
        dbPost.setTitle(this.title);
        dbPost.setDescription(this.description);
        dbPost.setLikes(this.likes);
        dbPost.setAccountId(accountId);

        return dbPost;

    }

}

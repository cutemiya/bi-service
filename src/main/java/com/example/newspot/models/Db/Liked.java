package com.example.newspot.models.Db;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "liked")
public class Liked {
    @Id
    @Column(name="id")
    private int id;

    @Getter
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "post_comment_id")
    private int postCommentId;

    @Getter
    @Column(name = "type")
    private String type;

    public int getPostId() {
        return this.postCommentId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setPostCommentId(int postCommentId) {
        this.postCommentId = postCommentId;
    }

    public void setType(String type) {
        this.type = type;
    }

}

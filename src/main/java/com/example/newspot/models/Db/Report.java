package com.example.newspot.models.Db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "post_comment_id")
    private int postCommentId;

    @Column(name = "type")
    private String type;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}

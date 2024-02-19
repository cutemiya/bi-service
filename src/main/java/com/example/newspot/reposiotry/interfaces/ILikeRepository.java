package com.example.newspot.reposiotry.interfaces;

import com.example.newspot.models.Db.Liked;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ILikeRepository extends JpaRepository<Liked, Integer> {
    @Modifying
    @Query(value = "insert into liked (account_id,post_comment_id,type) VALUES (:accId,:postId,:type)", nativeQuery = true)
    @Transactional
    void insert(@Param("accId") int accId, @Param("postId") int postId, @Param("type") String type);

    @Query(value = "select * from liked where account_id = :accId", nativeQuery = true)
    List<Liked> get(@Param("accId") int accId);

    @Modifying
    @Transactional
    @Query(value = "delete from liked where account_id = :accId and post_comment_id = :post", nativeQuery = true)
    void delete(@Param("accId") int accId, @Param("post") int post);
}

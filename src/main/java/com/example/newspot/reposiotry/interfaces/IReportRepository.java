package com.example.newspot.reposiotry.interfaces;

import com.example.newspot.models.Db.Report;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IReportRepository extends JpaRepository<Report, Integer> {
    @Modifying
    @Query(value = "insert into reports (account_id,post_comment_id,type) VALUES (:accId,:postId,:type)", nativeQuery = true)
    @Transactional
    void insert(@Param("accId") int accId, @Param("postId") int postId, @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "delete from reports where post_comment_id = :post", nativeQuery = true)
    void delete(@Param("post") int post);
}

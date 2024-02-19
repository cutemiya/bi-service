package com.example.newspot.service;

import com.example.newspot.models.Db.Report;
import com.example.newspot.reposiotry.interfaces.IReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class ReportService {
    @Autowired
    private IReportRepository reportRepository;

    @PersistenceContext
    private EntityManager entityManager;
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    public void deleteReport(int postId) {
        reportRepository.delete(postId);
    }
    public void insertReport(Report report) {
        reportRepository.insert(report.getAccountId(), report.getPostCommentId(), report.getType());
    }
}

package com.example.newspot.controller;

import com.example.newspot.models.Db.Report;
import com.example.newspot.models.Dto.AllPosts;
import com.example.newspot.models.Dto.Posts;
import com.example.newspot.service.PostService;
import com.example.newspot.service.ReportService;
import com.example.newspot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final PostService postService;

    public ReportController(ReportService reportService, UserService userService, PostService postService) {
        this.reportService = reportService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(path = {"/reports"})
    public AllPosts GetAllReports(HttpServletRequest request) throws BadRequestException {
        var ctx = request.getServletContext();
        var role = ctx.getAttribute("role").toString();

        if (!Objects.equals(role, "moder")) {
            throw new BadRequestException("Sorry u not a moder");
        }

        var res = new AllPosts();
        var reports = reportService.getAllReports();

        res.posts = new ArrayList<>();

        for (var report : reports) {
            var post = postService.readById(report.getPostCommentId());
            var user = userService.getUser(report.getAccountId());

            var newPosts = new Posts();
            newPosts.username = user.getUsername();
            newPosts.email = user.getEmail();
            newPosts.title = post.getTitle();
            newPosts.description = post.getDescription();
            newPosts.likes = post.getLikes();

            res.posts.add(newPosts);
        }

        return res;
    }

    @PostMapping(path = {"/report", "/report/{postId}"})
    public void NewReport(@PathVariable(name="postId") int postId, HttpServletRequest request) {
        var ctx = request.getServletContext();
        var accountId = Integer.parseInt(ctx.getAttribute("accountId").toString());

        var report = new Report();
        report.setType("post");
        report.setAccountId(accountId);
        report.setPostCommentId(postId);

        reportService.insertReport(report);
    }

    @DeleteMapping(path = {"/unreport", "/unreport/{postId}"})
    public void RemoveReport(@PathVariable(name="postId") int postId, HttpServletRequest request) throws BadRequestException {
        var ctx = request.getServletContext();
        var role = ctx.getAttribute("role").toString();

        if (!Objects.equals(role, "moder")) {
            throw new BadRequestException("Sorry u not a moder");
        }

        reportService.deleteReport(postId);
    }
}

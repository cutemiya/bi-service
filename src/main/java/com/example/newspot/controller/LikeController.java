package com.example.newspot.controller;

import com.example.newspot.models.Db.Liked;
import com.example.newspot.models.Dto.AllPosts;
import com.example.newspot.models.Dto.Posts;
import com.example.newspot.service.LikeService;
import com.example.newspot.service.PostService;
import com.example.newspot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping(path = {"/liked"})
    public AllPosts GetAllLikes(HttpServletRequest request) throws BadRequestException {
        var ctx = request.getServletContext();
        var accountId = Integer.parseInt(ctx.getAttribute("accountId").toString());

        var liked = likeService.get(accountId);
        var res = new AllPosts();
        res.posts = new ArrayList<>();

        for (var likePost: liked) {
           var post = postService.readById(likePost.getPostId());
           var user = userService.getUser(likePost.getAccountId());

           var posts = new Posts();
           posts.title = post.getTitle();
           posts.description = post.getDescription();
           posts.username = user.getUsername();
           posts.email = user.getEmail();
           posts.likes = post.getLikes();

           res.posts.add(posts);
        }

        return res;
    }

    @PostMapping(path = {"/like", "/like/{postId}"})
    public void LikePost(@PathVariable(name="postId") int postId, HttpServletRequest request) {
        var ctx = request.getServletContext();
        var accountId = Integer.parseInt(ctx.getAttribute("accountId").toString());

        var post = new Liked();
        post.setPostCommentId(postId);
        post.setAccountId(accountId);
        post.setType("post");

        likeService.insert(post);
    }

    @DeleteMapping(path = {"/unlike", "unlike/{postId}"})
    public void UnlikePost(@PathVariable(name="postId") int postId, HttpServletRequest request) {
        var ctx = request.getServletContext();
        var accountId = Integer.parseInt(ctx.getAttribute("accountId").toString());

        likeService.delete(accountId, postId);
    }
}

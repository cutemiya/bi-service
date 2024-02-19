package com.example.newspot.controller;

import com.example.newspot.models.Dto.AllPosts;
import com.example.newspot.models.Dto.Post;
import com.example.newspot.models.Dto.Posts;
import com.example.newspot.service.PostService;
import com.example.newspot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@EnableAutoConfiguration
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/post")
    public Post CreatePost(HttpServletRequest request, @RequestBody Post post) {
        var ctx = request.getServletContext();
        postService.create(post.MapToDb(Integer.parseInt(ctx.getAttribute("accountId").toString())));

        return post;
    }

    @DeleteMapping(path = {"/post", "/post/{id}"})
    public boolean DeletePost(@PathVariable(name="id") int id, HttpServletRequest request) throws BadRequestException {
        var ctx = request.getServletContext();
        var accountId = Integer.parseInt(ctx.getAttribute("accountId").toString());
        var role = ctx.getAttribute("role").toString();

        if (role.equals("admin"))
            postService.delete(id);

        var post = postService.readById(id).MapToDomain();

        if (post.accountId != accountId)
            throw new BadRequestException("error");

        postService.delete(id);

        return true;
    }

    @GetMapping(path={"/post/all"})
    public AllPosts GetAllPosts() {
        var posts = postService.getAllPosts();
        var res = new AllPosts();
        res.posts = new ArrayList<>();

        for (var post: posts) {
            var newPost = new Posts();
            newPost.title = post.getTitle();
            newPost.description = post.getDescription();
            newPost.likes = post.getLikes();

            var user = userService.getUser(post.getAccountId());

            newPost.email = user.getEmail();
            newPost.username = user.getUsername();

            res.posts.add(newPost);
        }

        return res;
    }
}

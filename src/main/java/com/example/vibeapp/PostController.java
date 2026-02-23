package com.example.vibeapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/new")
    public String newPostForm() {
        return "post_new_form";
    }

    @GetMapping("/posts/{no}")
    public String getPostDetail(@PathVariable("no") Long no, Model model) {
        Post post = postService.getPostByNo(no);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post_detail";
    }

    @PostMapping("/posts/add")
    public String addPost(@RequestParam("title") String title, @RequestParam("content") String content) {
        postService.addPost(title, content);
        return "redirect:/posts";
    }
}

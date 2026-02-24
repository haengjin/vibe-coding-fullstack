package com.example.vibeapp.post;

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
    public String listPosts(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int pageSize = 5;
        List<Post> posts = postService.findPagedPosts(page, pageSize);
        int totalPages = postService.getTotalPages(pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "post/posts";
    }

    @GetMapping("/posts/new")
    public String newPostForm() {
        return "post/post_new_form";
    }

    @GetMapping("/posts/{no}")
    public String getPostDetail(@PathVariable("no") Long no, Model model) {
        Post post = postService.findById(no);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post/post_detail";
    }

    @GetMapping("/posts/{no}/edit")
    public String editPostForm(@PathVariable("no") Long no, Model model) {
        Post post = postService.findById(no);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post/post_edit_form";
    }

    @PostMapping("/posts/add")
    public String addPost(@RequestParam("title") String title, @RequestParam("content") String content) {
        postService.create(title, content);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{no}/save")
    public String savePost(@PathVariable("no") Long no, @RequestParam("title") String title,
            @RequestParam("content") String content) {
        postService.update(no, title, content);
        return "redirect:/posts/" + no;
    }

    @GetMapping("/posts/{no}/delete")
    public String deletePost(@PathVariable("no") Long no) {
        postService.delete(no);
        return "redirect:/posts";
    }
}

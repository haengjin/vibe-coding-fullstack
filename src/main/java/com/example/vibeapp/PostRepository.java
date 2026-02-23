package com.example.vibeapp;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public java.util.Optional<Post> findByNo(Long no) {
        return posts.stream().filter(p -> p.getNo().equals(no)).findFirst();
    }

    public void save(Post post) {
        posts.add(post);
    }
}

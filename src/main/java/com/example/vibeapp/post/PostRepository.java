package com.example.vibeapp.post;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostRepository {

    List<Post> findAll();

    List<Post> findPaged(@Param("offset") int offset, @Param("size") int size);

    Long countAll();

    Post findById(@Param("id") Long id);

    int increaseViews(@Param("id") Long id);

    int save(Post post);

    int update(Post post);

    int deleteById(@Param("id") Long id);
}

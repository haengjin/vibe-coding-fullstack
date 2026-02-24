package com.example.vibeapp.post;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostTagRepository {

    int addTag(PostTag postTag);

    List<PostTag> findByPostNo(@Param("postNo") Long postNo);

    int deleteTagsByPostNo(@Param("postNo") Long postNo);

    int deleteTag(@Param("id") Long id);
}

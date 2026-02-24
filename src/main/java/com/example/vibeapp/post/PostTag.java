package com.example.vibeapp.post;

public class PostTag {
    private Long id;
    private Long postNo;
    private String tagName;

    public PostTag() {
    }

    public PostTag(Long id, Long postNo, String tagName) {
        this.id = id;
        this.postNo = postNo;
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostNo() {
        return postNo;
    }

    public void setPostNo(Long postNo) {
        this.postNo = postNo;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

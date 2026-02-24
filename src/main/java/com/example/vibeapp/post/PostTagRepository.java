package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostTagRepository {

    @PersistenceContext
    private EntityManager em;

    public int addTag(PostTag postTag) {
        // 신규 태그 엔티티를 persist로 저장한다.
        em.persist(postTag);
        return 1;
    }

    public List<PostTag> findByPostNo(Long postNo) {
        // JPQL로 게시글 번호에 매핑된 태그 목록을 조회한다.
        return em.createQuery(
                        "SELECT t FROM PostTag t WHERE t.postNo = :postNo ORDER BY t.id ASC",
                        PostTag.class)
                .setParameter("postNo", postNo)
                .getResultList();
    }

    public int deleteTagsByPostNo(Long postNo) {
        // JPQL bulk delete로 게시글의 태그를 한 번에 삭제한다.
        return em.createQuery("DELETE FROM PostTag t WHERE t.postNo = :postNo")
                .setParameter("postNo", postNo)
                .executeUpdate();
    }

    public int deleteTag(Long id) {
        // 기본 키 조회 후 remove로 단건 삭제한다.
        Optional<PostTag> found = Optional.ofNullable(em.find(PostTag.class, id));
        if (found.isEmpty()) {
            return 0;
        }
        em.remove(found.get());
        return 1;
    }
}

package com.example.vibeapp.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Post> findAll() {
        // JPQL로 엔티티 목록을 조회한다.
        return em.createQuery("SELECT p FROM Post p ORDER BY p.no DESC", Post.class)
                .getResultList();
    }

    public List<Post> findPaged(int offset, int size) {
        // JPQL + 페이징으로 필요한 구간만 조회한다.
        return em.createQuery("SELECT p FROM Post p ORDER BY p.no DESC", Post.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public Long countAll() {
        // JPQL count 쿼리로 전체 개수를 조회한다.
        return em.createQuery("SELECT COUNT(p) FROM Post p", Long.class)
                .getSingleResult();
    }

    public Optional<Post> findById(Long id) {
        // 기본 키 조회는 영속성 컨텍스트를 우선 활용한다.
        return Optional.ofNullable(em.find(Post.class, id));
    }

    public int increaseViews(Long id) {
        // 조회된 엔티티의 필드 변경은 Dirty Checking으로 반영된다.
        Optional<Post> found = findById(id);
        if (found.isEmpty()) {
            return 0;
        }
        Post post = found.get();
        post.setViews(post.getViews() + 1);
        return 1;
    }

    public int save(Post post) {
        // 신규 엔티티는 persist로 영속성 컨텍스트에 등록한다.
        em.persist(post);
        return 1;
    }

    public int update(Post post) {
        // 준영속 엔티티 상태는 merge로 병합해 업데이트한다.
        em.merge(post);
        return 1;
    }

    public int deleteById(Long id) {
        // 관리 상태 엔티티를 remove 하면 삭제 SQL이 반영된다.
        Optional<Post> found = findById(id);
        if (found.isEmpty()) {
            return 0;
        }
        em.remove(found.get());
        return 1;
    }
}

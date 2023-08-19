package com.plogcareers.backend.blog.repository;

import com.plogcareers.backend.blog.domain.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class VHotPostingRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;
    private final QVHotPosting qHotPosting = QVHotPosting.vHotPosting;

    public VHotPostingRepositorySupport(EntityManager entityManager) {
        super(Posting.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<VHotPosting> listFollowingHomePostings(List<Long> followingIDs, String search, Long lastCursorID, Long pageSize) {
        return queryFactory.selectFrom(
                        qHotPosting
                )
                .where(
                        ltPostingID(lastCursorID),
                        qHotPosting.stateID.eq(State.PUBLIC),
                        qHotPosting.blogID.in(followingIDs),
                        qHotPosting.title.upper().contains(search.toUpperCase())
                                .or(qHotPosting.mdContent.upper().contains(search.toUpperCase()))
                )
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression ltPostingID(Long cursorID) {
        if (cursorID == null) {
            return null;
        }
        return qHotPosting.id.lt(cursorID);
    }
}
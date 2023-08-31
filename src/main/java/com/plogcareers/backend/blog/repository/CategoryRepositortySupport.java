package com.plogcareers.backend.blog.repository;

import com.plogcareers.backend.blog.domain.entity.Category;
import com.plogcareers.backend.blog.domain.entity.QCategory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CategoryRepositortySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final QCategory qCategory = QCategory.category;

    public CategoryRepositortySupport(EntityManager entityManager) {
        super(Category.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Boolean existsDuplicatedCategory(Long blogID, Long categoryID, String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return false;
        }
        BooleanBuilder where = new BooleanBuilder();

        where.and(qCategory.blog.id.eq(blogID));
        where.and(qCategory.categoryName.eq(categoryName));


        Category dupCategory = queryFactory.selectFrom(qCategory).where(where).fetchFirst();

        if (dupCategory == null) {
            return false;
        }

        return !dupCategory.getId().equals(categoryID);
    }
}

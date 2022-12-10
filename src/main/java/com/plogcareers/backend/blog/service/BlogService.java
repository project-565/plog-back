package com.plogcareers.backend.blog.service;

import com.plogcareers.backend.blog.domain.dto.CreateCategoryRequest;
import com.plogcareers.backend.blog.domain.dto.ListCategoriesResponse;
import com.plogcareers.backend.blog.domain.dto.UpdateCategoryRequest;
import com.plogcareers.backend.blog.domain.entity.Blog;
import com.plogcareers.backend.blog.domain.entity.Category;
import com.plogcareers.backend.blog.exception.*;
import com.plogcareers.backend.blog.repository.BlogRepository;
import com.plogcareers.backend.blog.repository.CategoryRepositortySupport;
import com.plogcareers.backend.blog.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryRepositortySupport categoryRepositortySupport;

    // 카테고리 생성하기
    public void createCategory(Long blogID, Long loginedUserID, @NotNull CreateCategoryRequest createCategoryRequest) throws BlogNotFoundException, CategoryDuplicatedException {
        Blog blog = blogRepository.findById(blogID).orElseThrow(BlogNotFoundException::new);

        if (!blog.isOwner(loginedUserID)) {
            throw new NotProperAuthorityException();
        }

        if (categoryRepository.existsByBlogAndCategoryName(blog, createCategoryRequest.getCategoryName())) {
            throw new CategoryDuplicatedException();
        }

        categoryRepository.save(createCategoryRequest.toEntity(blog));
    }

    // 카테고리 가져오기
    public ListCategoriesResponse listCategories(Long blogID) throws BlogNotFoundException {
        if (!blogRepository.existsById(blogID)) {
            throw new BlogNotFoundException();
        }
        List<Category> categories = categoryRepository.findCategoryByBlogIdOrderByCategoryName(blogID);

        return new ListCategoriesResponse(categories.stream().map(Category::toCategoryDto).toList());
    }

    // 카테고리 수정하기
    @Transactional
    public void updateCategory(Long blogID, Long loginedUserID, @NotNull UpdateCategoryRequest request) throws BlogNotFoundException, CategoryNotFoundException, NotProperAuthorityException {
        Blog blog = blogRepository.findById(blogID).orElseThrow(BlogNotFoundException::new);
        Category category = categoryRepository.findById(request.getId()).orElseThrow(CategoryNotFoundException::new);
        if (!blog.isOwner(loginedUserID)) {
            throw new NotProperAuthorityException();
        }
        if (!category.isOwner(loginedUserID)) {
            throw new CategoryBlogMismatchedException();
        }
        if (categoryRepositortySupport.existsDuplicatedCategory(blogID, request.getId(), request.getCategoryName())) {
            throw new CategoryDuplicatedException();
        }
        categoryRepository.save(request.toCategoryEntity(category, blog));
    }

    // 카테고리 삭제하기
    @Transactional
    public void deleteCategory(Long blogID, Long categoryID, Long loginedUserID) throws BlogNotFoundException, CategoryNotFoundException {
        Blog blog = blogRepository.findById(blogID).orElseThrow(BlogNotFoundException::new);
        Category category = categoryRepository.findById(categoryID).orElseThrow(CategoryNotFoundException::new);
        if (!blog.isOwner(loginedUserID)) {
            throw new NotProperAuthorityException();
        }
        if (!category.isOwner(loginedUserID)) {
            throw new NotProperAuthorityException();
        }
        categoryRepository.deleteCategoryById(categoryID);
    }
}


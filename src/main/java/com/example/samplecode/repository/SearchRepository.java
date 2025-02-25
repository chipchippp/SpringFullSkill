package com.example.samplecode.repository;

import com.example.samplecode.dto.response.PageResponse;
import com.example.samplecode.dto.response.UserDetailResponse;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.example.samplecode.util.AppConst.SORT_BY;

@Slf4j
@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String LIKE_FORMAT = "%%%s%%";


    public PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy) {
        log.info("Get all users WithSortByMultipleColumns");

        StringBuilder sqlQuery = new StringBuilder("SELECT new com.example.samplecode.dto.response.UserDetailResponse(u.id, u.firstName, u.lastName, u.email) FROM User u WHERE 1=1");
        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" and (lower(u.firstName) like lower(:search)");
            sqlQuery.append(" or lower(u.lastName) like lower(:search)");
            sqlQuery.append(" or lower(u.email) like lower(:search))");
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                sqlQuery.append(String.format(" order by u.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult(pageNo * pageSize);
        selectQuery.setMaxResults(pageSize);

        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("search", String.format(LIKE_FORMAT, search));
        }

        List users = selectQuery.getResultList();

        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(u) FROM User u WHERE 1=1");
        if (StringUtils.hasLength(search)) {
            sqlCountQuery.append(" and (lower(u.firstName) like lower(:search)");
            sqlCountQuery.append(" or lower(u.lastName) like lower(:search)");
            sqlCountQuery.append(" or lower(u.email) like lower(:search))");
        }

        Query selectCountQuery = entityManager.createQuery(sqlCountQuery.toString());
        if (StringUtils.hasLength(search)) {
            selectCountQuery.setParameter("search", String.format(LIKE_FORMAT, search));
        }

        Long totalElements = (Long) selectCountQuery.getSingleResult();

        Page<UserDetailResponse> page = new PageImpl<>(users, PageRequest.of(pageNo, pageSize), totalElements);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(page.getTotalPages())
                .items(page.getContent())
                .build();
    }

    public PageResponse<?> advanceSearchUser(int pageNo, int pageSize, String search, String sortBy){

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(page.getTotalPages())
                .items(page.getContent())
                .build();
    }
}
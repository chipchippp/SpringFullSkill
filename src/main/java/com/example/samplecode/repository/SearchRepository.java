package com.example.samplecode.repository;

import com.example.samplecode.dto.response.PageResponse;
import com.example.samplecode.dto.response.UserDetailResponse;
import com.example.samplecode.model.Address;
import com.example.samplecode.model.User;
import com.example.samplecode.repository.criteria.SearchCriteria;
import com.example.samplecode.repository.criteria.UserSearchCriteriaQueryConsumer;
import jakarta.persistence.Persistence;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
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

        StringBuilder sqlQuery = new StringBuilder("SELECT new com.example.samplecode.dto.response.UserDetailResponse(u.id, u.firstName, u.lastName, u.email) FROM tbl_user u WHERE 1=1");
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

        StringBuilder sqlCountQuery = new StringBuilder("SELECT COUNT(u) FROM tbl_user u WHERE 1=1");
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

    public PageResponse<?> searchUserByCriteria(int pageNo, int pageSize, String sortBy, String address, String... search){

        List<SearchCriteria> criteriaList = new ArrayList<>();

        if (search != null){
            for (String s : search){
                Pattern pattern = Pattern.compile(SORT_BY);
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        List<User> users = getUsers(pageNo, pageSize, sortBy, criteriaList, address);

        Long totalElements = getTotalElements(criteriaList, address);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(totalElements.intValue())
                .items(users)
                .build();
    }

    private Long getTotalElements(List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        // xử lý các điều kiện tìm kiếm
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaQueryConsumer consumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder, root, predicate);

        if (StringUtils.hasLength(address)) {
            Join<Address, User> join = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.like(join.get("city"), "%" + address + "%");
            // tìm kiếm tất cả các field của address
            query.select(criteriaBuilder.count(root));
            query.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(consumer);
            predicate = consumer.getPredicate();
            query.select(criteriaBuilder.count(root));
            query.where(predicate);
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    private List<User> getUsers(int pageNo, int pageSize, String sortBy, List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

//        xử lý các điều kiện tìm kiếm
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaQueryConsumer consumer = new UserSearchCriteriaQueryConsumer(criteriaBuilder, root, predicate);

        if (StringUtils.hasLength(address)){
            Join<Address, User> join = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.like(join.get("city"), "%" + address + "%");
//            tìm kiếm tất cả các field của address
            query.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(consumer);
            predicate = consumer.getPredicate();
            query.where(predicate);
        }

//        xử lý sắp xếp
        if (StringUtils.hasLength(sortBy)){
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()){
                String column = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")){
                    query.orderBy(criteriaBuilder.asc(root.get(column)));
                }else {
                    query.orderBy(criteriaBuilder.desc(root.get(column)));
                }
            }
        }
        return entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
    }
}
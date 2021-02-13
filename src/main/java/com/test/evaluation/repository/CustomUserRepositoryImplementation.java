package com.test.evaluation.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserRepositoryImplementation implements CustomUserRepository {

    private final EntityManager entityManager;

    private static final String DELETE_STMT = "DELETE FROM favorite_list WHERE user_id =  (:userid) AND favorite=(:favorite)";
    private static final String INSERT_STMT = "INSERT INTO favorite_list(user_id, favorite) values ( (:userid),(:favorite))";

    @Override
    public void updateFavorite(Long UserId, Long mediaId, String op) {
     if(op.equalsIgnoreCase("delete"))
   {
    StringBuilder query = new StringBuilder(DELETE_STMT);
    Query nativeQuery = entityManager.createNativeQuery(query.toString());
    nativeQuery.setParameter("userid", UserId);
    nativeQuery.setParameter("favorite", mediaId);
    nativeQuery.executeUpdate();
  }
  else
  {
    StringBuilder query = new StringBuilder(INSERT_STMT);
    Query nativeQuery = entityManager.createNativeQuery(query.toString());
    nativeQuery.setParameter("userid", UserId);
    nativeQuery.setParameter("favorite", mediaId);
    nativeQuery.executeUpdate();
  }

    }
}

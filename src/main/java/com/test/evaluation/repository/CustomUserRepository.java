package com.test.evaluation.repository;

public interface CustomUserRepository {


    void updateFavorite(Long UserId, Long mediaId, String op);
}

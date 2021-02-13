package com.test.evaluation.services;

import com.test.evaluation.entity.User;

public interface UserService {
    User getUserById(Long userId);

    User favoriteUpdate(Long userId, Long mediaId, String operation);

    Long gettingUniqueFavoriteFromAllUser();
}

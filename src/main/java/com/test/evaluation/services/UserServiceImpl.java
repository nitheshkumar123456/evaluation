package com.test.evaluation.services;

import com.test.evaluation.constant.HttpStatusString;
import com.test.evaluation.entity.User;
import com.test.evaluation.exception.GeneralEvaluationException;
import com.test.evaluation.repository.CustomUserRepository;
import com.test.evaluation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    CustomUserRepository customUserRepository;

    private static Random random  = new Random();

    @Override
    public User getUserById(Long userId)
    {
        Optional<User> userModel = userRepository.findById(userId);

        if (!userModel.isPresent()) {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "The requested user was not found");
        }
        return userModel.get();
    }

    @Override
    public User favoriteUpdate(Long userId, Long mediaId, String operation)
    {
        if(operation.equalsIgnoreCase("delete") ||operation.equalsIgnoreCase("add") )
        {
              customUserRepository.updateFavorite(userId,mediaId,operation);
              return  userRepository.findById(userId).get();
        }
        else
        {
            throw new GeneralEvaluationException(HttpStatus.BAD_REQUEST, HttpStatusString.BAD_REQUEST,
                    "Not a specified operation");
        }


    }

    @Override
    public Long gettingUniqueFavoriteFromAllUser()
    {
        List<Long> mediaIds=new ArrayList<Long>();
        //fetching all list of user from user table
       List<User> userList=userRepository.findAll();
       //i have added favoroti in evry user so adding all the favorite ids into some list
       userList.stream().map(x-> mediaIds.addAll(x.getFavorite())).collect(Collectors.toList());
        //two user may contain duplicate favorite making it unique by adding set
       Set<Long> set = new HashSet<Long>(mediaIds);

        mediaIds.clear();
        // moving unique favorite ids to list
        mediaIds.addAll(set);
        //fetching a random id from favorite list of all user send back as trending media id
        Long randomElement = mediaIds.get(random.nextInt(mediaIds.size()));
        return randomElement;
    }
}

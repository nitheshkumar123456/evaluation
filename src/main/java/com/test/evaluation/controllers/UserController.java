package com.test.evaluation.controllers;

import com.test.evaluation.entity.User;
import com.test.evaluation.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("favorite/{userid}/{mediaid}")
    @ApiOperation("method can be used for adding and deletin favorite of a user")
    public ResponseEntity<User> updateFavoriteForUser(@PathVariable("userid") Long userId, @PathVariable("mediaid") Long mediaId,
                                                      @RequestParam("operation") String operation){

        return new ResponseEntity<User>(userService.favoriteUpdate(userId,mediaId,operation), HttpStatus.OK);
    }
    @GetMapping("getUserById/{userid}")
    @ApiOperation("getting user by userid")
    public ResponseEntity<User> getUserById( @PathVariable("userid") Long UserId
                                                     ){

        return new ResponseEntity<User>(userService.getUserById(UserId), HttpStatus.OK);
    }


}

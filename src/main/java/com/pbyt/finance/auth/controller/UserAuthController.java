package com.pbyt.finance.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pbyt.finance.auth.model.UserLoginModel;
import com.pbyt.finance.auth.model.UserLoginResponse;
import com.pbyt.finance.auth.service.AuthService;
import com.pbyt.finance.exception.InvalidCredential;
import com.pbyt.finance.exception.NotFound;
import com.pbyt.finance.global.model.MessageResponse;
import com.pbyt.finance.user.entity.TblUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody @Validated UserLoginModel userLoginModel) throws NotFound, InvalidCredential, JsonProcessingException {
        Optional<TblUser> user = authService.checkRegister(userLoginModel.getMobileNumber());
        if (user.isEmpty()) throw new NotFound("User Not Found");
        UserLoginResponse response = authService.login(userLoginModel, user.get());
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Login Successfully")
                .status(HttpStatus.OK)
                .data(response)
                .build());
    }
}

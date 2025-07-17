package org.example.postservice.config;

import org.example.postservice.model.dto.APIResponse;
import org.example.postservice.model.dto.orther.UserInforResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.UUID;

@FeignClient(name = "auth-service")
public interface UserClient {
    @GetMapping("/auth-service/users/infor/{id}")
    APIResponse<UserInforResponse> getUserById(@PathVariable("id") UUID id);
}



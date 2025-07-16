package org.example.postservice.config;

import org.example.postservice.model.dto.orther.UserInforResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service") // Tên service hoặc URL tùy config
public interface UserClient {

    @GetMapping("/users/infor/{id}")
    UserInforResponse getUserById(@PathVariable("id") UUID id);
}

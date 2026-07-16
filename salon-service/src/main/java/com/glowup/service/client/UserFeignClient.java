package com.glowup.service.client;

import com.glowup.Payload.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:5001")
public interface UserFeignClient {

    @GetMapping("/api/user/profile")
    ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception;

    @GetMapping("/api/user/{id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) throws Exception;

}

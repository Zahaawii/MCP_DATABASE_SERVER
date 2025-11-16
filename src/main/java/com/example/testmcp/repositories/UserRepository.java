package com.example.testmcp.repositories;

import com.example.testmcp.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    UserInfo findUserByUserId(Long id);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByName(String username);
}

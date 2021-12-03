package com.example.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserModelRepository extends CrudRepository<UserModel, Integer>{
    List<UserModel> findByEmail(String email);
}

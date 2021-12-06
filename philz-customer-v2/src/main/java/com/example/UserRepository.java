package com.example;

import org.springframework.data.jpa.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	//@Query("FROM User WHERE email=:email")
	User findByEmail(@Param("email") String email);
}
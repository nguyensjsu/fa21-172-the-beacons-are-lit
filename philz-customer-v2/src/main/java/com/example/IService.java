package com.example;
import java.util.Collection;
import java.util.Optional;

public interface IService<T> {
	Collection<T> findAll();
	
	Optional<T> findById(Integer id);
	
	T saveOrUpdate(T t);
	
	String deleteById(Integer id);
}
package com.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(indexes = @Index(name = "altIndex", columnList = "email", unique = true)) //use email as secondary key
@Data
@RequiredArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String mobile;
	@Column(nullable = false)
	private String password;
	@Getter
	@Setter
	@Column(nullable = false)
	private String securityQuestionAnswer; 

}
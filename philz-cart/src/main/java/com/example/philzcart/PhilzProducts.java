package com.example.philzcart;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="PhilzProducts" )
@Data
@RequiredArgsConstructor
public class PhilzProducts {
	
	private @Id @GeneratedValue Long id;
	@Column(nullable = false)
	private int productID; 
	@Column(nullable = false)
	private String blendType;
	@Column(nullable = false) 
	private String name;
	@Column(nullable = false)
	private String roast;
	final double price = 18.50;

}
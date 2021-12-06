package com.example.philzproduct;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="PhilzProducts" )
@Data
@RequiredArgsConstructor
public class PhilzProducts {
	
	private @Id @GeneratedValue Long id;
	private String productID;
	private String blendType;
	private String name;
//	private String roast;
	final double price = 18.50;

}
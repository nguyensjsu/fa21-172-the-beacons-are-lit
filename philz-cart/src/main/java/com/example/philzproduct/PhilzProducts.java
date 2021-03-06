package com.example.philzproduct;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="PhilzProducts" )
@Data
@RequiredArgsConstructor
public class PhilzProducts implements Serializable {
	
	private @Id @GeneratedValue Long id;
	private String productID;
	private String blendType;
	private String name;
//	private String roast;
	final double price = 18.50;

}
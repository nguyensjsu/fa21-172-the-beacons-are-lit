package com.example.philzcart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@Table(name="PhilzProducts")
@Data
@RequiredArgsConstructor
public class PhilzProducts {
	@Id @GeneratedValue
	long id;
	String name;
	String roast;
	final double price = 18.50;

}
package com.banking.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountNumber;
	private String accountType;
	private Double accountBalance;
	
	@Temporal(TemporalType.TIME)
	private Date createdDateTime;
	
	@Temporal(TemporalType.TIME)
	private Date updatedDateTime;
	
	@OneToOne(mappedBy = "account")
	@JsonBackReference
	private User user;

}

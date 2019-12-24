package com.okane.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class RegisterForm {

	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	@NotNull
	private int salary;
	@NotNull
	private int reserve;
}

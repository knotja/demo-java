package com.okane.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UpdateForm {
	@NotEmpty
	private String name;
	@NotEmpty
	private String password;
	@NotNull
	private int salary;
}

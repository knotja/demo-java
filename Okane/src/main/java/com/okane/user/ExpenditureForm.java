package com.okane.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ExpenditureForm {
	@NotEmpty
	private String type;
	@NotEmpty
	private String detail;
	@NotNull
	private int value;
}

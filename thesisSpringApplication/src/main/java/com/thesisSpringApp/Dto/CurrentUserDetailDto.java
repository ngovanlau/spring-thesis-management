package com.thesisSpringApp.Dto;

import com.thesisSpringApp.pojo.Faculty;
import com.thesisSpringApp.pojo.Role;
import com.thesisSpringApp.pojo.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentUserDetailDto {

	private User user;
	private Faculty faculty;
	private Role role;

	public CurrentUserDetailDto(User user, Faculty faculty, Role role) {
		this.user = user;
		this.faculty = faculty;
		this.role = role;
	}
}

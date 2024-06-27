package com.thesisSpringApp.Dto;

import java.util.List;

import com.thesisSpringApp.pojo.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListsByRoleDTO {
	private List<User> usersGiangVien;;
	private List<User> usersSinhVien;
}

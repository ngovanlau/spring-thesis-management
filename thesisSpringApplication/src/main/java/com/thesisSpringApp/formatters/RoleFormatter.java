package com.thesisSpringApp.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.thesisSpringApp.pojo.Role;

/* Lớp này đc tạo ra khi packing-bean để biến modelattribute 
 biết đc là sử dụng khóa chính (id) của các pojo trong trường khóa ngoại*/

public class RoleFormatter implements Formatter<Role> {

    @Override
	public String print(Role role, Locale locale) {
		return String.valueOf(role.getId());
    }

    @Override
	public Role parse(String roleId, Locale locale) throws ParseException {
		Role r = new Role();
		r.setId(Integer.parseInt(roleId));
		return r;
    }
    
}

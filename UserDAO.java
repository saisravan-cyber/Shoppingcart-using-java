




package com.niit.shoppingcart.dao;

import org.springframework.stereotype.Component;

import com.niit.shoppingcart.model.User;

@Component
public interface UserDAO 
{
	public boolean saveUser(User user);
}

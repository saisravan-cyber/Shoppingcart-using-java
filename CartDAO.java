package com.niit.shoppingcart.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.niit.shoppingcart.model.Cart;

@Component
public interface CartDAO 
{
	public boolean add(Cart item);
	public Cart get(int id);
	public List getAllItems();
	public void remove(int id);
	public void removeAll();
	//public void update(int cartItemId,int quantity);
		
}
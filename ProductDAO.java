package com.niit.shoppingcart.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.niit.shoppingcart.model.Product;

@Component
public interface ProductDAO 
{
	public boolean addPro(Product product);
	public boolean updatePro(Product product);
	public List getAllPro();
	public Product getSinglePro(int id);
	public void deletePro(int id);

}

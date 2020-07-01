




package com.niit.shoppingcart.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.shoppingcart.dao.UserDAO;
import com.niit.shoppingcart.model.User;

@Repository("userDAO")
@Component
@Transactional
public class UserDAOimpl implements UserDAO
{
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public UserDAOimpl(SessionFactory sessionFactory)
	{
		
		try 
		{
			this.sessionFactory = sessionFactory;
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

	}



	@Transactional
	public boolean saveUser(User user) 
	{
		
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		}
}

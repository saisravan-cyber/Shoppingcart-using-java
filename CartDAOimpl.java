package com.niit.shoppingcart.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.shoppingcart.dao.CartDAO;
import com.niit.shoppingcart.model.Cart;

@Repository("cartDAO")
public class CartDAOimpl implements CartDAO
{
	//private static final Logger logger = LoggerFactory.getLogger(CartDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean add(Cart item) 
	{
		//logger.debug("Starting of the method add");
		try 
		{
			sessionFactory.getCurrentSession().saveOrUpdate(item);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public Cart get(int id)
	{
		//logger.debug("Starting of the method get");
		Session session = sessionFactory.getCurrentSession();
		//logger.debug("End of the method get");
		return (Cart) session.get(Cart.class, id);		
	}
	@Transactional
	public List getAllItems()
	{
		//logger.debug("Starting of the method getAllItems");
        Session session =sessionFactory.openSession();
        List blist1 = session.createQuery("from Cart").list();
        //logger.debug("End of the method getAllItems");
        return blist1;
	
	}
	@Transactional
	public void remove(int id)
	{
		//logger.debug("Starting of the method remove");
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		Cart chartItem=(Cart)session.load(Cart.class, id);
		session.delete(chartItem);	
		tx.commit();
		session.close();
		//logger.debug("End of the method remove");				
	}
	public void removeAll() 
	{
		//logger.debug("Starting of the method removeAll");
		List<Cart> cartItems = getAllItems();
		for(Cart item: cartItems) 
		{
			remove(item.cartItemId);
		}
		//logger.debug("End of the method removeAll");
	}
	@Transactional
	public void update(int cartItemId,int quantity) 
	{
//		logger.debug("Starting of the method update");
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		Cart cartItem=(Cart)session.load(Cart.class, cartItemId);
	    session.saveOrUpdate(cartItem);
		tx.commit();
//		logger.debug("End of the method update");
	}

	
	


}

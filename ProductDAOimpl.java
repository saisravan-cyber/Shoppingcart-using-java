package com.niit.shoppingcart.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.model.Product;

@Repository("ProductDAO")
@Transactional
public class ProductDAOimpl implements ProductDAO
{
	//private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);
	@Autowired
    private SessionFactory sessionFactory;

	
	public SessionFactory getSessionFactory() 
	{
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) 
	{
		this.sessionFactory = sessionFactory;
	}
	@Transactional
	public boolean addPro(Product product)
	{
		//logger.debug("Starting of the method calling addPro");
		try 
	    {
		sessionFactory.getCurrentSession().saveOrUpdate(product);
		//logger.debug("Ending of the method addPro");
		return true;
	    } 
	    catch (HibernateException e) 
	     {
		e.printStackTrace();
		return false;
	     }		
	}
	@Transactional
	public boolean updatePro(Product product) 
	 {
		//logger.debug("Starting of the method calling updatePro");
		try 
	    {
		sessionFactory.getCurrentSession().update(product);
		//logger.debug("Ending of the method addPro");
		return true;
	    } 
	    catch (HibernateException e) 
	     {
		e.printStackTrace();
		return false;
	     }		
	 }
	public List getAllPro() 
	{
		//logger.debug("Starting of the method calling getAllChairs");
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		List blist=session.createQuery("from Product").list();
		tx.commit();
		
		//logger.debug("End of the method getAllChairs");
		return blist;
		
	}
	public Product getSinglePro(int id)
	{
		//logger.debug("Starting of the method getSingleChair");
		Session session=sessionFactory.openSession();
		Product product=(Product)session.load(Product.class, id);
		//logger.debug("End of the method getSingleProduct");
		return product;
	}
	public void deletePro(int id) {
		//logger.debug("Starting of the method deleteChair");
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		Product product=(Product)session.load(Product.class, id);
		session.delete(product);
		tx.commit();	
		session.close();
		//logger.debug("End of the method deleteChair");
		
	}
}
package com.niit.shoppingcart.daoimpl;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.model.Supplier;

@Repository("supplierDAO")
@Component
@Transactional
public class SupplierDAOimpl implements SupplierDAO
{
	//private static final Logger logger = LoggerFactory.getLogger(SupplierDAOImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	public SupplierDAOimpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public boolean save(Supplier supplier)
	{
		//logger.debug("Starting of the method saveOrUpdate");
		
		try 
		    {
			sessionFactory.getCurrentSession().saveOrUpdate(supplier);
			//logger.debug("Ending of the method saveOrUpdate");
			return true;
		    } 
		    catch (HibernateException e) 
		     {
			e.printStackTrace();
			return false;
		     }
	}

	@Transactional
	public boolean delete(int id)
	{
		//logger.debug("Starting of the method delete");
		try
		{
		Supplier SupplierToDelete = new Supplier();
		SupplierToDelete.setId(id);
		sessionFactory.getCurrentSession().delete(SupplierToDelete);
		//logger.debug("Ending of the method delete");
		return true;
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public List getAllSuppliers()
	{
		//logger.debug("Starting of the method getAllSuppliers");
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		List list = session.createQuery("from Supplier").list();
		tx.commit();
		//logger.debug("Ending of the method getAllSuppliers");
		return list;
	}

	@Transactional
	public Supplier getSingleSupplier(int id)
	{
		//logger.debug("Starting of the method getSingleSupplier");
		Session session = sessionFactory.openSession();
		Supplier supplier = (Supplier) session.load(Supplier.class, id);
		//logger.debug("Ending the method of getSingleSupplier");
		return supplier;
	}

	
	@Transactional
	public boolean update(Supplier supplier)
	{
		//logger.debug("Starting of the method update");
		try {
			sessionFactory.getCurrentSession().update(supplier);
			//logger.debug("Ending the method of update");
			return true;
		    } 
		catch (HibernateException e) 
		    {
			e.printStackTrace();
			return false;
		}
		//logger.debug("End of the method saveOrUpdate");
		}
		
}

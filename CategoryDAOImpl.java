package com.niit.shoppingcart.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.model.Category;
@Repository("categoryDAO")
@Component
@Transactional
public class CategoryDAOImpl implements CategoryDAO
{
	
	
    @Autowired
    SessionFactory sessionFactory;
		
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<Category> list() 
	{
		@SuppressWarnings("unchecked")
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		List<Category> listCategory = (List<Category>) 
		          session.createCriteria(Category.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		     tx.commit();
		     return listCategory;
	}
	

		
	@Transactional
	public boolean saveOrUpdate(Category category) 
	{
		try 
		{
			System.out.println(category.getId());
			System.out.println(category.getName());
			System.out.println(category.getDescription());
			sessionFactory.getCurrentSession().saveOrUpdate(category);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	@Transactional
	public boolean updateCategory(Category category) 
	{
		try 
		{
			sessionFactory.getCurrentSession().update(category);
			return true;
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		
	}

	
	@Transactional
	public Category get(int id) 
	{
		Session session=sessionFactory.openSession();
		Category category=(Category)session.load(Category.class,id);
		return category;
	}
	
	@Transactional
	public boolean deleteCategory(String id) 
	{
		Category CategoryToDelete = new Category();
		int idNo=Integer.parseInt(id);
		CategoryToDelete.setId(idNo);
		try 
		    {
			sessionFactory.getCurrentSession().delete(CategoryToDelete);
			return true;
			} 
		catch (Exception e) 
		    {
			e.printStackTrace();
			return false;
		    }
	 }

	@Transactional
	public List getAllCategories() 
	{
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		List list = session.createQuery("from Category").list();
		tx.commit();
		return list;
	}
}
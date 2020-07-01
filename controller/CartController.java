package com.niit.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.niit.shoppingcart.dao.CartDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.model.Cart;
import com.niit.shoppingcart.model.Product;

@Controller
public class CartController
{
		
		//Logger log = LoggerFactory.getLogger(ShopCartController.class);
		
		@Autowired
		private ProductDAO productDAO;
		@Autowired
		private CartDAO cartDAO;
		
		   @ModelAttribute("cartItem")
		    public Cart cartInformation()
		    {
			    //log.debug("Starting of the method cartInfo");
			    return new Cart();
		    }
		
		   @RequestMapping(value="viewCart",method=RequestMethod.GET)
			public String viewCart(@RequestParam int proId)
			{  
			//log.debug("Starting of the method viewCart");
		    Product product=productDAO.getSinglePro(proId);
		    Cart cartItem=new Cart();
		    // cart.setGrandTotal(chair.getChairPrice());
		   	System.out.println(product.getProId());
		   	List list=cartDAO.getAllItems();
		   	System.out.println("List size......."+list.size());
		   	for(int i=0;list.size()>i;i++)
		   	{
		   	  Cart cti=(Cart)list.get(i);
		   	  if(proId==cti.getProId())
		   	  {
		   		    		  
		   		  System.out.println("yogi");
		    cartItem.setProId(product.getProId());
		    cartItem.setProName(product.getProName());
		    cartItem.setProPrice(product.getProPrice());
		    cartItem.setProDesc(product.getProDesc());
		   cartItem.setProStyle(product.getProStyle());
		   cartItem.setQuantity(cartItem.getQuantity()+1+cti.getQuantity());
		   cartItem.setProPrice(product.getProPrice()*cartItem.getQuantity());
		   cartDAO.add(cartItem);
	       int count=cartItem.getCartItemId();
	       cartDAO.remove(--count);
	       //log.debug("Ending of the method viewCart");
	       return "viewCart";
		   	}
		   	}
		   	System.out.println("reddy");
		   	cartItem.setProId(product.getProId());
		    cartItem.setProName(product.getProName());
		    cartItem.setProPrice(product.getProPrice());
		    cartItem.setProDesc(product.getProDesc());
		    cartItem.setProStyle(product.getProStyle());
		    cartItem.setQuantity(cartItem.getQuantity()+1);
		    cartItem.setProPrice(product.getProPrice()*cartItem.getQuantity());
		    cartDAO.add(cartItem);		  
		    //log.debug("Ending of the method viewCart");
		    return "viewCart";
		    }
			  
		 @RequestMapping(value="/listItems",method=RequestMethod.GET,produces="application/json")
		    public @ResponseBody String showListItems()
		       {
			    //log.debug("Starting of the method showListItems");
		    	List list=cartDAO.getAllItems();
		    	Gson x=new Gson();
		    	String json=x.toJson(list);
		    	//log.debug("Ending of the method showListItems");
		    	return json;
		    }
		 
		 @RequestMapping("/deleteItemCart")
		    public ModelAndView deleteChair(@RequestParam int id)
		    { 
			    //log.debug("Starting of the method deleteChair");
			    cartDAO.remove(id);
			    //log.debug("Ending of the method deleteChair");
		    	return new ModelAndView("viewCart");
		    }
		 
		 @RequestMapping("/removeAll")
		 public ModelAndView removeAllItem()
		 {
			 //log.debug("Starting of the method removeAllItem");
			 cartDAO.removeAll();
			 //log.debug("Ending of the method removeAllItem");
			 return new ModelAndView("viewCart");
		 }
		 
		 @RequestMapping("/viewProduct")
		 public ModelAndView viewProducts()
		 {
//			 log.debug("Starting of the method viewPro");
			 return new ModelAndView("viewProduct");
		 }
		
				 
			 @RequestMapping(value="/viewCartInformation", method = RequestMethod.GET)
			 public ModelAndView viewCartInformation() 
			 {
				   //log.debug("Starting of the method viewCartInformation");
				   return new ModelAndView("myCartInformation");	 
			 }
			 
	}



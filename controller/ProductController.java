package com.niit.shoppingcart.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.model.Product;

@Controller
public class ProductController 
{
//private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	SupplierDAO supplierDAO;
	
	@ModelAttribute("product")
    public Product createProduct()
    {
		//log.debug("Starting of the method createProduct");
    	return new Product();
    }
	
	
	 @RequestMapping("/addProduct")
	    public ModelAndView newProduct()
	    {
		    //log.debug("Starting of the method newProduct");
		    ModelAndView mv=new ModelAndView("addProduct");
		    mv.addObject("suppliers",supplierDAO.getAllSuppliers());
		    mv.addObject("categories",categoryDAO.list());
	    	//log.debug("Ending of the method newProduct");
	    	return mv;
	    }
	
	@RequestMapping("/retriveRecords")
	public ModelAndView retriveRecords()
	{  
		//log.debug("Starting of the method retriveRecords");
		ModelAndView mv=new ModelAndView("manageProducts");
		//log.debug("Ending of the method retriveRecords");
		return mv;
	} 
	

	 @RequestMapping(value="/storePro", method = RequestMethod.POST)
	    public String addPro(HttpServletRequest request,@Validated @ModelAttribute("product")Product product,BindingResult result)
	           {
		 //log.debug("Starting of the method addPro");
		      	if(result.hasErrors())
	        	{
	        		return "addProduct";
	        	}
		      	System.out.println(product.getProId());
		      	System.out.println(product.getProName());
		      	System.out.println(product.getImage());
	        	String filename=product.getImg().getOriginalFilename();
	        	product.setImage(filename);
	        	
	        	try{
	        		byte[] bytes=new byte[product.getImg().getInputStream().available()];
	        		product.getImg().getInputStream().read(bytes);
	        		BufferedInputStream buffer=new BufferedInputStream(product.getImg().getInputStream());
	        		MultipartFile file=product.getImg();
	        		String path=request.getServletContext().getRealPath("/")+"resources/images";
	        		File rootPath=new File(path);
	        		if(!rootPath.exists())
	        			rootPath.mkdirs();
	        		File store=new File(rootPath.getAbsolutePath()+"/"+filename);
	        		System.out.println("Image path :"+path);
	        		OutputStream os=new FileOutputStream(store);
	        		os.write(bytes);
	        	}
	        	catch(Exception e){
	        		System.out.println(e.getMessage());
	        	}
	        	productDAO.addPro(product);
	        	//log.debug("Ending of the method addChairs");
	        	//return "ViewAll";
	        	return "manageProducts";
	        }
	 
	 @RequestMapping(value="/list",method=RequestMethod.GET,produces="application/json")
	    public @ResponseBody String showList()
	    {
	    	//log.debug("Starting of the method showList");
	    	List list=productDAO.getAllPro();
	    	Gson x=new Gson();
	    	String json=x.toJson(list);
	    	//log.debug("End of the method showList");
	    	return json;
	    }
	    @RequestMapping(value="editProduct",method=RequestMethod.GET)
	    public ModelAndView editProduct(@RequestParam int id)
	    {
	    //log.debug("Starting of the method editChair");
		 Product product=productDAO.getSinglePro(id);
		 //log.debug("End of the method editChair");
		 return new ModelAndView("editProduct","product",product);
	    }
	    
	    @RequestMapping(value="/updatePro",method=RequestMethod.POST)
	    public ModelAndView updatePro(HttpServletRequest request,@Valid @ModelAttribute("product")Product product,BindingResult result)
	    {
	    	//log.debug("Starting of the method updatePro");
	    	String filename=product.getImg().getOriginalFilename();
	    	
     	product.setImage(filename);
     	
	    	try{
     		byte[] bytes=new byte[product.getImg().getInputStream().available()];
     		product.getImg().getInputStream().read(bytes);
     		BufferedInputStream buffer=new BufferedInputStream(product.getImg().getInputStream());
     		MultipartFile file=product.getImg();
     		String path=request.getServletContext().getRealPath("/")+"resources/images";
     		File rootPath=new File(path);
     		if(!rootPath.exists())
     			rootPath.mkdirs();
     		File store=new File(rootPath.getAbsolutePath()+"/"+filename);
     		OutputStream os=new FileOutputStream(store);
     		os.write(bytes);
     	}
     	catch(Exception e){
     		System.out.println(e.getMessage());
     	}
     		        
	    	productDAO.updatePro(product);
	    	//log.debug("End of the method updateChair");
	    	return new ModelAndView("manageProducts");
	    }
	    
	    @RequestMapping("/delete")
	    public ModelAndView deletePro(@RequestParam int id)
	    {		 
	    	//log.debug("Starting of the method deleteChair");
		    productDAO.deletePro(id);
	    	ModelAndView mv=new ModelAndView("manageProducts");
	    	//log.debug("End of the method deleteChair");
	    	return mv;
	    }
	    
	    @RequestMapping(value="viewProduct",method=RequestMethod.GET)
	    public ModelAndView viewProduct(@RequestParam int id, @ModelAttribute Product products)
	    {
	    	//log.debug("Starting of the method viewProduct");
	    	Product product=productDAO.getSinglePro(id);
	    	//log.debug("End of the method viewProduct");
	    	return new ModelAndView("viewProduct","product",product);
	    	
	    }
	    @RequestMapping("/displayProducts")
	    public ModelAndView displayProducts()
	    {
	    	//log.debug("Starting of the method displayProducts");
	    	ModelAndView mv=new ModelAndView("displayProducts");
	    	mv.addObject("products",productDAO.getAllPro());
	    	//log.debug("Ending of the method displayProducts");
	    	return mv;
	    	
	    }
	    
	
	 
}
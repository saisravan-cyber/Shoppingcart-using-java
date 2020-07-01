package com.niit.shoppingcart.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.model.Supplier;
@Controller
public class SupplierController 
{
	//Logger log = LoggerFactory.getLogger(SupplierController.class);
	@Autowired
	SupplierDAO supplierDAO;
	
	@Autowired
	CategoryDAO categoryDAO;

	@ModelAttribute("Supplier")
	public Supplier createSupplier() 
	{
		//log.debug("Starting of the method createSupplier");
		return new Supplier();
	}

	@RequestMapping("/supplier")
	public ModelAndView addSupplier1() 
	{
		//log.debug("Starting of the method addSupplier");
		ModelAndView mv = new ModelAndView("addSupplier");
		//log.debug("Ending of the method addSupplier");
		return mv;
	}
		
	@RequestMapping("addSupplier")
	public ModelAndView addSupplier() 
	{
		//log.debug("Starting of the method addSupplier");
		ModelAndView mv = new ModelAndView("addSupplier");
		mv.addObject("categories",categoryDAO.list());
		//log.debug("Ending of the method addSupplier");
		return mv;
	}
	
	@RequestMapping("storeSupplier")
	public String storeSupplier(HttpServletRequest request, @Validated @ModelAttribute("Supplier") Supplier supplier,
			BindingResult result)
	{
		//log.debug("Starting of the method storeSupplier");
		if (result.hasErrors())
		{
			return "addSupplier";
		}
		
		supplierDAO.save(supplier);
		//log.debug("Ending of the method storeSupplier");
		return "manageSuppliers";

	}
	
	@RequestMapping("/manageSupplier")
	public ModelAndView manageSuppliers() 
	{
		//log.debug("Starting of the method manageSuppliers");
		ModelAndView mv = new ModelAndView("manageSuppliers");
		//log.debug("Ending of the method manageSuppliers");
		return mv;
	}


	@RequestMapping(value = "listSuppliers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String showList()
	{
		//log.debug("Starting of the method showList");
		List list = supplierDAO.getAllSuppliers();
		Gson x = new Gson();
		String json = x.toJson(list);
		//log.debug("End of the method showList");
		return json;
	}

	@RequestMapping("/deleteSupplier")
	public ModelAndView deleteSupplier(@RequestParam int id) 
	{
		//log.debug("Starting of the method deleteSupplier");
		supplierDAO.delete(id);
		ModelAndView mv = new ModelAndView("manageSuppliers");
		//log.debug("Ending of the method deleteSupplier");
		return mv;
	}

	@RequestMapping(value = "viewSupplier", method = RequestMethod.GET)
	public ModelAndView viewSuppliers(@RequestParam int id, @ModelAttribute Supplier suppliers) 
	{
		//log.debug("Starting of the method viewSup");
		Supplier supplier = supplierDAO.getSingleSupplier(id);
		//log.debug("End of the method viewSup");
		return new ModelAndView("viewSupplier", "supplier", supplier);
	}
	 		 
		 		 
	 @RequestMapping(value="editSupplier",method=RequestMethod.GET)
	    public ModelAndView editSupplier(@RequestParam int id)
	    {
		    //log.debug("Starting of the method editSuppliers");
		    Supplier supplier=supplierDAO.getSingleSupplier(id);
		    //log.debug("End of the method editSuppliers");
	    	return new ModelAndView("editSupplier","Supplier",supplier);
	    }
	 
	 @RequestMapping(value="updateSupplier",method=RequestMethod.POST)
	    public ModelAndView updateSupplier(HttpServletRequest request,@Valid @ModelAttribute("Supplier")Supplier supplier,BindingResult result,Model model)
	    {
		    //log.debug("Starting of the method updateSupplier"); 
		    supplierDAO.update(supplier);
		    //log.debug("End of the method updateSupplier");
			return new ModelAndView("manageSuppliers");
	    }	 
}

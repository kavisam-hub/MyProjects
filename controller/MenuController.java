package com.dtvs.dtvsonline.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MydashboardView;
import com.dtvs.dtvsonline.model.User;
import com.dtvs.dtvsonline.service.DatabaseService;
import com.dtvs.dtvsonline.service.MaterialMasterService;
import com.dtvs.dtvsonline.utility.Utils;

@Controller
public class MenuController {

	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	@Autowired MaterialMasterService materialmasterservice;
	@Autowired DatabaseService databaseservice;
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	 public String accessDenied(ModelMap model, Principal principal) {
	  String username = principal.getName();
	  username=Utils.GetUserName();
	  User u=databaseservice.getUserById(Utils.GetUserName());
	  username=u.getName();
	  model.addAttribute("message", "Sorry "+username+" You don't have privileges to view this page!!!");
	  return "403";
	 }
	
	@RequestMapping(value = "/mydashboard", method = RequestMethod.GET)
	 public String mydashboard(ModelMap model) {
		List<MaterialMaster> materialmasters=materialmasterservice.fetchUserMaterialMasterRequest();
		List<MydashboardView> pendingReqCounter=materialmasterservice.UserPendingRequestCounetr();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("counter", pendingReqCounter);
		logger.info("I am in Mydashbaord",Utils.GetUserName());
		
	  return "mydashboard";
	 }
	
	@RequestMapping(value ="/login", method = RequestMethod.GET)
	   public String Login(ModelMap model) {	   
	      return "login";
	   } 
	/*Spring Security see this :
			@RequestMapping(value = "/login", method = RequestMethod.GET)
			public ModelAndView login(
				@RequestParam(value = "error", required = false) String error,
				@RequestParam(value = "logout", required = false) String logout) {
		 
				ModelAndView model = new ModelAndView();
				if (error != null) {
					model.addObject("error", "Invalid username and password!");
				}
		 
				if (logout != null) {
					model.addObject("msg", "You've been logged out successfully.");
				}
				model.setViewName("customlogin");
		 
				return model;
		 
			} */
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {		
        return "logout";
    }
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "denied";
    }
	
	@RequestMapping(value = { "/about"}, method = RequestMethod.POST)
   public String productsPage(ModelMap model) {
       return "about";
   }
	@RequestMapping(value = { "/emphome"}, method = RequestMethod.GET)
	   public String prodPage(ModelMap model) {
	       return "emphome";
	   }

   @RequestMapping(value = { "/"}, method = RequestMethod.GET)
   public String welcome(ModelMap model) {	   
      return "homepage";
   }
   
   @RequestMapping(value = { "/displayComingSoonForm"}, method = RequestMethod.GET)
   public String comingSoon(ModelMap model) {
       return "ComingSoonForm";
   }
   
   @RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExcel() {
		List<MaterialMaster> sapmaterialreqlist = materialmasterservice.fetchSAPMaterialList();
	//	return new ModelAndView("AnimalListExcel", "animalList", animalList);
	   return new ModelAndView("SAPListExcel","sapmaterialist",sapmaterialreqlist);
	}
/*   @RequestMapping(value = { "/materialsearch"}, method = RequestMethod.GET)
   public String MaterialMasterViewForm(ModelMap model) {
       return "materialmastersearch";
   } */
   
  /* @RequestMapping(value = { "/mmrrequest"}, method = RequestMethod.GET)
   public String displayMaterialRequestForm(Model model) {
   	MaterialMasterRequest mmreq=new MaterialMasterRequest();
   	model.addAttribute("mmr", mmreq);
       return "mmrrequest";
   }*/
   
   
/*   @RequestMapping(value= "/saveMaterial")
   public String addMaterial(@ModelAttribute("mmr") MaterialMasterRequest mmrequest) {
   //	mmrbusiness.addMaterial(mmrequest);
   	return "login";
   }
   
   // To Display Employee Save form    
   @RequestMapping(value = {"/employee"}, method = RequestMethod.GET)
   public String showEmployeeForm(Model model) {
   	Employee emp=new Employee();
   	model.addAttribute("empForm", emp);
       return "employee";
   }
   @RequestMapping(value = "/saveEmployee",method=RequestMethod.POST)
   public ModelAndView saveEmployee(@ModelAttribute("empForm") Employee employee) { 
   	employeeService.addEmployee(employee);    
       return new ModelAndView("redirect:/");
   }*/

	
}

package com.dtvs.dtvsonline.controller;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.dtvs.dtvsonline.model.Employee;
import com.dtvs.dtvsonline.service.EmployeeService;
 
@Controller
public class EmployeeController {
 
    private static final Logger logger = Logger
            .getLogger(EmployeeController.class);
 
    public EmployeeController() {
     //   System.out.println("EmployeeController()");
    }
    // 
    @Autowired
    private EmployeeService employeeService;
 
    @RequestMapping(value = "/homee")
    public String listEmployee(Model model) throws IOException {
        List<Employee> listEmployee = employeeService.getAllEmployees();
        model.addAttribute("listEmployee", listEmployee);       
        return "homescreen";
    }
    
    // To check for model...
    @ModelAttribute("empl")
    public List<Employee> getallname(){
    	return employeeService.getAllEmployees();
    }
  // To display EmployeeForm
    @RequestMapping(value = "/newEmployee", method = RequestMethod.GET)
    public String newContact(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);    
        return "empform";
    }
 
    // To Save Employee form data and return to Home page
    @RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
    public ModelAndView saveEmployee(@Valid @ModelAttribute("employee") Employee employee,BindingResult thebindingresult) {
    //	System.out.println("Error Value"+thebindingresult);
        if(thebindingresult.hasErrors())
        {
        //	return new ModelAndView("redirect:/newEmployee");
        	ModelAndView view=new ModelAndView("empform");
        	view.addObject("employee",employee);
        	return view;
        }
    	if (employee.getId() == 0)
        { // if employee id is 0 then create the  employee otherwise update the employee
            employeeService.addEmployee(employee);
        } else {
            employeeService.updateEmployee(employee);
        }
        return new ModelAndView("redirect:/homee");
    }
    	// To Delete Employee
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
    public ModelAndView deleteEmployee(@ModelAttribute ("employee") Employee emp) {
        int employeeId = emp.getId();
        employeeService.deleteEmployee(employeeId);
        return new ModelAndView("redirect:/homee");
    }
 
    @RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
    public ModelAndView editContact(@ModelAttribute ("employee") Employee emp) {
        int employeeId = emp.getId();
        Employee employee = employeeService.getEmployee(employeeId);
   //     System.out.println("Employee Details are:" +employee.getName());
        
      //  model.addAttribute("employee", employee);
        ModelAndView model = new ModelAndView("empform");        
        model.addObject("employee", employee);
        return model;
     //   return "homescreen";
      //  return new ModelAndView("empform");
    } 
}
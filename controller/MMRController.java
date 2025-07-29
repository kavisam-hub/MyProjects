package com.dtvs.dtvsonline.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MaterialMasterForm;
import com.dtvs.dtvsonline.model.MmrMaster;
import com.dtvs.dtvsonline.model.MydashboardView;
import com.dtvs.dtvsonline.model.User;
import com.dtvs.dtvsonline.service.DatabaseService;
//import com.dtvs.dtvsonline.service.EmployeeService;
import com.dtvs.dtvsonline.service.MailAPIService;
import com.dtvs.dtvsonline.service.MaterialCommodityMasterService;
import com.dtvs.dtvsonline.service.MaterialGroupMstrService;
//import com.dtvs.dtvsonline.service.MaterialMasterServerService;
import com.dtvs.dtvsonline.service.MaterialMasterService;
import com.dtvs.dtvsonline.service.MaterialTypeMstrService;
import com.dtvs.dtvsonline.service.MmrMasterService;
import com.dtvs.dtvsonline.utility.Utils;

@Controller
public class MMRController {

	private static final Logger logger = LoggerFactory.getLogger(MMRController.class);				

	@Autowired
	private DatabaseService databaseservice;

	private MailAPIService mailserviceapi;

	@Autowired(required = true)
	@Qualifier(value = "mailserviceapi")
	public void setMailAPIService(MailAPIService ms) {
		this.mailserviceapi = ms;
	}

	@Autowired
	private MaterialMasterService materialmasterservice;
	@Autowired
	private MmrMasterService mmrmasterservice;
/*	@Autowired
	private MaterialMasterServerService materialmasterserviceservice; */

/*	@Autowired(required = true)
	private EmployeeService empService;

	@Qualifier(value = "empService")
	public void setEmployeeService(EmployeeService us) {
		this.empService = us;
	}  */

	@Autowired(required = true)
	private MaterialCommodityMasterService mcmService;

	@Qualifier(value = "mcmService")
	public void SetMaterialCommodityMasterService(MaterialCommodityMasterService comservice) {
		this.mcmService = comservice;
	}

	@Autowired
	private MaterialTypeMstrService mtmService;

	@Qualifier(value = "mtmService")
	public void SetMaterialTypeMstrService(MaterialTypeMstrService mtmcservice) {
		this.mtmService = mtmcservice;
	}

	@Autowired
	private MaterialGroupMstrService mgmService;

	@Qualifier(value = "mgmService")
	public void SetMaterialGroupMstrService(MaterialGroupMstrService mgmcservice) {
		this.mgmService = mgmcservice;
	}

	public MMRController() {
		// System.out.println("I am In Material Master Controller:");
	}
	//My  Dash board display
	@RequestMapping(value = "/dashboard/mydashboard/pendingRequest", method = RequestMethod.GET)
	public String myDashboard(Model model) {
	//	List<MaterialMaster> materialmasters=materialmasterservice.fetchAllMaterialMaster();
		List<MaterialMaster> materialmasters=materialmasterservice.UserPendingRequest();
		List<MydashboardView> pendingReqCounter=materialmasterservice.UserPendingRequestCounetr();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("counter", pendingReqCounter);
	//	model.addAttribute("userreqpendingcount", pendingReqCounter);
		return "mydashboard";
	}
	
	@RequestMapping(value = "/dashboard/mydashboard/completedRequest", method = RequestMethod.GET)
	public String myCompletedRequest(Model model) {	
		List<MaterialMaster> materialmasters=materialmasterservice.UserCompletedRequest();
		List<MydashboardView> pendingReqCounter=materialmasterservice.UserPendingRequestCounetr();
		model.addAttribute("dashboardlist", materialmasters);	
		model.addAttribute("counter", pendingReqCounter);
		return "mydashboard";
	}
	
	@RequestMapping(value = "/dashboard/mydashboard/rejectedRequest", method = RequestMethod.GET)
	public String myRejectedRequest(Model model) {			
		List<MaterialMaster> materialmasters=materialmasterservice.UserRejectedRequest();
		List<MydashboardView> pendingReqCounter=materialmasterservice.UserPendingRequestCounetr();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("counter", pendingReqCounter);
		return "mydashboard";
	}	
	
	// HOD Dashboard display
	@RequestMapping(value = "/dashboard/hod", method = RequestMethod.GET)
	public String dashboardHOD(Model model) {	
		List<MaterialMaster> materialmasters=materialmasterservice.fetchAllMaterialMaster();
		List<MydashboardView> hoddashboardInbox=materialmasterservice.gethoddashboardInbox();
	//	List<MaterialMaster> materialmastersCounterList=materialmasterservice.pclallrequestcounter();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("message", "HOD Pending Request");
		model.addAttribute("hoddashboardInboxCount", hoddashboardInbox);
	//	model.addAttribute("materialreqcounter",materialmastersCounterList);
		return "hoddashboard";
	}
	
	@RequestMapping(value = "/dashboard/hoddashboard/pendingRequest", method = RequestMethod.GET)
	public String HODdashboardPending(Model model) {	
		List<MaterialMaster> materialmasters= materialmasterservice.fetchAllMaterialMaster();	
		List<MydashboardView> hoddashboardInbox=materialmasterservice.gethoddashboardInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message", "HOD Pending Request");
		model.addAttribute("hoddashboardInboxCount", hoddashboardInbox);
		return "hoddashboard";
	}
	@RequestMapping(value = "/dashboard/hoddashboard/inprocessRequest", method = RequestMethod.GET)
	public String HODdashboardInProcess(Model model) {	
		List<MaterialMaster> materialmasters= materialmasterservice.gethodInprocessMaterialList();
		List<MydashboardView> hoddashboardInbox=materialmasterservice.gethoddashboardInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message", "Material Inprocess Status");
		model.addAttribute("hoddashboardInboxCount", hoddashboardInbox);
		return "hoddashboard";
	}
	@RequestMapping(value = "/dashboard/hoddashboard/rejectedRequest", method = RequestMethod.GET)
	public String HODdashboardRejectedProcess(Model model) {
		String rejectedFlagVal="H";
		List<MaterialMaster> materialmasters= materialmasterservice.gethodRejectedOrCompletedMaterialList(rejectedFlagVal);
		List<MydashboardView> hoddashboardInbox=materialmasterservice.gethoddashboardInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message", "Rejected Material Request List");
		model.addAttribute("hoddashboardInboxCount", hoddashboardInbox);
		return "hoddashboard";
	}
	@RequestMapping(value = "/dashboard/hoddashboard/completedRequest", method = RequestMethod.GET)
	public String HODdashboardCompletedProcess(Model model) {		
		List<MaterialMaster> materialmasters= materialmasterservice.gethodCompletedMaterialList();	
		List<MydashboardView> hoddashboardInbox=materialmasterservice.gethoddashboardInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message", "Completed Material Request List");
		model.addAttribute("hoddashboardInboxCount", hoddashboardInbox);
		return "hoddashboard";
	}
	
	//Plant wise Dashboard Display	
		
		@RequestMapping(value = "/dashboard/plantwise", method = RequestMethod.GET)
		public String dashboardPlantwise(Model model) {	
			String statusFlagM="Counter";
			String plant="1108";
			List<MaterialMaster> materialmasters=materialmasterservice.plantwisependingRequest();
			List<MaterialMaster> materialmastersMannurCounetr=materialmasterservice.plantwiseRequest(plant,statusFlagM);
			// To get PlantWise count based on status
			List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
			
			User user=databaseservice.getUserById(Utils.GetUserName());
			String LoggedUserPlant=user.getPlant();
			model.addAttribute("dashboardlist", materialmasters);
			model.addAttribute("MannurCounter", materialmastersMannurCounetr);			
			model.addAttribute("message","Material Pending Request-"+LoggedUserPlant);
			// for getting plant wise status count
			model.addAttribute("plantInbox", plantwiseinbox);
			return "plantwisedashboard";
		}
	@RequestMapping(value = "/dashboard/plantwise/mannur/pendingRequest", method = RequestMethod.GET)
	public String materialPendingRequestMannurPlant(Model model) {	
		String plant="1108";
		String statusFlag="P";
		List<MaterialMaster> materialmasters=materialmasterservice.plantwiseRequest(plant,statusFlag);	
		// To get PlantWise count based on status
		List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message","Material Pending Request-Mannur");
		// for getting plant wise status count
		model.addAttribute("plantInbox", plantwiseinbox);
		return "plantwisedashboard";
	}
	@RequestMapping(value = "/dashboard/plantwise/oragadam/pendingRequest", method = RequestMethod.GET)
	public String materialPendingRequestOragadamPlant(Model model) {	
		String plant="1308";
		String statusFlag="P";
		List<MaterialMaster> materialmasters=materialmasterservice.plantwiseRequest(plant,statusFlag);	
		// To get PlantWise count based on status
		List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message","Material Pending Request-Oragadam");
		// for getting plant wise status count
		model.addAttribute("plantInbox", plantwiseinbox);
		return "plantwisedashboard";
	}
	@RequestMapping(value = "/dashboard/plantwise/mannur/completedRequest", method = RequestMethod.GET)
	public String materialCompletedRequestMannurPlant(Model model) {	
		String plant="1108";
		String statusFlag="C";
		List<MaterialMaster> materialmasters=materialmasterservice.plantwiseRequest(plant,statusFlag);	
		// To get PlantWise count based on status
		List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message","Material Completed Request-Mannur");
		// for getting plant wise status count
		model.addAttribute("plantInbox", plantwiseinbox);
		return "plantwisedashboard";
	}
	
	@RequestMapping(value = "/dashboard/plantwise/oragadam/completedRequest", method = RequestMethod.GET)
	public String materialCompletedRequestOragadamPlant(Model model) {	
		String plant="1308";
		String statusFlag="C";
		List<MaterialMaster> materialmasters=materialmasterservice.plantwiseRequest(plant,statusFlag);	
		// To get PlantWise count based on status
		List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message","Material Completed Request-Oragadam");
		// for getting plant wise status count
		model.addAttribute("plantInbox", plantwiseinbox);
		return "plantwisedashboard";
	}
	
	@RequestMapping(value = "/dashboard/plantwise/mannur/rejectedRequest", method = RequestMethod.GET)
	public String materialRejectedRequestMannurPlant(Model model) {	
		String plant="1108";
		String statusFlag="R";
		List<MaterialMaster> materialmasters=materialmasterservice.plantwiseRequest(plant,statusFlag);	
		// To get PlantWise count based on status
		List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message","Material Rejected Request-Mannur");
		// for getting plant wise status count
		model.addAttribute("plantInbox", plantwiseinbox);
		return "plantwisedashboard";
	}
	
	@RequestMapping(value = "/dashboard/plantwise/oragadam/rejectedRequest", method = RequestMethod.GET)
	public String materialRejectedRequestOragadamPlant(Model model) {	
		String plant="1308";
		String statusFlag="R";
		List<MaterialMaster> materialmasters=materialmasterservice.plantwiseRequest(plant,statusFlag);	
		// To get PlantWise count based on status
		List<MydashboardView> plantwiseinbox=materialmasterservice.getplantwiseInbox();
		
		model.addAttribute("dashboardlist", materialmasters);		
		model.addAttribute("message","Material Rejected Request-Oragadam");
		// for getting plant wise status count
		model.addAttribute("plantInbox", plantwiseinbox);
		return "plantwisedashboard";
	}
	
	// PCL DashBoard Display
	@RequestMapping(value = "/dashboard/pcl", method = RequestMethod.GET)
	public String dashboardPCL(Model model) {	
		List<MaterialMaster> materialmasters=materialmasterservice.pclallrequest();
	//	List<MaterialMaster> materialmastersCounterList=materialmasterservice.pclallrequestcounter();
		List<MydashboardView> pcdashboardlinboxCounter=materialmasterservice.getpcldashboardinboxCounter();
		model.addAttribute("dashboardlist", materialmasters);
	//	model.addAttribute("materialreqcounter",materialmastersCounterList);
		model.addAttribute("message", "Material InProcess Status");
		model.addAttribute("pcldashboardInbox",pcdashboardlinboxCounter);
		return "pcldashboard";		
	}
	
	@RequestMapping(value = "/dashboard/pcldashboard/pendingRequest", method = RequestMethod.GET)
	public String dashboardPCLPendingRequest(Model model) {
		String flagstatus="D";
		List<MaterialMaster> materialmasters=materialmasterservice.pclapprovedrequest(flagstatus);
		List<MydashboardView> pcdashboardlinboxCounter=materialmasterservice.getpcldashboardinboxCounter();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("message", "Material Pending List");
		model.addAttribute("pcldashboardInbox",pcdashboardlinboxCounter);
		return "pcldashboard";
	}
	@RequestMapping(value = "/dashboard/pcldashboard/approvedRequest", method = RequestMethod.GET)
	public String dashboardPCLApprovedRequest(Model model) {
		String flagstatus="M";
		List<MaterialMaster> materialmasters=materialmasterservice.pclapprovedrequest(flagstatus);
		List<MydashboardView> pcdashboardlinboxCounter=materialmasterservice.getpcldashboardinboxCounter();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("message", "PCL Material Approved List");
		model.addAttribute("pcldashboardInbox",pcdashboardlinboxCounter);
		return "pcldashboard";
	}
	@RequestMapping(value = "/dashboard/pcldashboard/rejectedRequest", method = RequestMethod.GET)
	public String dashboardPCLRejectedRequest(Model model) {
		String flagstatus="P";
		List<MaterialMaster> materialmasters=materialmasterservice.pclapprovedrequest(flagstatus);
		List<MydashboardView> pcdashboardlinboxCounter=materialmasterservice.getpcldashboardinboxCounter();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("message", "PCL Material Rejected List");
		model.addAttribute("pcldashboardInbox",pcdashboardlinboxCounter);
		return "pcldashboard";
	}
	
	@RequestMapping(value = "/dashboard/pcldashboard/allRequest", method = RequestMethod.GET)
	public String dashboardPCLallRequest(Model model) {		
		List<MaterialMaster> materialmasters=materialmasterservice.getPCLallRequestList();
		List<MydashboardView> pcdashboardlinboxCounter=materialmasterservice.getpcldashboardinboxCounter();
		model.addAttribute("dashboardlist", materialmasters);
		model.addAttribute("message", "Material Request List Status");
		model.addAttribute("pcldashboardInbox",pcdashboardlinboxCounter);
		return "pcldashboard";
	}
	
	
	
	// To Display SAP edit screen for material request
	@RequestMapping(value="/SAPMaterialReqEdit",method=RequestMethod.GET)
	public String dispplayMaterialforedit(Model model)
	{
		List<MaterialMaster> materialmasters = new ArrayList<>();
		materialmasters = materialmasterservice.fetchSAPMaterialList();

		//materialmasterform.setMaterialmasterarray(materialmasters);
		// ModelAndView view=new ModelAndView("mmrequestlist");
		model.addAttribute("materialmasterlist", materialmasters);

		return "sapmaterialedit";
	}
	/* It displays object data into form for the given id.   
     * The @PathVariable puts URL data into variable.*/    
    @RequestMapping(value="/edit",method=RequestMethod.GET)    
    public ModelAndView edit(@ModelAttribute("materialrequest") MaterialMaster material){ 
    	int materialid = material.getMmr_keyid();
    //	System.out.println("Avinash editing one" +materialid);
        material=materialmasterservice.getMaterialById(materialid); 
               
  //    model.addAttribute("matrequest", material);  
      
  //      System.out.println("Avinash edit" +material.getMmr_description());       
    //    return "sapedit";    
        ModelAndView model = new ModelAndView("sapedit");        
        model.addObject("matrequest", material);
        return model;
    }  
    
    /* To update Material Request and back to Material update List for SAP */
    @RequestMapping(value="/edit(mmr_keyid)",method= RequestMethod.POST)
    public ModelAndView updateMaterialSAP(@ModelAttribute("matrequest") MaterialMaster material, @PathVariable Integer mmr_keyid)
    {    	
    	ModelAndView modelAndView = new ModelAndView("homepage");    	 
        materialmasterservice.updatematerialmaster(material);
        String message = "Team was successfully edited.";
        modelAndView.addObject("message", message); 
        return modelAndView;
    }
    
	// To Display HOD Material List for approval ** Stage 1
	@RequestMapping(value = "/displayMMList", method = RequestMethod.GET)
	public String displayMaterial(Model model) {
		// List<MaterialMaster> materialmasterarray=new ArrayList<MaterialMaster>();

		// materialmasterarray=materialmasterservice.fetchAllMaterialMaster();

		MaterialMasterForm materialmasterform = new MaterialMasterForm();
		List<MaterialMaster> materialmasters = new ArrayList<>();
		materialmasters = materialmasterservice.fetchAllMaterialMaster();
		materialmasterform.setMaterialmasterarray(materialmasters);
		// ModelAndView view=new ModelAndView("mmrequestlist");
		model.addAttribute("materialmasterform", materialmasterform);
		return "mmrequestlist";
	}

	// Business logic for Dept Head approval and mail triggering ** Stage 1
	@RequestMapping(value = "/displayMMList", method = RequestMethod.POST, params = "Approve")
	public String approveMaterialsHOD(@ModelAttribute("materialmasterform") MaterialMasterForm s, Model model) {
		String tbldata = "";
		String action = "";
		String status = "D";
		String toaddress="";
		List<MaterialMaster> materiallistapprove = new ArrayList<>();
		User u = databaseservice.getUserById(Utils.GetUserName());
		String userid = u.getId();
		if (null != s.getMaterialmasterarray()) {
			// materialmasterservice.updateMaterialArray(s.getMaterialmasterarray());
			List<MaterialMaster> materiallist = s.getMaterialmasterarray();
		//	List<MaterialMaster> materiallistapprove = new ArrayList<>();

			if (!materiallist.isEmpty()) {
				for (MaterialMaster material : materiallist) {
					if (material.getMmr_status() != null) {
				//		System.out.println("Checklist Is checkd " + material.getMmr_keyid());
						// material.setMmr_status("D");
						// material.setMmr_createdby(userid);
						materiallistapprove.add(material);
					//	System.out.println("Modified Decsription ---- " + materiallistapprove.get(0).getMmr_description());
						if (material.getMmr_action().equals("E")) {
							action = "Extension";
						//	System.out.println("Action is " + action);
						} else if (material.getMmr_action().equals("N")) {
							action = "Creation";
						//	System.out.println("Action is " + action);
						}
						tbldata = tbldata + " <tr> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_keyid() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_purpose() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_type() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_group() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_category() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_description() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_unit() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_storagetype() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_hsncode() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_commodity() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_pricecontrol() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_valclass() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_profitcenter() + "</td>"	
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_make() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_model() + "</td>"	
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_partno() + "</td>"	
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_specification() + "</td>"	
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_itemtype() + "</td>"	
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_otherdescription() + "</td>"	
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_machine() + "</td>"								
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_materialcode() + "</td> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" + action
								+ "</td> " + " </tr>";
					}
				}
			}
			materialmasterservice.updateMaterialArray(materiallistapprove,status);
		}
		
		if(u.getDept().equals("SAP") || u.getDept().equals("EDP"))
		{
		//	System.out.println("Hi I am IT/SAP");
			materialmasterservice.sentMailByEDPHOD(materiallistapprove);
			
		}
		// email body
	//	else if (tbldata.length() > 0) {
		else {
			if (tbldata.length() > 0) {
			String body = " <p>Sir,</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;HOD approval completed.Pending for PCL head approval to proceed further.</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;To View and Approve or Reject the request,Please "
					+ "<a href=https://itprocesscloud2.delphi-tvs.com/mmronline/MMListPCLApproval target=\"_blank\">click here</a>"
					+ "</p>" + " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>MMR No</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Purpose</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material Type</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Group</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Imp/Ind</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Description</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Unit</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Sloc</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Control Code</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Commodity</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Price Control</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ValCl</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ProfitCtr</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Make</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Model</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ItemPartNo</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Specification</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ItemType</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>OtherDescription</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Machine</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Action</th>"
					+ " </tr>" + tbldata + " </table>" + " <p>&nbsp;</p>"
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\"This is an automated mail generated from SAP Information Systems.\"</p> "
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Incase you need any further clarifications,request you to contact concerned SAP Module team.</p> "
					+ " <p>Regards,</p> " + " <p>" + u.getName() + "</p>";

			// model.addAttribute("message", "Helofsd");
			// code changed for EDP only for HOD approval mail triggering based on requester plant pcl head  
			
			toaddress="<"+u.getPclheadmailid()+">";
		//	toaddress = "<avinash.edp@delphitvs.com>";
			String fromAddress = u.getName() + "<" + u.getMailid() + ">";
		//	String ccAddress = "";
			String subject = "DTVS-Workflow System-MMR Approval-process";			
			this.mailserviceapi.ReadyToSendEmail(toaddress, fromAddress, subject, body);
			}
		}
		return "redirect:/displayMMList";
	}

	// Business logic to reject the Material Request list by dept head and
	// triggering mail when reject button is clicked
	@RequestMapping(value = "/displayMMList", method = RequestMethod.POST, params = "Reject")
	public String rejectMaterialsHOD(@ModelAttribute("materialmasterform") MaterialMasterForm s, Model model) {
		String tbldata = "";
		String action = "";
		String status = "H";
		List<MaterialMaster> materiallist = s.getMaterialmasterarray();
		List<MaterialMaster> materiallistreject = new ArrayList<MaterialMaster>();
		if (null != s.getMaterialmasterarray()) {
			if (!materiallist.isEmpty()) {
				for (MaterialMaster material : materiallist) {
					// index variable for indexing element of array  materiallist
					int index=0;
					
					if (material.getMmr_status() != null) {
					//	System.out.println("Checklist Is checkd " + material.getMmr_keyid());	
					//	System.out.println("Checklist Is checkd " + materiallist.get(index).getMmr_rejectionreason());
						
/* below line to set the material object attributes value material.setMmr_rejectionreason(materiallist.get(index).getMmr_rejectionreason());
  to avoid rejection modal popup dummy value ,,,,,   */  
						material.setMmr_rejectionreason(materiallist.get(index).getMmr_rejectionreason());
						materiallistreject.add(material);						
						if (material.getMmr_action().equals("E")) {
							action = "Extension";
							// System.out.println("Action is " + action);
						} else if (material.getMmr_action().equals("N")) {
							action = "Creation";
							// System.out.println("Action is " + action);
						}
						tbldata = tbldata + " <tr> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_keyid() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_type() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_group() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_category() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_description() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_unit() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_storagetype() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_hsncode() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_commodity() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_pricecontrol() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_valclass() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_profitcenter() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_materialcode() + "</td> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" + action
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_rejectionreason()+ "</td> " + " </tr>";
					}
					index++;
				}
			}
			
		}
		if(materiallistreject.size()==1){
			materialmasterservice.sendMailToUsers(materiallistreject,status);
			materialmasterservice.updateMaterialArray(materiallistreject, status);
			model.addAttribute("msg", "Material rejected Successfully");
			return "redirect:/displayMMList";
		}else {
			model.addAttribute("msg1", "Alert!");
			model.addAttribute("msg","Please Select One line item at a time to reject");
			return "mmrequestlist";
		}
	}

	// To Display Material List for PC&L approval ** Stage 3
	@RequestMapping(value = "/MMListPCLApproval", method = RequestMethod.GET)
	public String displayMaterialPCLApproval(Model model) {
		MaterialMasterForm materialmasterform = new MaterialMasterForm();
		List<MaterialMaster> materialmasters = new ArrayList<>();
		materialmasters = materialmasterservice.fetchPCLMaterialList();

		materialmasterform.setMaterialmasterarray(materialmasters);
		// ModelAndView view=new ModelAndView("mmrequestlist");
		model.addAttribute("materialmasterform", materialmasterform);
		model.addAttribute("listGroup", this.mgmService.getAllGroup());
		return "mmrequestlistPCLApproval";
	}

	// Material/PCL Head approval screen business logic
	@RequestMapping(value = "/MMListPCLApproval", method = RequestMethod.POST, params = "Approve")
	public String approveMaterialsPCL(@ModelAttribute("materialmasterform") MaterialMasterForm s, Model model) {
		String tbldata = "";
		String action = "";
		String status = "M";
		if (null != s.getMaterialmasterarray()) {
			// materialmasterservice.updateMaterialArray(s.getMaterialmasterarray());
			List<MaterialMaster> materiallist = s.getMaterialmasterarray();
			List<MaterialMaster> materiallistapprove = new ArrayList<>();

			if (!materiallist.isEmpty()) {
				for (MaterialMaster material : materiallist) {
					if (material.getMmr_status() != null) {
						// System.out.println("Checklist Is checkd " + material.getMmr_keyid());
						// below code is to add material single object into array of object variable
						// materiallistapprove
						// material.setMmr_status("M");
						materiallistapprove.add(material);

						// This is for mail
						if (material.getMmr_action().equals("E")) {
							action = "Extension";
							// System.out.println("Action is " + action);
						} else if (material.getMmr_action().equals("N")) {
							action = "Creation";
							// System.out.println("Action is " + action);
						}
						tbldata = tbldata + " <tr> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_keyid() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_purpose() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_type() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_group() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_category() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_description() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_make() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_model() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_partno() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_specification() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_itemtype() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_otherdescription() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_machine() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_unit() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_storagetype() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_hsncode() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_commodity() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_pricecontrol() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_valclass() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_profitcenter() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_materialcode() + "</td> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" + action
								+ "</td> " + " </tr>";
					}
				}
			}
			materialmasterservice.updateMaterialArray(materiallistapprove, status);
		}

		// email body
		if (tbldata.length() > 0) {

			User u = databaseservice.getUserById(Utils.GetUserName());
			String body = " <p>Dear Team ,&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
					+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
					+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
					+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;"
					+ "<a href=https://itprocesscloud2.delphi-tvs.com/mmronline/materialmaster/search/ target=\"_blank\">Help</a>" + "</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Kindly verify and create/extent material in SAP.</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;To View and Approve the request,Please "
					+ "<a href=https://itprocesscloud2.delphi-tvs.com/mmronline/MMListSAPAction target=\"_blank\">click here</a>"
					+ "</p>" + " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>MMR No</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Purpose</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material Type</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Group</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Imp/Ind</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Description</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Make</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Model</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ItemPartno</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Specification</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ItemType</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>OtherDescription</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Machine</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Unit</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Sloc</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Control Code</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Commodity</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Price Control</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ValCl</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ProfitCtr</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Action</th>"
					+ " </tr>" + tbldata + " </table>" + " <p>&nbsp;</p>"
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\"This is an automated mail generated from SAP Information Systems.\"</p> "
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Incase you need any further clarifications,request you to contact concerned SAP Module team.</p> "
					+ " <p>Regards,</p> " + " <p>" + u.getName() + "</p>";

			// model.addAttribute("message", "Helofsd");
		//	String toaddress = "<avinash.edp@delphitvs.com>";
		//	String toaddress = "<va.sap@delphitvs.com>";
			String toaddress = "<vs.qed@delphitvs.com>";
			String fromAddress = u.getName() + "<" + u.getMailid() + ">";
			String subject = "DTVS-Workflow System-MMR Approval-process";
			this.mailserviceapi.ReadyToSendEmail(toaddress, fromAddress, subject, body);
		}

		return "redirect:/MMListPCLApproval";
	}

	// Material request list PLC/Material Head rejection and mail triggering
	// business logic
	@RequestMapping(value = "/MMListPCLApproval", method = RequestMethod.POST, params = "Reject")

	public String rejectPCLHead(@ModelAttribute("materialmasterform") MaterialMasterForm s, Model model) {
		String tbldata = "";
		String action = "";
		String status = "P";		
		List<MaterialMaster> materiallist=s.getMaterialmasterarray();
		List<MaterialMaster> materialrejectlist = new ArrayList<>();		
		if (null != s.getMaterialmasterarray()) {
			// materialmasterservice.updateMaterialArray(s.getMaterialmasterarray());		
			
			if (!materiallist.isEmpty()) {
				for (MaterialMaster material : materiallist) {
					int index=0;
					if (material.getMmr_status() != null) {
					//	 System.out.println("Test 1: Checklist Is checkd:- "+ material.getMmr_rejectionreason());						 
						// below code is to add material single object into array of object variable
						// materiallistapprove
						// material.setMmr_status("P");
			/* below line to set the material object attributes value material.setMmr_rejectionreason(materiallist.get(index).getMmr_rejectionreason());
						  to avoid rejection modal popup dummy value ",,,,,"         */  
						material.setMmr_rejectionreason(materiallist.get(index).getMmr_rejectionreason());
						
						materialrejectlist.add(material);

						// This is for mail
						if (material.getMmr_action().equals("E")) {
							action = "Extension";
						//	System.out.println("Action is " + action);
						} else if (material.getMmr_action().equals("N")) {
							action = "Creation";
						//	System.out.println("Action is " + action);
						}					
						tbldata = tbldata + " <tr> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_keyid() + "</td>"								
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_type() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_group() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_category() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_description() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_unit() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_storagetype() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_hsncode() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_commodity() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_pricecontrol() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_valclass() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_profitcenter() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_materialcode() + "</td> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" 
								+ action + "</td> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" 
								+ material.getMmr_rejectionreason() + "</td> " 								
								+ " </tr>";
					}
					index++;
				}
			}
			
		}
		// email body
/*		if (tbldata.length() > 0) {

			User u = databaseservice.getUserById(Utils.GetUserName());
			String body = " <p>Dear Requestor ,</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Please note that Your request for new material creation/extention has been rejected by the Material Head</p>"					
					+ " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>MMR No</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material Type</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Group</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Imp/Ind</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Description</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Unit</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Sloc</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Control Code</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Commodity</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Price Control</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ValCl</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ProfitCtr</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Action</th>"
					+ " </tr>" + tbldata + " </table>" + " <p>&nbsp;</p>"
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\"This is an automated mail generated from SAP Information Systems.\"</p> "
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Incase you need any further clarifications,request you to contact concerned SAP Module team.</p> "
					+ " <p>Regards,</p> " + " <p>" + u.getName() + "</p>";

			// model.addAttribute("message", "Helofsd");
			String toaddress = "<avinash.edp@delphitvs.com>";
			String fromAddress = u.getName() + "<" + u.getMailid() + ">";
			String subject = "Software test of MMR workflow";
			this.mailserviceapi.ReadyToSendEmail(toaddress, fromAddress, subject, body);
		} */
		if(materialrejectlist.size()==1)
		{			
		materialmasterservice.sendMailToUsers(materialrejectlist,status);
		materialmasterservice.updateMaterialArray(materialrejectlist, status);		
		return "redirect:/MMListPCLApproval";
		}else {		
			model.addAttribute("msg1", "Alert!");
			model.addAttribute("msg","Please Select One line item at a time to reject");
			return"mmrequestlistPCLApproval";
		}
		
	}

	// Display Material SAP action screen
	@RequestMapping(value = "/MMListSAPAction", method = RequestMethod.GET)
	public String displayMaterialSAPAction(Model model) {
		MaterialMasterForm materialmasterform = new MaterialMasterForm();
		List<MaterialMaster> materialmasters = new ArrayList<>();
		materialmasters = materialmasterservice.fetchSAPMaterialList();

		materialmasterform.setMaterialmasterarray(materialmasters);
		// ModelAndView view=new ModelAndView("mmrequestlist");
		model.addAttribute("materialmasterform", materialmasterform);
		return "mmRequestListSAPAction";
	}

	@RequestMapping(value = "/MMListSAPAction", method = RequestMethod.POST, params = "Approve")
	public String approveSAPTeam(@ModelAttribute("materialmasterform") MaterialMasterForm s, Model model) {
		String tbldata = "";
		String action = "";
		String status = "C";
		if (null != s.getMaterialmasterarray()) {
			// materialmasterservice.updateMaterialArray(s.getMaterialmasterarray());
			List<MaterialMaster> materiallist = s.getMaterialmasterarray();
			List<MaterialMaster> materiallistapprove = new ArrayList<>();

			if (!materiallist.isEmpty()) {
				for (MaterialMaster material : materiallist) {
					if (material.getMmr_status() != null) {
						// System.out.println("Checklist Is checkd "+ material.getMmr_keyid());
						// below code is to add material single object into array of object variable
						// materiallistapprove
						// material.setMmr_status("C");
						materiallistapprove.add(material);
					}
				}
			}
			materialmasterservice.updateMaterialArray(materiallistapprove, status);
		}
		return "redirect:/MMListSAPAction";
	}

	@RequestMapping(value = "/MMListSAPAction", method = RequestMethod.POST, params = "Reject")
	public String rejectSAPTeam(@ModelAttribute("materialmasterform") MaterialMasterForm s, Model model) {
		String tbldata = "";
		String action = "";
		String status = "S";
		List<MaterialMaster> materiallist = s.getMaterialmasterarray();
		List<MaterialMaster> materialrejectlists = new ArrayList<>();
		if (null != s.getMaterialmasterarray()) {
			// materialmasterservice.updateMaterialArray(s.getMaterialmasterarray());
			

			if (!materiallist.isEmpty()) {
				for (MaterialMaster material : materiallist) {
					int index=0;
					if (material.getMmr_status() != null) {
						// System.out.println("Checklist Is checkd "+ material.getMmr_keyid());
						// below code is to add material single object into array of object variable
						// materiallistapprove
						material.setMmr_status("S");
	/* below line to set the material object attributes value material.setMmr_rejectionreason(materiallist.get(index).getMmr_rejectionreason());
	 to avoid rejection modal popup dummy value ,,,,,   */  
						material.setMmr_rejectionreason(materiallist.get(index).getMmr_rejectionreason());
						materialrejectlists.add(material);

						// This is for mail
						if (material.getMmr_action().equals("E")) {
							action = "Extension";
							//System.out.println("Action is " + action);
						} else if (material.getMmr_action().equals("N")) {
							action = "Creation";
						//	System.out.println("Action is " + action);
						}
						tbldata = tbldata + " <tr> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_keyid() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_type() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_group() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_category() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_description() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_unit() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_storagetype() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_hsncode() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_commodity() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_pricecontrol() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_valclass() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_profitcenter() + "</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_materialcode() + "</td> "
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" + action +"</td>"
								+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
								+ material.getMmr_rejectionreason()+ "</td>"
								+ "</tr>";
					}
					index++;
				}
			}
			
		}
		// email body
	/*	if (tbldata.length() > 0) {

			User u = databaseservice.getUserById(Utils.GetUserName());
			String body = " <p>Dear Requestor ,</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Please note that Your request for new material creation/extention has been rejected by SAP Team.</p>"					
					+ " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>MMR No</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material Type</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Group</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Imp/Ind</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Description</th> "
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Unit</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Sloc</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Control Code</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Commodity</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Price Control</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ValCl</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ProfitCtr</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Action</th>"
					+ " </tr>" + tbldata + " </table>" + " <p>&nbsp;</p>"
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\"This is an automated mail generated from SAP Information Systems.\"</p> "
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Incase you need any further clarifications,request you to contact concerned SAP Module team.</p> "
					+ " <p>Regards,</p> " + " <p>" + u.getName() + "</p>";

			// model.addAttribute("message", "Helofsd");
			String toaddress = "<avinash.edp@delphitvs.com>";
			String fromAddress = u.getName() + "<" + u.getMailid() + ">";
			String subject = "Software test of MMR workflow";
			this.mailserviceapi.ReadyToSendEmail(toaddress, fromAddress, subject, body);
		} */
		if(materialrejectlists.size()==1)
		{
			materialmasterservice.updateMaterialArray(materialrejectlists, status);
			materialmasterservice.sendMailToUsers(materialrejectlists, status);
			return "redirect:/MMListSAPAction";
		}else {
			model.addAttribute("msg1", "Alert!");
			model.addAttribute("msg","Please Select One line item at a time to reject");
			return "mmRequestListSAPAction";
		}

		
	}	
	
	// to display Master Master Search List
	@RequestMapping(value = "/materialsearch", method = RequestMethod.GET)
	public ModelAndView displayMaterialSearch(@ModelAttribute("materialsearchform") MmrMaster mmrmaster) {
		List<MmrMaster> mmrmasterlist = mmrmasterservice.fetchMaterialList();
		// System.out.println("I am in search");
		ModelAndView view = new ModelAndView("materialmastersearch");
		view.addObject("materialsearchform", mmrmasterlist);
		return view;
	}

	// To Display Server side material form New 24-07-19 Avinash
	@RequestMapping(value = "/materialmaster/search", method = RequestMethod.GET)
	public String MaterialSearch(){	
		
	//	List<MmrMaster> mmrmasterlist=mmrmasterservice.fetchMaterialList();
	//	ObjectMapper mapper = new ObjectMapper();
	//	model.addAttribute("materialmasterlist",mapper.writeValueAsString(mmrmasterservice.fetchMaterialList()));
		return "cloudsearch";
	}

	 // This url /materialmaster/ajaxtest  is from  custom-datatable.js 
/*	@RequestMapping(value = "/materialmaster/ajaxtest", method = RequestMethod.GET)
	public @ResponseBody List<MmrMaster> listAllUsers() {
		List<MmrMaster> result=mmrmasterservice.fetchMaterialList();	
	//	response.setContentType("application/json");
	    return result;
	}  */
	
	@ResponseBody
	@RequestMapping("/loadServerSideData")
	public String loadServerSideData(HttpServletResponse response, HttpServletRequest request) {
		/* getting the JSON response to load in data table */
		// String jsonResponse = idtService.getDataTableResponse(request);
		// String
		// jsonResponse=materialmasterserverserviceimpl.fetchallMaterialList(request);
		// System.out.println("I am in server side controller");
		// String
		// jsonResponse=materialmasterserviceservice.fetchallMaterialList(request);
		// System.out.println("I am in server side:jsonResponse"+jsonResponse);
		/* Setting the response type as JSON */
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		return "";
	}
	
	
	// To Save MAsterial Master by Requestor
	@RequestMapping(value = "/saveMaterialMaster", method = RequestMethod.POST)
	public ModelAndView addMateriaMaster(@Valid @ModelAttribute("materialmasterform") MaterialMaster materialmaster,
			BindingResult thebindingresult) {
		// System.out.println("Material Master Category:" +
		// materialmaster.getMmr_category());
	//	System.out.println("Material Master Group:" + materialmaster.getMmr_group());
	//	System.out.println("Material Master Error Results:" + thebindingresult);
		if (thebindingresult.hasErrors()) {
		//	System.out.println("MM Error:" + thebindingresult);
			ModelAndView view = new ModelAndView("mmrrequest");
			view.addObject("materialmasterform", materialmaster);
			return view;
		}
		// else
	//	System.out.println("MM Keyid " + materialmaster.getMmr_keyid());

		// System.out.println("MM Keyid "+materialmaster.getMmr_category());
		materialmasterservice.addMaterial(materialmaster);
		logger.info("Data updated");
	//	System.out.println("Data Saving ");

		return new ModelAndView("redirect:/displayMMRequestForm");
	}

	// To display MM Request form
	@RequestMapping(value = "/displayMMRequestForm", method = RequestMethod.GET)
	public String newContact(Model model) {
		MaterialMaster materialmaster = new MaterialMaster();
		model.addAttribute("materialmasterform", materialmaster);
		model.addAttribute("listCommodity", this.mcmService.fetchAllCommodity());
		model.addAttribute("listType", this.mtmService.getMaterialType());
		model.addAttribute("listGroup", this.mgmService.getAllGroup());
		return "mmrrequest";
	}

	// To Display MM Approval HOD Screen
	@RequestMapping(value = "/displayMMApprovalHODForm", method = RequestMethod.GET)
	public String MMApprovalHOD(Model model) {
		// int mmrkeyid=materialmaster.getMmr_keyid();
		MaterialMaster materialmaste = new MaterialMaster();
		model.addAttribute("materialmasterform", materialmaste);
		return "mmapprovalHOD";
	}

}

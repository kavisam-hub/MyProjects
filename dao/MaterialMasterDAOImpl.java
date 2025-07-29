package com.dtvs.dtvsonline.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

// import com.dtvs.dtvsonline.model.Employee;
import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MydashboardView;
import com.dtvs.dtvsonline.model.User;
import com.dtvs.dtvsonline.service.DatabaseService;
import com.dtvs.dtvsonline.service.MailAPIService;
import com.dtvs.dtvsonline.utility.Utils;

@Repository
public class MaterialMasterDAOImpl implements MaterialMasterDAO {

	private static final Logger logger = Logger.getLogger(MaterialMasterDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DatabaseService databaseservice;

	private MailAPIService mailserviceapi;

	@Autowired(required = true)
	@Qualifier(value = "mailserviceapi")
	public void setMailAPIService(MailAPIService ms) {
		this.mailserviceapi = ms;
	}

	@Override
	public void saveMaterialMaster(MaterialMaster materialmaster) {

		Session session = sessionFactory.getCurrentSession();		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
		String sdate = dateFormat.format(date);
		Date dd = null;
		try {
			dd = dateFormat.parse(sdate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		User u = databaseservice.getUserById(Utils.GetUserName());

		// System.out.println(" Formated Date is :"+dd);
		materialmaster.setMmr_createdby(u.getId());
		materialmaster.setMmr_dept(u.getDept());
		materialmaster.setMmr_createdtimestamp(dd);
		materialmaster.setMmr_status("A");
		//added for crnhb on 10-07-19
	/*	if(u.getIscrnhbuser().equalsIgnoreCase("Y"))
		{
			materialmaster.setMmr_iscrnhb("Y");
		}
		else {
			materialmaster.setMmr_iscrnhb("N");
		} */
		// materialmaster.setMmr_createdtimestamp();
		session.saveOrUpdate(materialmaster);
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request has been generated.. ");	

	}
 /* To display pending list of Material request raised by user & to be approved by Dept. HOD   */
	
	@SuppressWarnings("unchecked")
	public List<MaterialMaster> getAllMaterialMaster() {
		List<MaterialMaster> matlists = new ArrayList<>();		
		Session session = sessionFactory.getCurrentSession();
		User loggedUser=databaseservice.getUserById(Utils.GetUserName());
		String query="";
	//	String loggedUserid=Utils.GetUserName();
		String loggedDept=loggedUser.getDept();
		String loggedUserPlant=loggedUser.getPlant();		
		
	//	System.out.println("Logger User Id ---"+loggedUserid);
		if(loggedUserPlant.equalsIgnoreCase("Mannur"))
		{
			loggedUserPlant="1108";			
		}
		else if(loggedUserPlant.equalsIgnoreCase("Oragadam"))
		{
			loggedUserPlant="1308";			
		}
		
	//	if(loggedUserid.equalsIgnoreCase("MKSHARMA"))
		if(loggedDept.equalsIgnoreCase("EDP") ||loggedDept.equalsIgnoreCase("SAP"))
		{
		 query="from MaterialMaster m where m.mmr_status='A' and m.mmr_dept in('SAP','EDP')";
		 matlists=session.createQuery(query).list();	
	
		}
/*		else if(loggedUser.getIscrnhbhod().equalsIgnoreCase("Y"))
		{
			query="from MaterialMaster m where m.mmr_status='A' and m.mmr_iscrnhb='Y' and m.mmr_dept=:MMRDept and m.mmr_plant=:MMRPlant";
			matlists=session.createQuery(query).setParameter("MMRDept", loggedDept).setParameter("MMRPlant", loggedUserPlant).list();
			System.out.println("I am in CRNHB Dept");
		} */
		else {
		/*	query="from MaterialMaster m where m.mmr_status='A' and  m.mmr_iscrnhb='N' and m.mmr_dept=:MMRDept and m.mmr_plant=:MMRPlant"; */
			query="from MaterialMaster m where m.mmr_status='A' and m.mmr_dept=:MMRDept and m.mmr_plant=:MMRPlant";
			matlists=session.createQuery(query).setParameter("MMRDept", loggedDept).setParameter("MMRPlant", loggedUserPlant).list();
		//	System.out.println("I am in other dept");
		}
		
	//	matlists = session.createQuery("from MaterialMaster s where s.mmr_status='A' ").list();
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request Dept. Approval Screen Viewed.. ");
		return matlists;
		// return sessionFactory.getCurrentSession().createQuery("from MaterialMaster
		// where mmr_status=:flag").list();

	}

	@Override
	public void updateMaterialMaster(MaterialMaster materialmaster,String status) {
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
		String sdate = dateFormat.format(date);
		Date dd = null;
		try {
			dd = dateFormat.parse(sdate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		Session session = sessionFactory.getCurrentSession();
		MaterialMaster ms=(MaterialMaster) session.get(MaterialMaster.class, materialmaster.getMmr_keyid());
		User uu = databaseservice.getUserById(Utils.GetUserName());
		String loggedUser=uu.getId();
	//	ms.setMmr_createdby(uu.getId());
		ms.setMmr_status(status);
		ms.setMmr_type(materialmaster.getMmr_type());
		ms.setMmr_description(materialmaster.getMmr_description());
		ms.setMmr_materialcode(materialmaster.getMmr_materialcode());		
		
		if(status=="D")
		{
			ms.setMmr_hodapprovedtimestamp(dd);
			ms.setMmr_depthod(loggedUser);
		}else if(status=="H") 
		{
			ms.setMmr_rejectionreason(materialmaster.getMmr_rejectionreason());
			ms.setMmr_hodrejectedtimestamp(dd);
			ms.setMmr_depthod(loggedUser);
		//	System.out.println("Avinash Recjection Testing HOD :---"+ materialmaster.getMmr_rejectionreason());
		}
		else if(status=="M")
		{
			ms.setMmr_pclapprovedtimestamp(dd);
			ms.setMmr_pclhod(loggedUser);
			
		}else if(status=="C") {
			ms.setMmr_sapprocesstimestamp(dd);
			ms.setMmr_sapapprovedby(loggedUser);
		}else if(status=="S"){
			ms.setMmr_rejectionreason(materialmaster.getMmr_rejectionreason());
			ms.setMmr_saprejectedtimestamp(dd);
			ms.setMmr_sapapprovedby(loggedUser);
		}
		else if(status=="P") 
		{
			ms.setMmr_rejectionreason(materialmaster.getMmr_rejectionreason());
			ms.setMmr_pclrejectedtimestamp(dd);
			ms.setMmr_pclhod(loggedUser);		
		}
		session.update(ms);	

	}

	@SuppressWarnings("unchecked")
	public List<MaterialMaster> getPCLMaterialList() {
				
	//	return sessionFactory.getCurrentSession().createQuery("from MaterialMaster ms where ms.mmr_status='D'").list();
		List<MaterialMaster> matlists = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		User loggedUser=databaseservice.getUserById(Utils.GetUserName());
		String loggedDept=loggedUser.getDept();
		String loggedUserPlant=loggedUser.getPlant();
		String query="";
		
		//Added by Akila on 20/12/2019
				if(loggedUser.getCcno().equals("102258") || loggedUser.getCcno().equals("100010"))
				{
					loggedDept=loggedUser.getDept();
					loggedUserPlant=loggedUser.getPlant();
					if(loggedUserPlant.equalsIgnoreCase("Mannur"))
					{
						loggedUserPlant="1108";	
					}
					else if(loggedUserPlant.equalsIgnoreCase("Oragadam"))
					{
						loggedUserPlant="1308";	
					}
					query="from MaterialMaster m where m.mmr_status='D' and m.mmr_plant=:MMRPlant";		
					matlists=session.createQuery(query).setParameter("MMRPlant", loggedUserPlant).list();
					return matlists;	
				}
				else
				{
					Model model=null;
					model.addAttribute("error","You are not authorized to approve");
				}
				return matlists;	
		
	}

	@SuppressWarnings("unchecked")
	public List<MaterialMaster> getSAPMaterialList() {
		
		return sessionFactory.getCurrentSession().createQuery("from MaterialMaster ms where ms.mmr_status='M'").list();
	}

	@Override
	public void sendEmailToUsers(List<MaterialMaster> materiallistreject,String status) {
		
		String tbldata = "";
		String action = "";
		String Requserid="";
		String messagevar="";
		Session session = sessionFactory.getCurrentSession();
		if (!materiallistreject.isEmpty()) {

			for (MaterialMaster material : materiallistreject) {
				Integer mmrkeyid = material.getMmr_keyid();

				MaterialMaster rmm = (MaterialMaster) session.get(MaterialMaster.class, mmrkeyid);
			//	System.out.println("Helloooooooo " + rmm.getMmr_createdby());
				Requserid=rmm.getMmr_createdby();
				// String Query="from MaterialMaster m where m.mmr_keyid= :MMRkeyid";
				// List<MaterialMaster>
				// result=session.createQuery(Query).setParameter("MMRkeyid", mmrkeyid).list();
				if (material.getMmr_action().equals("E")) {
					action = "Extension";
				//	System.out.println("Action is " + action);
				} else if (material.getMmr_action().equals("N")) {
					action = "Creation";
				//	System.out.println("Action is " + action);
				}
				tbldata = tbldata + " <tr> " + " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
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
						+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" 
						+ action + "</td> "
						+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" 
						+ material.getMmr_rejectionreason() + "</td> "
						+ " </tr>";
			}
		}
		// Email Body
		if(status.equals("P"))
		{
			messagevar="the PCL/Material head";
		}
		else if(status.equals("H")){
			messagevar="your department head";
		}else if(status.equals("S"))
		{
			messagevar="the SAP Team";
		}
		if (tbldata.length() > 0) {

			User u = databaseservice.getUserById(Utils.GetUserName());
			User ReqUser=databaseservice.getUserById(Requserid);
			String body = " <p>Dear "+ReqUser.getName()+",</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Your request to create/extent the material in SAP has been rejected by " + messagevar+"</p>"					
					+ " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
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
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Reason</th>"
					+ " </tr>" + tbldata + " </table>" + " <p>&nbsp;</p>"
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\"This is an automated mail generated from SAP Information Systems.\"</p> "
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Incase you need any further clarifications,request you to contact concerned SAP Module team.</p> "
					+ " <p>Regards,</p> " + " <p>" + u.getName() + "</p>";

			// model.addAttribute("message", "Helofsd");
		//	String toaddress = "<avinash.edp@delphitvs.com>";
			String toAddress="<"+ ReqUser.getMailid()+">";
			String fromAddress = u.getName() + "<" + u.getMailid() + ">";
			
			String subject = "DTVS-Workflow System-MMR Approval-process";
			String msgBody = "Dear Sir";
			this.mailserviceapi.ReadyToSendEmail(toAddress, fromAddress, subject, body);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> getUserAllMaterialMasterRequest() {		
		List<MaterialMaster> matlists = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		User loggedUserObj=databaseservice.getUserById(Utils.GetUserName());
		String loggedUserId=loggedUserObj.getId();
		String query="from MaterialMaster m where m.mmr_createdby=:logeduserid";		
		matlists=session.createQuery(query).setParameter("logeduserid", loggedUserId).list();
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MydashboardView> getPendingRequestCounter() {
		
		Session session = sessionFactory.getCurrentSession();
	//	List<MydashboardView> matlists = new ArrayList<>();
		User loggedUserObj=databaseservice.getUserById(Utils.GetUserName());
	//	String loggedUserId=loggedUserObj.getId();
		String loggedUserId=Utils.GetUserName();
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
	//	String query="select count(*) from MaterialMaster m where m.mmr_createdby=:logeduserid";
	/*
		String query="select COUNT(mmr_status),mmr_dept,mmr_status from MaterialMaster  WHERE " + 
			" mmr_plant=:userplant and mmr_createdby=:createdby " + 
				" GROUP BY(mmr_status) ";
		System.out.println("Avinash Testing");
		 matlists=session.createSQLQuery(query)
		.setParameter("createdby",loggedUserId).setParameter("userplant", plantcode).
		setResultTransformer(Transformers.aliasToBean(MydashboardView.class)).list();
		
		MydashboardView matlis=(MydashboardView) matlists.get(0);
		System.out.println("Avinash Testing" + matlis.getMmr_status().toString()); 
		
		*/
		List<MydashboardView>  matlists=session.createSQLQuery("select COUNT(*) as count,MMR_DEPT,MMR_STATUS "
				+" from materialmasterrequest WHERE " 
				 +" MMR_PLANT=:userplant and mmr_createdby=:createdby "
				 +" GROUP BY(MMR_STATUS)").setParameter("userplant",plantcode).setParameter("createdby",loggedUserId).setResultTransformer(Transformers.aliasToBean(MydashboardView.class)).list();
		
	//	System.out.print(" Hello Testing "+ matlists.get(0).getCount()+ matlists.get(0).getMMR_DEPT()+ matlists.get(0).getMMR_STATUS());
		
	//	Integer userPendingReqCounter=matlists.size();
	//	System.out.println("Integer value is bbbbbbsjajs ----**"+userPendingReqCounter);
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchUserPendingRequest() {
		
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		User loggedUserObj=databaseservice.getUserById(Utils.GetUserName());
		String loggedUserfact=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserfact.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserfact.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String loggedUserId=Utils.GetUserName();
		String query="from MaterialMaster m where m.mmr_createdby=:logeduserid and m.mmr_plant=:userplant and m.mmr_status in('A','D','M')" ;		
		matlists=session.createQuery(query).setParameter("logeduserid", loggedUserId).setParameter("userplant", plantcode).list();
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request Pending List viewed.. ");
		return matlists;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchUserCompletedRequest() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		String query="from MaterialMaster m where m.mmr_createdby=:logeduserid and m.mmr_status='C'" ;		
		matlists=session.createQuery(query).setParameter("logeduserid", loggedUserId).list();
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request Completed List viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchRejectedRequest() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		String query="from MaterialMaster m where m.mmr_createdby=:logeduserid and m.mmr_status in('H','P','S')" ;		
		matlists=session.createQuery(query).setParameter("logeduserid", loggedUserId).list();
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request Rejected List viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchplantpendingRequest() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('A','D','M')" ;		
		matlists=session.createQuery(query).setParameter("loggeduserplant", plantcode).list();
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchpclallReqStatus() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('P','M')" ;		
		matlists=session.createQuery(query).setParameter("loggeduserplant", plantcode).list();	
		logger.info("Login Name:"+Utils.GetUserName()+" Action :PCL All Material Request List Viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchpclapprovedRequest(String statusflag) {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status=:flagStatus)" ;		
		matlists=session.createQuery(query).setParameter("loggeduserplant", plantcode).setParameter("flagStatus", statusflag).list();		
		logger.info("Login Name:"+Utils.GetUserName()+" Action :PCL Material Approved List Viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchpclRequestCounter() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlistscounter = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String query="select mmr_status,count(*) as status from MaterialMaster m where m.mmr_plant=:loggeduserplant GROUP BY mmr_status)" ;		
		matlistscounter=session.createQuery(query).setParameter("loggeduserplant", plantcode).list();		
		return matlistscounter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchhodInprocessMaterialList() {
		List<MaterialMaster> matlists = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		User loggedUser=databaseservice.getUserById(Utils.GetUserName());
		String query="";
		String loggedUserid=Utils.GetUserName();
		String loggedDept=loggedUser.getDept();
		String loggedUserPlant=loggedUser.getPlant();
	//	System.out.println("Logger User Id ---"+loggedUserid);
		if(loggedUserPlant.equalsIgnoreCase("Mannur"))
		{
			loggedUserPlant="1108";			
		}
		else if(loggedUserPlant.equalsIgnoreCase("Oragadam"))
		{
			loggedUserPlant="1308";			
		}
		
	//	if(loggedUserid.equalsIgnoreCase("MKSHARMA")) Commented on 23-07-19
		if(loggedDept.equalsIgnoreCase("SAP")||loggedDept.equalsIgnoreCase("EDP"))
		{
		 query="from MaterialMaster m where m.mmr_status in('D','M') and m.mmr_dept in('SAP','EDP')";
		 matlists=session.createQuery(query).list();		 
		}else {
			query="from MaterialMaster m where m.mmr_status in('D','M') and m.mmr_dept=:MMRDept and m.mmr_plant=:MMRPlant";
			matlists=session.createQuery(query).setParameter("MMRDept", loggedDept).setParameter("MMRPlant", loggedUserPlant).list();			
		}
		
	//	matlists = session.createQuery("from MaterialMaster s where s.mmr_status='A' ").list();
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request InProcess List Viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchHODRejectedORCompletedMaterialList(String flagVal) {
		List<MaterialMaster> matlists = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		User loggedUser=databaseservice.getUserById(Utils.GetUserName());
		String query="";
		String loggedUserid=Utils.GetUserName();
		String loggedDept=loggedUser.getDept();
		String loggedUserPlant=loggedUser.getPlant();
	//	System.out.println("Logger User Id ---"+loggedUserid);
		if(loggedUserPlant.equalsIgnoreCase("Mannur"))
		{
			loggedUserPlant="1108";			
		}
		else if(loggedUserPlant.equalsIgnoreCase("Oragadam"))
		{
			loggedUserPlant="1308";			
		}
		
	//	if(loggedUserid.equalsIgnoreCase("MKSHARMA")) Commented On 23-07-19
		if(loggedDept.equalsIgnoreCase("EDP")|| loggedDept.equalsIgnoreCase("SAP"))
		{
		 query="from MaterialMaster m where m.mmr_status in('H','P','S') and m.mmr_dept in('SAP','EDP')";
		 matlists=session.createQuery(query).list();		 
		}else {
			query="from MaterialMaster m where m.mmr_status in('H','P','S') and m.mmr_dept=:MMRDept and m.mmr_plant=:MMRPlant";
			matlists=session.createQuery(query).setParameter("MMRDept", loggedDept).setParameter("MMRPlant", loggedUserPlant).list();			
		}
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Request Rejected List Viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchHODCompletedMaterialList() {
		List<MaterialMaster> matlists = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		User loggedUser=databaseservice.getUserById(Utils.GetUserName());
		String query="";
		String loggedUserid=Utils.GetUserName();
		String loggedDept=loggedUser.getDept();
		String loggedUserPlant=loggedUser.getPlant();
	//	System.out.println("Logger User Id ---"+loggedUserid);
		if(loggedUserPlant.equalsIgnoreCase("Mannur"))
		{
			loggedUserPlant="1108";			
		}
		else if(loggedUserPlant.equalsIgnoreCase("Oragadam"))
		{
			loggedUserPlant="1308";			
		}
		
	//	if(loggedUserid.equalsIgnoreCase("MKSHARMA")) Commented on 23-07-19
		if(loggedDept.equalsIgnoreCase("SAP")|| loggedDept.equalsIgnoreCase("EDP"))
		{
		 query="from MaterialMaster m where m.mmr_status in('C') and m.mmr_dept in('SAP','EDP')";
		 matlists=session.createQuery(query).list();		 
		}else {
			query="from MaterialMaster m where m.mmr_status in('C') and m.mmr_dept=:MMRDept and m.mmr_plant=:MMRPlant";
			matlists=session.createQuery(query).setParameter("MMRDept", loggedDept).setParameter("MMRPlant", loggedUserPlant).list();			
		}
		logger.info("Login Name:"+Utils.GetUserName()+" Action :Material Completed List Viewed.. ");
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchPCLAllMaterialListdashboard() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('D','P','M'))" ;		
		matlists=session.createQuery(query).setParameter("loggeduserplant", plantcode).list();		
		return matlists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchPlantWisePendingRequest() {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		String query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('A','D','M'))" ;		
		matlists=session.createQuery(query).setParameter("loggeduserplant", plantcode).list();		
		return matlists;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialMaster> fetchPlantWiseRequest(String plant, String statusFlag) {
		Session session = sessionFactory.getCurrentSession();
		List<MaterialMaster> matlists = new ArrayList<>();	
		String query="";
		if(statusFlag.equalsIgnoreCase("P")) {
			query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('A','D','M'))" ;	
		}
		else if(statusFlag.equalsIgnoreCase("C")) {
			query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('C'))" ;	
		}
		else if(statusFlag.equalsIgnoreCase("R")) {
			query="from MaterialMaster m where m.mmr_plant=:loggeduserplant and m.mmr_status in('H','P','S'))" ;	
		}
		else if(statusFlag.equalsIgnoreCase("Counter")) {
			query="select m.mmr_status,count(*) as counter from  MaterialMaster m where m.mmr_plant=:loggeduserplant group by (m.mmr_status)" ;	
		}
		matlists=session.createQuery(query).setParameter("loggeduserplant", plant).list();		
		return matlists;
	}

	@Override
	public MaterialMaster fetchMaterialById(int keyid) {
		MaterialMaster ms=(MaterialMaster) sessionFactory.getCurrentSession().get(MaterialMaster.class, keyid);		
	//	System.out.println("Avinash edit inside DAO" +ms.getMmr_keyid());
		return ms; 
	}

	@Override
	public void sentMailFromEdpHOD(List<MaterialMaster> materiallistapprove) {
		String requesteduserid="";
		List<MaterialMaster> mannurmaterialreqlist=new ArrayList<>();
		List<MaterialMaster> oragadammaterialreqlist=new ArrayList<>();
		String requestorid="";
	//	System.out.println("Inside sentMailFromEdpHOD block");
		Session session = sessionFactory.getCurrentSession();
		if(!materiallistapprove.isEmpty())
		{
		  //	System.out.println("Inside main If block");
			for(MaterialMaster materialmstrreq:materiallistapprove)
			{
				Integer mmrkeyid = materialmstrreq.getMmr_keyid();
				MaterialMaster rmm = (MaterialMaster) session.get(MaterialMaster.class, mmrkeyid);
			//	System.out.println("Helloooooooo " + rmm.getMmr_createdby());
				String Requserid=rmm.getMmr_createdby();
			//	requestorid=materialmstrreq.getMmr_createdby();
			//	User requestedUser=databaseservice.getUserById(requestorid);
			//	System.out.println("Inside For Loop" + requestorid);
			//	System.out.println("Inside For Loop- " + rmm.getMmr_createdby());
				if(rmm.getMmr_plant().equalsIgnoreCase("1308"))
				{
					requesteduserid=rmm.getMmr_plant();
					oragadammaterialreqlist.add(rmm);
				//	System.out.println("I am Oragadam");
				}else if(rmm.getMmr_plant().equals("1108"))
				{
					mannurmaterialreqlist.add(rmm);
					requesteduserid=rmm.getMmr_plant();
				//	System.out.println("I am Mannur");
					
				}				
				
			}
		//	System.out.println("Total Material request " +oragadammaterialreqlist.size());
			edphodmailTriggering(oragadammaterialreqlist);
			
		//	System.out.println("Total Material request " +mannurmaterialreqlist.size());
			edphodmailTriggering(mannurmaterialreqlist);
		}		
		
	}

	private void edphodmailTriggering(List<MaterialMaster> plantmaterialrequest) 
	{
		String tbldata = "";
		String action = "";
		String Requserid="";		
		Session session = sessionFactory.getCurrentSession();
		if (!plantmaterialrequest.isEmpty()) {

			for (MaterialMaster material : plantmaterialrequest) {
				Integer mmrkeyid = material.getMmr_keyid();

				MaterialMaster rmm = (MaterialMaster) session.get(MaterialMaster.class, mmrkeyid);
			//	System.out.println("Helloooooooo " + rmm.getMmr_createdby());
				Requserid=rmm.getMmr_createdby();
			//	System.out.println("Purpose Value is What "+ rmm.getMmr_purpose()); 
				// String Query="from MaterialMaster m where m.mmr_keyid= :MMRkeyid";
				// List<MaterialMaster>
				// result=session.createQuery(Query).setParameter("MMRkeyid", mmrkeyid).list();
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
						+ "</td> " + " </tr>";			}
		}
		// Email Body
		
		if (tbldata.length() > 0) {

			User u = databaseservice.getUserById(Utils.GetUserName());
			User ReqUser=databaseservice.getUserById(Requserid);
			String body = " <p>Sir</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;HOD approval completed.Pending for PCL head approval to proceed further</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;To View and Approve or Reject the request,Please "
					+ "<a href=https://itprocesscloud2.delphi-tvs.com/mmronline/MMListPCLApproval target=\"_blank\">click here</a>"
					+ " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
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
			String toAddress="<"+ ReqUser.getPclheadmailid()+">";
			String fromAddress = u.getName() + "<" + u.getMailid() + ">";
			
			String subject = "DTVS-Workflow System-MMR Approval-process";
		//	String msgBody = "Dear Sir";
			this.mailserviceapi.ReadyToSendEmail(toAddress, fromAddress, subject, body);
		}			
	}

	// to get plantwise dashboard inbox counter
	@SuppressWarnings("unchecked")
	@Override
	public List<MydashboardView> fetchplantwiseInbox() {		
		Session session = sessionFactory.getCurrentSession();
		List<MydashboardView>  matlists=session.createSQLQuery("select count(*) as count,MMR_PLANT,MMR_STATUS"
				+" from materialmasterrequest where MMR_PLANT=1308 "
				+"  group by(MMR_STATUS) UNION "
				+" select count(*) as count,MMR_PLANT,MMR_STATUS from materialmasterrequest "
				+" where mmr_plant=1108 "
				+" group by(MMR_STATUS)").setResultTransformer(Transformers.aliasToBean(MydashboardView.class)).list();
		
	//	System.out.print(" Hello Testing "+ matlists.get(0).getCount()+ matlists.get(0).getMMR_PLANT()+ matlists.get(0).getMMR_STATUS());
		
		return matlists;
	}

	// To get plant wise  PCL dash board inbox counter
	@Override
	public List<MydashboardView> fetchpcldashboardinboxCounter() {
		Session session=sessionFactory.getCurrentSession();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String plantcode="";
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		
		@SuppressWarnings("unchecked")
		List<MydashboardView> pcldashboardInbox=session.createSQLQuery("select count(*) as count,MMR_PLANT,MMR_STATUS "
				+" from materialmasterrequest where MMR_PLANT=:empplantcode "
				+"  group by(MMR_STATUS)").setParameter("empplantcode",plantcode).setResultTransformer(Transformers.aliasToBean(MydashboardView.class)).list();	
	//	System.out.println(" Hello Testing "+ pcldashboardInbox.get(0).getCount()+ pcldashboardInbox.get(0).getMMR_PLANT()+ pcldashboardInbox.get(0).getMMR_STATUS());
	//	System.out.println("Plant Is"+ plantcode);
		return pcldashboardInbox;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MydashboardView> fetchhoddashboardInbox() {		
		Session session=sessionFactory.getCurrentSession();
		String loggedUserId=Utils.GetUserName();
		User loggedUserObj=databaseservice.getUserById(loggedUserId);
		String loggedUserFactory=loggedUserObj.getPlant();
		String loggedUserDept=loggedUserObj.getDept();
		String plantcode="";
		List<MydashboardView> hoddashboaxInboxCount=new ArrayList<>();
		if(loggedUserFactory.equalsIgnoreCase("Oragadam"))
		{
			plantcode="1308";
		}else if(loggedUserFactory.equalsIgnoreCase("Mannur"))
		{
			plantcode="1108";
		}
		if(loggedUserDept.equalsIgnoreCase("EDP")|| loggedUserDept.equalsIgnoreCase("SAP"))
		{
			hoddashboaxInboxCount=session.createSQLQuery("select count(*) as count,MMR_PLANT,MMR_STATUS "
					+" from materialmasterrequest where  "
					+" MMR_DEPT in('SAP','EDP')  group by(MMR_STATUS)")
					.setResultTransformer(Transformers.aliasToBean(MydashboardView.class)).list();
		}else 
		{
			hoddashboaxInboxCount=session.createSQLQuery("select count(*) as count,MMR_PLANT,MMR_STATUS "
					+" from materialmasterrequest where MMR_PLANT=:EMPPLANT "
					+" and MMR_DEPT=:EMPDEPT  group by(MMR_STATUS)").setParameter("EMPPLANT", plantcode)
					.setParameter("EMPDEPT", loggedUserDept).setResultTransformer(Transformers.aliasToBean(MydashboardView.class)).list();
		}
		
	//	System.out.println(" Hello Testing "+ hoddashboaxInboxCount.get(0).getCount()+ hoddashboaxInboxCount.get(0).getMMR_PLANT()+ hoddashboaxInboxCount.get(0).getMMR_STATUS());
		return hoddashboaxInboxCount;
	}
	
}

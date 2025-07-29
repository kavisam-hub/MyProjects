package com.dtvs.dtvsonline.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MaterialMasterForm;
import com.dtvs.dtvsonline.model.User;
import com.dtvs.dtvsonline.service.DatabaseService;
import com.dtvs.dtvsonline.service.MailAPIService;
import com.dtvs.dtvsonline.service.MaterialMasterService;
import com.dtvs.dtvsonline.utility.Utils;

@Controller
public class ExcelController {

	@Autowired
	private DatabaseService databaseservice;
	
	private User user;
	/*
	 * @Autowired(required=true)
	 * 
	 * @Qualifier(value="databaseservice") public void
	 * setDatabaseService(DatabaseService ds) { this.databaseservice=ds; }
	 */

	// @Autowired
	ServletContext context;

	private MailAPIService mailserviceapi;

	@Autowired(required = true)
	@Qualifier(value = "mailserviceapi")
	public void setMailAPIService(MailAPIService ms) {
		this.mailserviceapi = ms;
	}

	@Autowired
	private MaterialMasterService materialmasterservice;

	@RequestMapping(value = "/displaymaterialinformation", method = RequestMethod.GET)
	// To display material request form 
	public String HelloWorld(Model model) {
		// model.addAttribute("lstUser", lstUser);
		// model.addAttribute("message", "Welcome to Spring MVC");
		return "materialinformation";
	}

	/*
	 * @RequestMapping(value = "/processExcel", method = RequestMethod.POST) public
	 * String processExcel(Model model, @RequestParam("excelfile") MultipartFile
	 * excelfile) { try { List<MaterialInfo> lstUser = new ArrayList<>(); int i = 0;
	 * // Creates a workbook object from the uploaded excelfile HSSFWorkbook
	 * workbook = new HSSFWorkbook(excelfile.getInputStream()); // Creates a
	 * worksheet object representing the first sheet HSSFSheet worksheet =
	 * workbook.getSheetAt(0); // Reads the data in excel file until last row is
	 * encountered while (i <= worksheet.getLastRowNum()) { // Creates an object for
	 * the UserInfo Model MaterialInfo material = new MaterialInfo(); // Creates an
	 * object representing a single row in excel HSSFRow row =
	 * worksheet.getRow(i++); // Sets the Read data to the model class
	 * material.setMaterialType(row.getCell(0).getStringCellValue());
	 * material.setMaterialGroup(row.getCell(1).getStringCellValue());
	 * material.setImportindigenous(row.getCell(2).getStringCellValue());
	 * material.setMaterialDescription(row.getCell(3).getStringCellValue());
	 * material.setUOM(row.getCell(4).getStringCellValue());
	 * material.setStorage(row.getCell(5).getStringCellValue());
	 * material.setHSNCode((int)row.getCell(6).getNumericCellValue());
	 * material.setMaterialCommodity((int)row.getCell(7).getNumericCellValue());
	 * material.setPriceControl(row.getCell(8).getStringCellValue());
	 * material.setValuationClass((int)row.getCell(9).getNumericCellValue());
	 * material.setProfitCenter(row.getCell(10).getStringCellValue());
	 * material.setMaterialCode(row.getCell(11).getStringCellValue());
	 * 
	 * // persist data into database in here lstUser.add(material); }
	 * workbook.close(); System.out.println("material output"+lstUser);
	 * model.addAttribute("lstUser", lstUser); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return "materialinformation"; }
	 */
	@RequestMapping(value = "/displaymaterialinformation", method = RequestMethod.POST, params = "action1")
	public String processExcel2007(Model model, @RequestParam("excelfile2007") MultipartFile excelfile) {
		try {
			user=databaseservice.getUserById(Utils.GetUserName());
			String plant=user.getPlant();
			MaterialMasterForm materialmasterform = new MaterialMasterForm();
			List<MaterialMaster> materialmasters = new ArrayList<>();
			// Creates a workbook object from the uploaded excelfile
			XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
			// Creates a worksheet object representing the first sheet
			XSSFSheet worksheet = workbook.getSheetAt(0);			
			if (worksheet.getLastRowNum() == -1) {
				XSSFRow row = worksheet.getRow(1);				
				// Iterator<Row> rowIterator = worksheet.iterator();
				MaterialMaster material = new MaterialMaster();

				// Sets the Read data to the model class
				material.setMmr_purpose(row.getCell(0).getStringCellValue());
				material.setMmr_type(row.getCell(1).getStringCellValue());
				material.setMmr_group(row.getCell(2).getStringCellValue());
				material.setMmr_category(row.getCell(3).getStringCellValue());				
				material.setMmr_description(row.getCell(4).getStringCellValue());
				material.setMmr_make(row.getCell(5).getStringCellValue());
				material.setMmr_model(row.getCell(6).getStringCellValue());				
				row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);				
				material.setMmr_partno(row.getCell(7).getStringCellValue());				
				material.setMmr_specification(row.getCell(8).getStringCellValue());
				material.setMmr_itemtype(row.getCell(9).getStringCellValue());
				material.setMmr_otherdescription(row.getCell(10).getStringCellValue());
				material.setMmr_machine(row.getCell(11).getStringCellValue());
				material.setMmr_unit(row.getCell(12).getStringCellValue());
				material.setMmr_storagetype(row.getCell(13).getStringCellValue());
				material.setMmr_hsncode((int) row.getCell(14).getNumericCellValue());
				material.setMmr_commodity((int) row.getCell(15).getNumericCellValue());
				material.setMmr_pricecontrol(row.getCell(16).getStringCellValue());
				material.setMmr_valclass((int) row.getCell(17).getNumericCellValue());
				material.setMmr_profitcenter(row.getCell(18).getStringCellValue());
				if (row.getCell(19).getCellType() == row.getCell(19).CELL_TYPE_STRING) {
					material.setMmr_materialcode(row.getCell(19).getStringCellValue());
				} else if (row.getCell(19).getCellType() == row.getCell(19).CELL_TYPE_NUMERIC) {
					String materialint = String.valueOf((int) row.getCell(19).getNumericCellValue());					
					material.setMmr_materialcode(materialint);
				}
				
				// persist data into database in here
				materialmasters.add(material);
				// System.out.println("Material Details -- " + material.getMmr_type() + "\t" +
				// material.getMmr_group()
				// + "\t" + material.getMmr_description() + "\t" +
				// material.getMmr_materialcode());

			} else {
				for (int i = 1; i <= worksheet.getLastRowNum(); i++) {
					// Creates an object for the UserInfo Model
					MaterialMaster material = new MaterialMaster();
					// System.out.println("Integer i No details:" + i);
					// Creates an object representing a single row in excel
					XSSFRow row = worksheet.getRow(i);
					// System.out.println("Worksheet last row number" + worksheet.getLastRowNum());
					// Sets the Read data to the model class
					
					/* Below code is just testing */
					
				/*	String s=row.getCell(4).getStringCellValue();
					String[] arr = s.split("");
					int counter=0;
					for (String ch : arr) 
					{
						counter++;						
					}	System.out.println("Avinash Description " +counter); */
					
					material.setMmr_purpose(row.getCell(0).getStringCellValue());
					material.setMmr_type(row.getCell(1).getStringCellValue());
					material.setMmr_group(row.getCell(2).getStringCellValue());
					material.setMmr_category(row.getCell(3).getStringCellValue());
					material.setMmr_description(row.getCell(4).getStringCellValue());					
					material.setMmr_make(row.getCell(5).getStringCellValue());
					material.setMmr_model(row.getCell(6).getStringCellValue());
					row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
					material.setMmr_partno(row.getCell(7).getStringCellValue());
					material.setMmr_specification(row.getCell(8).getStringCellValue());
					material.setMmr_itemtype(row.getCell(9).getStringCellValue());
					material.setMmr_otherdescription(row.getCell(10).getStringCellValue());
					material.setMmr_machine(row.getCell(11).getStringCellValue());
					material.setMmr_unit(row.getCell(12).getStringCellValue());
					material.setMmr_storagetype(row.getCell(13).getStringCellValue());
					material.setMmr_hsncode((int) row.getCell(14).getNumericCellValue());
					material.setMmr_commodity((int) row.getCell(15).getNumericCellValue());
					material.setMmr_pricecontrol(row.getCell(16).getStringCellValue());
					material.setMmr_valclass((int) row.getCell(17).getNumericCellValue());
					material.setMmr_profitcenter(row.getCell(18).getStringCellValue());
					if (row.getCell(19).getCellType() == row.getCell(19).CELL_TYPE_STRING) {
						material.setMmr_materialcode(row.getCell(19).getStringCellValue());
					} else if (row.getCell(19).getCellType() == row.getCell(19).CELL_TYPE_NUMERIC) {
						String materialint = String.valueOf((int) row.getCell(19).getNumericCellValue());
						material.setMmr_materialcode(materialint);
					}
					
					// persist data into database in here
					materialmasters.add(material);
					// System.out.println("Material Description Excel-- " +
					// material.getMmr_description());

				}
			}
			workbook.close();
			materialmasterform.setMaterialmasterarray(materialmasters);
			// System.out.println(materialmasterform.getMaterialmasterarray().get(0).getMmr_description());
			model.addAttribute("materialform", materialmasterform);
			model.addAttribute("Plant", plant);
			// model.addAttribute("materialrequests", materialmasters);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "materialinformation";
	}

	@RequestMapping(value = "/displaymaterialinformation", method = RequestMethod.POST, params = "Submit")
	public String saveMaterialRequest(@ModelAttribute("materialform") MaterialMasterForm s, Model model) {

		String tbldata = "";
		String action = "";
	/*	User u = databaseservice.getUserById(Utils.GetUserName());
		String loggedUserHODID=u.getManager(); 
		User loggedUserHODIdobj=databaseservice.getUserById(loggedUserHODID);
		if(loggedUserHODIdobj!= null) {
			model.addAttribute("msg", "Dept Manager details is Missing. Pls Contact EDP");
			return "materialinformation";
		} */
	/*	List<MaterialMaster> sdListt = s.getMaterialmasterarray(); 	
			 
			 
					for (MaterialMaster spr : sdListt) 
					{
						if(spr.getMmr_description().isEmpty())
						{
							return "materialinformation";
						}
					}*/			 
		 
		  if (null != s.getMaterialmasterarray()) {
			materialmasterservice.saveMaterialArray(s.getMaterialmasterarray());
			List<MaterialMaster> sdList = s.getMaterialmasterarray();
			if (!sdList.isEmpty()) {
				for (MaterialMaster spr : sdList) {

					// System.out.print("outside if Action is "+spr.getMmr_keyid());
					if (spr.getMmr_action().equals("E")) {
						action = "Extension";
					//	System.out.println("Action is " + action);
					} else if (spr.getMmr_action().equals("N")) {
						action = "Creation";
					//	System.out.println("Action is " + action);
					}
					tbldata = tbldata + " <tr> "
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_keyid() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_purpose() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_type() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_group() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_category() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_description() + "</td>"							
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_unit() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_storagetype() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_hsncode() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_commodity() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_pricecontrol() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_valclass() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_profitcenter() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_make() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_model() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_partno() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_specification() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_itemtype() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_otherdescription() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"
							+ spr.getMmr_machine() + "</td>"
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>"							
							+ spr.getMmr_materialcode() + "</td> "
							+ " <td style='border: 1px solid #dddddd; text-align: left;padding: 8px;'>" + action
							+ "</td> " + " </tr>";
					// System.out.println("Descritption for Mail "+spr.getMmr_description());
				}
			}
		}
		// System.out.println("Table Data length is" + tbldata.length());
		// email body
		if (tbldata.length() > 0) {

			User u = databaseservice.getUserById(Utils.GetUserName());
			String loggedUserHODID=u.getManager(); 
			User loggedUserHODIdobj=databaseservice.getUserById(loggedUserHODID);
			// http://itprocesscloud:7075/dtvsonline/displayMMList
			String body = " <p>Dear Sir,&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
					+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
					+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"
					+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp;"
					+ "<a href=https://itprocesscloud2.delphi-tvs.com/mmronline/materialmaster/search/ target=\"_blank\">Help</a>" + "</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Kindly give your approval to create the new material in SAP.</p>"
					+ " <p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;To View and Approve or Reject the request,Please "
					+ "<a href=https://itprocesscloud2.delphi-tvs.com/mmronline/displayMMList target=\"_blank\">click here</a>"					
					+ "</p>" 
					+"<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;To View Or Search HSN Code Please "
					+ "<a href=https://cleartax.in/s/gst-hsn-lookup target=\"_blank\">click here</a>"
					+"</p>"
					+ " <table style='border-collapse: collapse;width: 100%;'>" + " <tr>"
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
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ValCl Date</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ProfitCtr</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Make</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Model</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>ItemPartNo</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Specification</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Item Type</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Other Description</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Machine</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Material</th>"
					+ " <th style='border: 1px solid #dddddd; text-align: left;padding: 8px;background-color: #0099cc;color:white;'>Action</th>"
					+ " </tr>" + tbldata + " </table>" + " <p>&nbsp;</p>"
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\"This is an automated mail generated from SAP Information Systems.\"</p> "
					+ " <p style='color:blue'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Incase you need any further clarifications,request you to contact concerned SAP Module team.</p> "
					+ " <p>Regards,</p> " + " <p>" + u.getName() + "</p>";

			// model.addAttribute("message", "Helofsd");
		//	String toaddress = "<"+u.getHodmailid()+">";
		//	String toaddress=loggedUserHODIdobj.getMailid();
		//	System.out.println("Manager Name ---"+toadd);
		//	String toaddress = "<avinash.edp@delphitvs.com>";
			String toaddress="<"+ loggedUserHODIdobj.getMailid()+ ">";
		//	System.out.println("HOD mail Id"+toaddress);
			String fromAddress ="<" +u.getMailid()+ ">";
			String subject = "DTVS-Workflow System-MMR Approval-process";
			String msgBody = "Dear Sir";
			this.mailserviceapi.ReadyToSendEmail(toaddress, fromAddress, subject, body);
			model.addAttribute("msg", "Mail has been sent successfully");
			return "materialinformation";
		}else {
		model.addAttribute("msg", "Please import Excel File!");
		return "materialinformation";
		}
	}
}

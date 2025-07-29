package com.dtvs.dtvsonline.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import com.dtvs.dtvsonline.model.MaterialMaster;

public class SAPMaterialListExcelView extends AbstractExcelView{	
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{		
		HSSFSheet excelSheet = workbook.createSheet("Material Request");
		setExcelHeader(excelSheet);
		List<MaterialMaster> materialList=new ArrayList<>();
		materialList = (List<MaterialMaster>) model.get("sapmaterialist");
		setExcelRows(excelSheet,materialList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("ReqId");
		excelHeader.createCell(1).setCellValue("RequestedBy");
		excelHeader.createCell(2).setCellValue("Dept");
		excelHeader.createCell(3).setCellValue("Action");
		excelHeader.createCell(4).setCellValue("Purpose");
		excelHeader.createCell(5).setCellValue("Type");
		excelHeader.createCell(6).setCellValue("Group");
		excelHeader.createCell(7).setCellValue("Imp/Local");
		excelHeader.createCell(8).setCellValue("Description");
		excelHeader.createCell(9).setCellValue("Make");
		excelHeader.createCell(10).setCellValue("Model");
		excelHeader.createCell(11).setCellValue("ItemPartNo");
		excelHeader.createCell(12).setCellValue("Specification");
		excelHeader.createCell(13).setCellValue("ItemType");
		excelHeader.createCell(14).setCellValue("Other Description");
		excelHeader.createCell(15).setCellValue("Machine");
		excelHeader.createCell(16).setCellValue("Unit");
		excelHeader.createCell(17).setCellValue("Storage");
		excelHeader.createCell(18).setCellValue("HSN Code");
		excelHeader.createCell(19).setCellValue("Commodity");
		excelHeader.createCell(20).setCellValue("PRC");
		excelHeader.createCell(21).setCellValue("ValClass");
		excelHeader.createCell(22).setCellValue("Profit Center");
		excelHeader.createCell(23).setCellValue("Material");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<MaterialMaster> materialList)
	{
		int record = 1;		
		for (MaterialMaster material : materialList)
		{
			HSSFRow excelRow = excelSheet.createRow(record++);
				
			excelRow.createCell(0).setCellValue(material.getMmr_keyid());
			excelRow.createCell(1).setCellValue(material.getMmr_createdby());
			excelRow.createCell(2).setCellValue(material.getMmr_dept());
			excelRow.createCell(3).setCellValue(material.getMmr_action());
			excelRow.createCell(4).setCellValue(material.getMmr_purpose());
			excelRow.createCell(5).setCellValue(material.getMmr_type());
			excelRow.createCell(6).setCellValue(material.getMmr_group());
			excelRow.createCell(7).setCellValue(material.getMmr_category());
			excelRow.createCell(8).setCellValue(material.getMmr_description());
			excelRow.createCell(9).setCellValue(material.getMmr_make());
			excelRow.createCell(10).setCellValue(material.getMmr_model());
			excelRow.createCell(11).setCellValue(material.getMmr_partno());
			excelRow.createCell(12).setCellValue(material.getMmr_specification());
			excelRow.createCell(13).setCellValue(material.getMmr_itemtype());
			excelRow.createCell(14).setCellValue(material.getMmr_otherdescription());
			excelRow.createCell(15).setCellValue(material.getMmr_machine());
			excelRow.createCell(16).setCellValue(material.getMmr_unit());
			excelRow.createCell(17).setCellValue(material.getMmr_storagetype());
			excelRow.createCell(18).setCellValue(material.getMmr_hsncode());
			excelRow.createCell(19).setCellValue(material.getMmr_commodity());
			excelRow.createCell(20).setCellValue(material.getMmr_pricecontrol());
			excelRow.createCell(21).setCellValue(material.getMmr_valclass());
			excelRow.createCell(22).setCellValue(material.getMmr_profitcenter());
			excelRow.createCell(23).setCellValue(material.getMmr_materialcode());
			
		}
	}
}

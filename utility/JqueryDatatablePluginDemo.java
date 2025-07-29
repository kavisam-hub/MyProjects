package com.dtvs.dtvsonline.utility;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.json.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SuppressWarnings("serial")
@Controller
public class JqueryDatatablePluginDemo extends HttpServlet {

	private String GLOBAL_SEARCH_TERM;
	private String COLUMN_NAME;
	private String DIRECTION;
	private int INITIAL;
	private int RECORD_SIZE;
	private String MTYP_SEARCH_TERM,MATLGROUP_SEARCH_TERM,IMPORT_INDIGINOUS_SEARCH_TERM,MATERIALDESCRIPTION_SEARCH_TERM,BUN_SEARCH_TERM,
	SLOC_SEARCH_TERM,CONTROLCODE_SEARCH_TERM,MATERIALCOMMODITY_SEARCH_TERM,PRC_SEARCH_TERM,VALCL_SEARCH_TERM,PROFITCTR_SEARCH_TERM,
	MATERIAL_SEARCH_TERM;
	@RequestMapping(value="/materialmaster/ajaxtest",method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[] columnNames = { "mtyp","matlgroup","Import_Indigenous","materialdescription","bun","sloc","controlcode","materialcommodity","prc","valcl","profitctr","material" }; 

		JSONObject jsonResult = new JSONObject();
		int listDisplayAmount = 25; // for No of records to be displayed
		int start = 0;
		int column = 0;
		String dir = "asc";
		String pageNo = request.getParameter("iDisplayStart");
		String pageSize = request.getParameter("iDisplayLength");
		String colIndex = request.getParameter("iSortCol_0");
		String sortDirection = request.getParameter("sSortDir_0");
		
		if (pageNo != null) {
			start = Integer.parseInt(pageNo);
			if (start < 0) {
				start = 0;
			}
		}
		if (pageSize != null) {
			listDisplayAmount = Integer.parseInt(pageSize);
			if (listDisplayAmount < 25 || listDisplayAmount > 100) {
				listDisplayAmount = 25;
			}
		}
		if (colIndex != null) {
			column = Integer.parseInt(colIndex);
			if (column < 0 || column > 11) // For No of Columns to be displayed
				column = 0;
		}
		if (sortDirection != null) {
			if (!sortDirection.equals("asc"))
				dir = "desc";
		}

		String colName = columnNames[column];
		int totalRecords= -1;
		try {
			totalRecords = getTotalRecordCount();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		RECORD_SIZE = listDisplayAmount;
		GLOBAL_SEARCH_TERM = request.getParameter("sSearch");
		MTYP_SEARCH_TERM=request.getParameter("sSearch_0");
		MATLGROUP_SEARCH_TERM=request.getParameter("sSearch_1");
		IMPORT_INDIGINOUS_SEARCH_TERM=request.getParameter("sSearch_2");
		MATERIALDESCRIPTION_SEARCH_TERM=request.getParameter("sSearch_3");
		BUN_SEARCH_TERM=request.getParameter("sSearch_4");
		SLOC_SEARCH_TERM=request.getParameter("sSearch_5");
		CONTROLCODE_SEARCH_TERM=request.getParameter("sSearch_6");
		MATERIALCOMMODITY_SEARCH_TERM=request.getParameter("sSearch_7");
		PRC_SEARCH_TERM=request.getParameter("sSearch_8");
		VALCL_SEARCH_TERM=request.getParameter("sSearch_9");
		PROFITCTR_SEARCH_TERM=request.getParameter("sSearch_10");
		MATERIAL_SEARCH_TERM=request.getParameter("sSearch_11");		
		
		COLUMN_NAME = colName;
		DIRECTION = dir;
		INITIAL = start;

		try {
			jsonResult = getmmr_itemmstrDetails(totalRecords, request);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out = response.getWriter();
		out.print(jsonResult);

	}

	public JSONObject getmmr_itemmstrDetails(int totalRecords, HttpServletRequest request)
			throws SQLException, ClassNotFoundException {

		int totalAfterSearch = totalRecords;
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		String searchSQL = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	
		String dbConnectionURL = "jdbc:mysql://itsm-db.cek8qshkmyku.ap-south-1.rds.amazonaws.com:3306/dtvsweblive?user=itsm_user&password=VxA79vA7g9";
		Connection con = DriverManager.getConnection(dbConnectionURL);
		String sql = "SELECT " + " mtyp,matlgroup,Import_Indigenous,materialdescription,bun,sloc,controlcode,materialcommodity,prc,valcl,profitctr,"
				+ "material " + "FROM " + "mmr_master " + "WHERE ";

		String globeSearch = "mtyp like  '%" + GLOBAL_SEARCH_TERM + "%'"				
				+ "or matlgroup like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or Import_Indigenous like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or materialdescription like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or bun like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or sloc like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or controlcode like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or materialcommodity like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or prc like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or valcl like '%" + GLOBAL_SEARCH_TERM + "%'"					
				+ "or profitctr like '%" + GLOBAL_SEARCH_TERM + "%'"
				+ "or material like '%" + GLOBAL_SEARCH_TERM + "%'";
				
				
	//	System.out.println("First SQL Query" +sql);		
		String mtypSearch=" mtyp like '%" + MTYP_SEARCH_TERM + "%'";
	//	System.out.println("This query including ValCl" +mtypSearch); 
		String matlgroupSearch=" matlgroup like '%" + MATLGROUP_SEARCH_TERM + "%'";
		String Import_IndigenousSearch=" Import_Indigenous like '%" + IMPORT_INDIGINOUS_SEARCH_TERM + "%'";
		String materialdescriptionSearch=" materialdescription like '%" + MATERIALDESCRIPTION_SEARCH_TERM + "%'";
		String bunSearch=" bun like '%" + BUN_SEARCH_TERM + "%'";
		String slocSearch=" sloc like '%" + SLOC_SEARCH_TERM + "%'";
		String controlcodeSearch=" controlcode like '%" + CONTROLCODE_SEARCH_TERM + "%'";
		String materialcommoditySearch=" materialcommodity like '%" + MATERIALCOMMODITY_SEARCH_TERM + "%'";
		String prcSearch=" prc like '%" + PRC_SEARCH_TERM + "%'";
		String valclSearch=" valcl like '%" + VALCL_SEARCH_TERM + "%'";
		String profitctrSearch=" profitctr like '%" + PROFITCTR_SEARCH_TERM + "%'";	
				
		String materialSearch=" material like '%" + MATERIAL_SEARCH_TERM + "%'";
			
		
        
		if (GLOBAL_SEARCH_TERM != "") {
			searchSQL = globeSearch;
		}
		else if(MTYP_SEARCH_TERM !="")
		{
			searchSQL=mtypSearch;
		}
		else if(MATLGROUP_SEARCH_TERM !="")
		{
			searchSQL=matlgroupSearch;
		}	
		else if(IMPORT_INDIGINOUS_SEARCH_TERM!="")
		{
			searchSQL=Import_IndigenousSearch;
		}
		else if(MATERIALDESCRIPTION_SEARCH_TERM!="")
		{
			searchSQL=materialdescriptionSearch;
		}
		else if(BUN_SEARCH_TERM!="")
		{
			searchSQL=bunSearch;
		}
		else if(SLOC_SEARCH_TERM!="")
		{
			searchSQL=slocSearch;
		}
		else if(CONTROLCODE_SEARCH_TERM!="")
		{
			searchSQL=controlcodeSearch;
		//	System.out.println(searchSQL);
		}
		else if(MATERIALCOMMODITY_SEARCH_TERM!="")
		{
			searchSQL=materialcommoditySearch;
		}
		else if(PRC_SEARCH_TERM!="")
		{
			searchSQL=prcSearch;
		}	
		else if(VALCL_SEARCH_TERM!="")
		{
			searchSQL=valclSearch;
		}
		else if(PROFITCTR_SEARCH_TERM!="")
		{
			searchSQL=profitctrSearch;
		}			
		
		else if(MATERIAL_SEARCH_TERM!=null)
		{
			searchSQL=materialSearch;
		}		
        
	//	sql += searchSQL;
		// For column wise filter 
		String ColumnfilterStr;
		ColumnfilterStr= mtypSearch +""+ "and" +""+matlgroupSearch+""+ "AND"+""+Import_IndigenousSearch +""+ "and" +""+materialdescriptionSearch
		+""+ "and" +""+ bunSearch +""+ "and" +""+ slocSearch +""+ "and" +""+ controlcodeSearch +""+ "and" +""+materialcommoditySearch
		+""+ "and" +""+ prcSearch +""+ "and" +""+ valclSearch +""+ "and" +""+profitctrSearch +""+ "and" +""+ materialSearch ;
		if(GLOBAL_SEARCH_TERM != "")
		{
			sql +=searchSQL;
		}else {
		sql+=ColumnfilterStr;
		}
	//	System.out.println("Query before"+sql);
		sql += " order by " + COLUMN_NAME + " " + DIRECTION;
		sql += " limit " + INITIAL + ", " + RECORD_SIZE;
   //     System.out.println("This query after if else :-" +sql);
        //for searching
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			JSONArray ja = new JSONArray();
			ja.put(rs.getString("mtyp"));
			ja.put(rs.getString("matlgroup"));
			ja.put(rs.getString("Import_Indigenous"));
			ja.put(rs.getString("materialdescription"));
			ja.put(rs.getString("bun"));
			ja.put(rs.getString("sloc"));
			ja.put(rs.getString("controlcode"));		
			ja.put(rs.getString("materialcommodity"));
			ja.put(rs.getString("prc"));
			ja.put(rs.getString("valcl"));
			ja.put(rs.getString("profitctr"));
			ja.put(rs.getString("material"));				
			array.put(ja);
		}
		stmt.close();
		rs.close();		
	//	System.out.println("JSON Array output:--" + array);
		String query = "SELECT " + "COUNT(*) as count " + "FROM " + "mmr_master " + "WHERE ";

		//for pagination
		if (GLOBAL_SEARCH_TERM != ""||VALCL_SEARCH_TERM != ""||MATERIAL_SEARCH_TERM != ""||IMPORT_INDIGINOUS_SEARCH_TERM != "" || CONTROLCODE_SEARCH_TERM != "" || MTYP_SEARCH_TERM != "" ||MATLGROUP_SEARCH_TERM != ""||BUN_SEARCH_TERM != ""||PRC_SEARCH_TERM != ""||PROFITCTR_SEARCH_TERM != ""||SLOC_SEARCH_TERM != ""|| MATERIALDESCRIPTION_SEARCH_TERM != "") {
	//	query += searchSQL;
			if(GLOBAL_SEARCH_TERM != "")
				query += searchSQL;
			else
				query +=ColumnfilterStr;			
			
			PreparedStatement st = con.prepareStatement(query);
			ResultSet results = st.executeQuery();

			if (results.next()) {
				totalAfterSearch = results.getInt("count");
			}
			st.close();
			results.close();
			con.close();
		}
		try {
			result.put("iTotalRecords", totalRecords);
			result.put("iTotalDisplayRecords", totalAfterSearch);
			result.put("aaData", array);
			
		} catch (Exception e) {

		}
//		System.out.println("Result JSON Object Value" +result);
		return result;
	}

	public int getTotalRecordCount() throws SQLException {

		int totalRecords = -1;
		String sql = "SELECT " + "COUNT(*) as count " + "FROM " + "mmr_master";
        String dbConnectionURL = "jdbc:mysql://itsm-db.cek8qshkmyku.ap-south-1.rds.amazonaws.com:3306/dtvsweblive?user=itsm_user&password=VxA79vA7g9";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection con = DriverManager.getConnection(dbConnectionURL);

		PreparedStatement statement = con.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			totalRecords = resultSet.getInt("count");
		}
		resultSet.close();
		statement.close();
		con.close();

		return totalRecords;
	}

}


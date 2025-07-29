package com.dtvs.dtvsonline.dao;

import java.util.List;

import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MydashboardView;


public interface MaterialMasterDAO {
	List<MaterialMaster> fetchpclallrequestCounter = null;
	public void saveMaterialMaster(MaterialMaster materialmaster);
	public List<MaterialMaster> getAllMaterialMaster();
	public void updateMaterialMaster(MaterialMaster materialmaster, String status);
	public List<MaterialMaster> getPCLMaterialList();
	public List<MaterialMaster> getSAPMaterialList();
	public void sendEmailToUsers(List<MaterialMaster> materiallistreject, String subject);
	public List<MaterialMaster> getUserAllMaterialMasterRequest();
	public List<MydashboardView> getPendingRequestCounter();
	public List<MaterialMaster> fetchUserPendingRequest();
	public List<MaterialMaster> fetchUserCompletedRequest();
	public List<MaterialMaster> fetchRejectedRequest();
	public List<MaterialMaster> fetchplantpendingRequest();
	public List<MaterialMaster> fetchpclallReqStatus();
	public List<MaterialMaster> fetchpclapprovedRequest(String statusflag);
	public List<MaterialMaster> fetchpclRequestCounter();
	public List<MaterialMaster> fetchhodInprocessMaterialList();
	public List<MaterialMaster> fetchHODRejectedORCompletedMaterialList(String flagVal);
	public List<MaterialMaster> fetchHODCompletedMaterialList();
	public List<MaterialMaster> fetchPCLAllMaterialListdashboard();
	public List<MaterialMaster> fetchPlantWisePendingRequest();
	public List<MaterialMaster> fetchPlantWiseRequest(String plant, String statusFlag);
	public MaterialMaster fetchMaterialById(int keyid);
	public void sentMailFromEdpHOD(List<MaterialMaster> materiallistapprove);
	public List<MydashboardView> fetchplantwiseInbox();
	public List<MydashboardView> fetchpcldashboardinboxCounter();
	public List<MydashboardView> fetchhoddashboardInbox();	

}

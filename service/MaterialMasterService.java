package com.dtvs.dtvsonline.service;

import java.util.List;

import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MydashboardView;


public interface MaterialMasterService {
	public void addMaterial(MaterialMaster materialmaster);
	public List<MaterialMaster> fetchAllMaterialMaster();
	public List<MaterialMaster> fetchPCLMaterialList();
	public void saveMaterialArray(List<MaterialMaster> sdlist);	
	public void updateMaterialArray(List<MaterialMaster> sdList, String status);
	public List<MaterialMaster> fetchSAPMaterialList();
	public void sendMailToUsers(List<MaterialMaster> materiallistreject, String status);
	public List<MaterialMaster> fetchUserMaterialMasterRequest();
	public List<MydashboardView> UserPendingRequestCounetr();
	public List<MaterialMaster> UserPendingRequest();
	public List<MaterialMaster> UserCompletedRequest();
	public List<MaterialMaster> UserRejectedRequest();
	public List<MaterialMaster> plantPendingRequest();
	public List<MaterialMaster> pclallrequest();
	public List<MaterialMaster> pclapprovedrequest(String flagstatus);
	public List<MaterialMaster> pclallrequestcounter();
	public List<MaterialMaster> gethodInprocessMaterialList();
	public List<MaterialMaster> gethodRejectedOrCompletedMaterialList(String rejectedFlagVal);
	public List<MaterialMaster> gethodCompletedMaterialList();
	public List<MaterialMaster> getPCLallRequestList();
	public List<MaterialMaster> plantwisependingRequest();
	public List<MaterialMaster> plantwiseRequest(String plant, String statusFlag);
	public MaterialMaster getMaterialById(int keyid);
	public void updatematerialmaster(MaterialMaster material);
	public void sentMailByEDPHOD(List<MaterialMaster> materiallistapprove);
	public List<MydashboardView> getplantwiseInbox();
	public List<MydashboardView> getpcldashboardinboxCounter();
	public List<MydashboardView> gethoddashboardInbox();

}

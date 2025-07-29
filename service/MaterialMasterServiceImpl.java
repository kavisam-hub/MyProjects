package com.dtvs.dtvsonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtvs.dtvsonline.dao.MaterialMasterDAO;
import com.dtvs.dtvsonline.model.MaterialMaster;
import com.dtvs.dtvsonline.model.MydashboardView;


@Service
@Transactional
public class MaterialMasterServiceImpl implements MaterialMasterService {

	@Autowired
	private MaterialMasterDAO materialmasterdao;
	@Override
	public void addMaterial(MaterialMaster materialmaster) {
		
		materialmasterdao.saveMaterialMaster(materialmaster);	

	}
	@Override
	public List<MaterialMaster> fetchAllMaterialMaster() {		
		return materialmasterdao.getAllMaterialMaster();
	}
	@Override
	public void saveMaterialArray(List<MaterialMaster> sdlist) {
		
		if(! sdlist.isEmpty())
		{
			for(MaterialMaster matmstr : sdlist)
			{
				materialmasterdao.saveMaterialMaster(matmstr);
			}
		}		
	}
	@Override
	public void updateMaterialArray(List<MaterialMaster> sdList,String status) {
		
		if(!sdList.isEmpty())
		{
			for(MaterialMaster matmstr:sdList)
			{
				materialmasterdao.updateMaterialMaster(matmstr,status);
			}
		}
		
	}
	@Override
	public List<MaterialMaster> fetchPCLMaterialList() {
		
		return materialmasterdao.getPCLMaterialList();
	}
	@Override
	public List<MaterialMaster> fetchSAPMaterialList() {
		
		return materialmasterdao.getSAPMaterialList();
	}
	@Override
	public void sendMailToUsers(List<MaterialMaster> materiallistreject,String subject) {
				
		materialmasterdao.sendEmailToUsers(materiallistreject,subject);
		
	}
	@Override
	public List<MaterialMaster> fetchUserMaterialMasterRequest() {
		
		return materialmasterdao.getUserAllMaterialMasterRequest();
	}
	@Override
	public List<MydashboardView> UserPendingRequestCounetr() {
		
		return materialmasterdao.getPendingRequestCounter();
	}
	@Override
	public List<MaterialMaster> UserPendingRequest() {
		
		return materialmasterdao.fetchUserPendingRequest();
	}
	@Override
	public List<MaterialMaster> UserCompletedRequest() {
		
		return materialmasterdao.fetchUserCompletedRequest();
	}
	@Override
	public List<MaterialMaster> UserRejectedRequest() {
		
		return materialmasterdao.fetchRejectedRequest();
	}
	@Override
	public List<MaterialMaster> plantPendingRequest() {
		
		return materialmasterdao.fetchplantpendingRequest();
	}
	@Override
	public List<MaterialMaster> pclallrequest() {
		
		return materialmasterdao.fetchpclallReqStatus();
	}
	@Override
	public List<MaterialMaster> pclapprovedrequest(String statusflag) {
		
		return materialmasterdao.fetchpclapprovedRequest(statusflag);
	}
	@Override
	public List<MaterialMaster> pclallrequestcounter() {
		
		return materialmasterdao.fetchpclRequestCounter();
	}
	@Override
	public List<MaterialMaster> gethodInprocessMaterialList() {
		
		return materialmasterdao.fetchhodInprocessMaterialList();
	}
	@Override
	public List<MaterialMaster> gethodRejectedOrCompletedMaterialList(String FlagVal) {
		
		return materialmasterdao.fetchHODRejectedORCompletedMaterialList(FlagVal);
	}
	@Override
	public List<MaterialMaster> gethodCompletedMaterialList() {		
		return materialmasterdao.fetchHODCompletedMaterialList();
	}
	@Override
	public List<MaterialMaster> getPCLallRequestList() {
		return materialmasterdao.fetchPCLAllMaterialListdashboard();
	}
	@Override
	public List<MaterialMaster> plantwisependingRequest() {
		return materialmasterdao.fetchPlantWisePendingRequest();
	}
	@Override
	public List<MaterialMaster> plantwiseRequest(String plant, String statusFlag) {
		
		return materialmasterdao.fetchPlantWiseRequest(plant,statusFlag);
	}
	@Override
	public MaterialMaster getMaterialById(int keyid) {
		
		return materialmasterdao.fetchMaterialById(keyid);
	}
	@Override
	public void updatematerialmaster(MaterialMaster material) {
		
		String status="M";
		materialmasterdao.updateMaterialMaster(material, status);		
	}
	@Override
	public void sentMailByEDPHOD(List<MaterialMaster> materiallistapprove) {
		materialmasterdao.sentMailFromEdpHOD(materiallistapprove);
		
	}
	@Override
	public List<MydashboardView> getplantwiseInbox() {
		
		return materialmasterdao.fetchplantwiseInbox();
	}
	@Override
	public List<MydashboardView> getpcldashboardinboxCounter() {
		
		return materialmasterdao.fetchpcldashboardinboxCounter();
	}
	@Override
	public List<MydashboardView> gethoddashboardInbox() {
		
		return materialmasterdao.fetchhoddashboardInbox();
	}

}

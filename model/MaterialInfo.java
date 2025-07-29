package com.dtvs.dtvsonline.model;

public class MaterialInfo {
	private String materialType; 
	private String materialGroup;
	private String importindigenous;
	private String materialDescription;
	private String UOM;
	private String storage;
	private int HSNCode;
	private int materialCommodity;
	private String priceControl;
	private int valuationClass;
	private String profitCenter;
	private String materialCode;
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getMaterialGroup() {
		return materialGroup;
	}
	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	public String getImportindigenous() {
		return importindigenous;
	}
	public void setImportindigenous(String importindigenous) {
		this.importindigenous = importindigenous;
	}
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	public String getUOM() {
		return UOM;
	}
	public void setUOM(String uOM) {
		UOM = uOM;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public int getHSNCode() {
		return HSNCode;
	}
	public void setHSNCode(int hSNCode) {
		HSNCode = hSNCode;
	}
	public int getMaterialCommodity() {
		return materialCommodity;
	}
	public void setMaterialCommodity(int materialCommodity) {
		this.materialCommodity = materialCommodity;
	}
	public String getPriceControl() {
		return priceControl;
	}
	public void setPriceControl(String priceControl) {
		this.priceControl = priceControl;
	}
	public int getValuationClass() {
		return valuationClass;
	}
	public void setValuationClass(int valuationClass) {
		this.valuationClass = valuationClass;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	
	public String getProfitCenter() {
		return profitCenter;
	}
	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}
	@Override
	public String toString() {
		return "MaterialInfo [materialType=" + materialType + ", materialGroup=" + materialGroup + ", importindigenous="
				+ importindigenous + ", materialDescription=" + materialDescription + ", UOM=" + UOM + ", storage="
				+ storage + ", HSNCode=" + HSNCode + ", materialCommodity=" + materialCommodity + ", priceControl="
				+ priceControl + ", valuationClass=" + valuationClass + ", materialCode=" + materialCode + "]";
	}
	
}

package com.dtvs.dtvsonline.model;

import java.math.BigInteger;

public class MydashboardView {
	
	private BigInteger count;
	private String MMR_DEPT;
	private String MMR_STATUS;
	private String MMR_PLANT;
	public BigInteger getCount() {
		return count;
	}
	public void setCount(BigInteger count) {
		this.count = count;
	}
	public String getMMR_DEPT() {
		return MMR_DEPT;
	}
	public void setMMR_DEPT(String mMR_DEPT) {
		MMR_DEPT = mMR_DEPT;
	}
	public String getMMR_STATUS() {
		return MMR_STATUS;
	}
	public void setMMR_STATUS(String mMR_STATUS) {
		MMR_STATUS = mMR_STATUS;
	}
	
	public String getMMR_PLANT() {
		return MMR_PLANT;
	}
	public void setMMR_PLANT(String mMR_PLANT) {
		MMR_PLANT = mMR_PLANT;
	}
	@Override
	public String toString() {
		return "MydashboardView [count=" + count + ", MMR_DEPT=" + MMR_DEPT + ", MMR_STATUS=" + MMR_STATUS
				+ ", MMR_PLANT=" + MMR_PLANT + "]";
	}
	
		

}

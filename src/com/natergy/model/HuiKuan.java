package com.natergy.model;

public class HuiKuan {
	private String xingMing;
	private double huiKuan;
	public String getXingMing() {
		return xingMing;
	}
	public void setXingMing(String xingMing) {
		this.xingMing = xingMing;
	}
	public double getHuiKuan() {
		return huiKuan;
	}
	public void setHuiKuan(double huiKuan) {
		this.huiKuan = huiKuan;
	}
	@Override
	public String toString() {
		return "HuiKuan [xingMing=" + xingMing + ", huiKuan=" + huiKuan + "]";
	}
	
	
}

package com.natergy.model;

public class DingDan {
	private String xingMing;
	private String riQi;
	private double dingHuoLiang;
	private double QianKuan;
	public String getRiQi() {
		return riQi;
	}
	public void setRiQi(String riQi) {
		this.riQi = riQi;
	}
	public double getDingHuoLiang() {
		return dingHuoLiang;
	}
	public void setDingHuoLiang(double dingHuoLiang) {
		this.dingHuoLiang = dingHuoLiang;
	}
	public double getQianKuan() {
		return QianKuan;
	}
	public void setQianKuan(double qianKuan) {
		QianKuan = qianKuan;
	}
	public String getXingMing() {
		return xingMing;
	}
	public void setXingMing(String xingMing) {
		this.xingMing = xingMing;
	}
	@Override
	public String toString() {
		return "DingDan [xingMing=" + xingMing + ", riQi=" + riQi + ", dingHuoLiang=" + dingHuoLiang + ", QianKuan="
				+ QianKuan + "]";
	}
	
	
}

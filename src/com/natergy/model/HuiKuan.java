package com.natergy.model;

public class HuiKuan {
	private String xingMing;
	private double huiKuan;
	private String huiKuanRiQi;
	private String zhuangTai;
	public String getZhuangTai() {
		return zhuangTai;
	}
	public void setZhuangTai(String zhuangTai) {
		this.zhuangTai = zhuangTai;
	}
	public String getHuiKuanRiQi() {
		return huiKuanRiQi;
	}
	public void setHuiKuanRiQi(String huiKuanRiQi) {
		this.huiKuanRiQi = huiKuanRiQi;
	}
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
		return "HuiKuan [xingMing=" + xingMing + ", huiKuan=" + huiKuan + ", huiKuanRiQi=" + huiKuanRiQi
				+ ", zhuangTai=" + zhuangTai + "]";
	}
}

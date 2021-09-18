package com.natergy.model;

public class QianKuan{
	private String xingMing;
	private double qianKuan;

	public double getQianKuan() {
		return qianKuan;
	}

	public void setQianKuan(double qianKuan) {
		this.qianKuan = qianKuan;
	}

	public String getXingMing() {
		return xingMing;
	}

	public void setXingMing(String xingMing) {
		this.xingMing = xingMing;
	}

	@Override
	public String toString() {
		return "QianKuan [xingMing=" + xingMing + ", qianKuan=" + qianKuan + "]";
	}
}

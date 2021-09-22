package com.natergy.test;

import java.util.List;
import java.util.Map;

import com.natergy.dao.DingDanDao;
import com.natergy.model.DingDan;
import com.natergy.model.HuiKuan;
import com.natergy.model.QianKuan;

public class DealService {
	DingDanDao dingdandao=new DingDanDao();
	public int zengjiadingdan(DingDan dingdan){
		return dingdandao.ZengJiaDingDan(dingdan);
	}
	public int zengjiahuikuan(HuiKuan huikuan){
		return dingdandao.ZengJiaHuiKuan(huikuan);
	}
	public int zengjiaqiankuan(QianKuan qiankuan){
		return dingdandao.ZengJiaQianKuan(qiankuan);
	}
	public boolean existskehu(String xingming){
		return dingdandao.ExistsKeHu(xingming);
	}
	public List<Map<String,Object>> chaxundingdan(){
		return dingdandao.ChaXunDingDan();
	}
	public List<Map<String,Object>> chaxunqiankuan(){
		return dingdandao.ChaXunQianKuan();
	}
	public List<Map<String,Object>> chaxunhuikuan(){
		return dingdandao.ChaXunHuiKuan();
	}
	public List<Map<String,Object>> getdingdan(String xingming){
		return dingdandao.getDingDan(xingming);
	}
	public double getqiankuan(String xingming){
		return dingdandao.getQianKuan(xingming);
	}
	public int gengxinqiankuan(String xingming,double qiankuan){
		return dingdandao.GengXinQianKuan(xingming, qiankuan);
	}
	public int gengxindingdanqiankuan(String xingming,int id,double qiankuan){
		return dingdandao.GengXinDingDanQianKuan(xingming, id, qiankuan);
	}
	public int gengxindingdan(HuiKuan huikuan){
		return dingdandao.GengXinDingDan(huikuan);
	}
	
}

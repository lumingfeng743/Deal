package com.natergy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.natergy.model.DingDan;
import com.natergy.model.HuiKuan;
import com.natergy.model.QianKuan;
import com.natergy.util.C3P0Util;

public class DingDanDao {
	Connection con = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ResultSet resultset2=null;
	PreparedStatement pstmt = null;
	ResultSetMetaData metaData =null;
	//插入新订单数据
	public int ZengJiaDingDan(DingDan dingdan){
		int num=0;	
		
		String xingming=dingdan.getXingMing();
		String riqi=dingdan.getRiQi();
		double dinghuoliang=dingdan.getDingHuoLiang();
		double qiankuan=dingdan.getQianKuan();
		
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			String sql = "INSERT INTO 订单   "
					+ "(客户,下单日期,订货量,欠款) "
					+ " values ('"+xingming+"','"+riqi+"','"+dinghuoliang+"','"+qiankuan+"')";
			pstmt = con.prepareStatement(sql);			
			num = pstmt.executeUpdate();			
			C3P0Util.t_commitTransaction();//提交事务		
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(num==1&&ExistsKeHu(xingming)==true){//老客户
				double qiankuan2=getQianKuan(xingming);
				num=GengXinQianKuan(xingming,qiankuan2+qiankuan);
			}else{//新客户
				QianKuan qian=new QianKuan();
				qian.setXingMing(xingming);
				qian.setQianKuan(qiankuan);
				num=ZengJiaQianKuan(qian);
			}
		return num;
	}
	//插入新汇款数据
	public int ZengJiaHuiKuan(HuiKuan huikuan){
		int num=0;
		String xingming=huikuan.getXingMing();
		double huikuan1=huikuan.getHuiKuan();
		String huikuanriqi=huikuan.getHuiKuanRiQi();
		HuiKuan huikuan2=huikuan;
		num=GengXinDingDan(huikuan2);
		String zhuangtai="未处理";
		if(num==1){
			zhuangtai="已处理";
		}
		num=0;
		try{
			C3P0Util.t_beginTransaction();//开启事务
			con=C3P0Util.getConnection();
			String sql = "INSERT INTO 客户汇款   "
					+ "(客户,汇款,汇款日期,状态) "
					+ " values ('"+xingming+"','"+huikuan1+"','"+huikuanriqi+"','"+zhuangtai+"')";
			pstmt = con.prepareStatement(sql);			
			num = pstmt.executeUpdate();			
			C3P0Util.t_commitTransaction();//提交事务		
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return num;
	}
	//为新客户插入欠款数据
	public int ZengJiaQianKuan(QianKuan qiankuan){
		int num=0;
		String xingming=qiankuan.getXingMing();
		double qiankuan1=qiankuan.getQianKuan();
		try{
			C3P0Util.t_beginTransaction();//开启事务
			con=C3P0Util.getConnection();
			String sql = "INSERT INTO 客户欠款   "
					+ "(客户,欠款) "
					+ " values ('"+xingming+"','"+qiankuan1+"')";
			pstmt = con.prepareStatement(sql);			
			num = pstmt.executeUpdate();			
			C3P0Util.t_commitTransaction();//提交事务		
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return num;
	}
	//查询是否为新客户
	public boolean ExistsKeHu(String xingming){
		boolean flag=false;
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			statement = con.createStatement();
			String sql = "select * from `客户欠款` where `客户`='"+xingming+"'";
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				flag=true;
			}			
			C3P0Util.t_commitTransaction();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return flag;
	}
	//查询全部订单数据
	public List<Map<String,Object>> ChaXunDingDan(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			statement = con.createStatement();
			String sql = "select `客户`,`下单日期`,`订货量`,`欠款`  from `订单` ";
			resultSet = statement.executeQuery(sql);
			int i=1;		
			while(resultSet.next()){//循环读取	
				HashMap<String, Object> map = new HashMap<String, Object>();							
				map.put("Id", i);
				i++;
				map.put("客户",resultSet.getString("客户"));//从数据库读取的客户信息写入map	
				map.put("下单日期",resultSet.getString("下单日期"));//从数据库读取的下单日期写入map
				map.put("订货量",resultSet.getString("订货量"));//从数据库读取的订货量写入map
				map.put("欠款",resultSet.getString("欠款"));
				list.add(map);
			}	
			C3P0Util.t_commitTransaction();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return list;
	}
	//查询全部欠款数据
	public List<Map<String,Object>> ChaXunQianKuan(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			statement = con.createStatement();
			String sql = "select `客户`,`欠款` from `客户欠款` ";
			resultSet = statement.executeQuery(sql);
			int i=1;			
			while(resultSet.next()){//循环读取
				HashMap<String, Object> map = new HashMap<String, Object>();				
				map.put("Id", i);
				i++;
				map.put("客户",resultSet.getString("客户"));//从数据库读取的客户信息写入map	
				map.put("欠款",resultSet.getDouble("欠款"));
				list.add(map);
			}	
			C3P0Util.t_commitTransaction();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return list;
		}
	
	
	//查询全部汇款
	public List<Map<String,Object>> ChaXunHuiKuan(){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			statement = con.createStatement();
			String sql = "select `客户`,`汇款`,`汇款日期`,`状态` from `客户汇款`";
			resultSet = statement.executeQuery(sql);
			int i=1;			
			while(resultSet.next()){//循环读取
				HashMap<String, Object> map = new HashMap<String, Object>();				
				map.put("Id", i);
				i++;
				map.put("客户",resultSet.getString("客户"));//从数据库读取的客户信息写入map	
				map.put("汇款",resultSet.getString("汇款"));
				map.put("汇款日期",resultSet.getString("汇款日期"));
				map.put("状态",resultSet.getString("状态"));
				list.add(map);
			}	
			C3P0Util.t_commitTransaction();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return list;
	}
	//查询某个客户全部欠款
	public List<Map<String,Object>> getDingDan(String xingming){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			statement = con.createStatement();
			String sql = "select `欠款`,`id` from `订单` where `客户`='"+xingming+"' and `欠款`<> 0 ";
			resultSet = statement.executeQuery(sql);			
			while(resultSet.next()){//循环读取
				HashMap<String, Object> map = new HashMap<String, Object>();				
				map.put("id",resultSet.getInt("id"));
				map.put("欠款",resultSet.getDouble("欠款"));
				list.add(map);
			}	
			C3P0Util.t_commitTransaction();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return list;
	}
	//查询某个用户总欠款
	public double getQianKuan(String xingming){
		double qiankuan=0;
		try {
			C3P0Util.t_beginTransaction();//开启事务
			con = C3P0Util.t_getConnection();
			statement = con.createStatement();
			String sql = "select `欠款` from `客户欠款` where `客户`='"+xingming+"'";
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()){
				qiankuan=resultSet.getDouble("欠款");
			}			
			C3P0Util.t_commitTransaction();//提交事务
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return qiankuan;
	}
	//更新总欠款
	public int GengXinQianKuan(String xingming,double qiankuan){
		int num =0;
		try{
			C3P0Util.t_beginTransaction();//开启事务
			con=C3P0Util.getConnection();
			String sql = "UPDATE `客户欠款`   "
					+ "set  `欠款`='"+qiankuan+"' "
					+ " where `客户`='"+xingming+"'";
			pstmt = con.prepareStatement(sql);			
			num = pstmt.executeUpdate();			
			C3P0Util.t_commitTransaction();//提交事务		
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return num;
	}
	//更新一条订单欠款
	public int GengXinDingDanQianKuan(String xingming,int id,double qiankuan){
		int num =0;
		try{
			C3P0Util.t_beginTransaction();//开启事务
			con=C3P0Util.getConnection();
			String sql = "UPDATE `订单`   "
					+ "set  `欠款`='"+qiankuan+"' "
					+ " where `id`='"+id+"' and `客户`='"+xingming+"'";
			pstmt = con.prepareStatement(sql);			
			num = pstmt.executeUpdate();			
			C3P0Util.t_commitTransaction();//提交事务		
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				C3P0Util.t_releaseConnection(con);//释放连接
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return num;
	}
	//更新订单欠款
	public int GengXinDingDan(HuiKuan huikuan){
		int num=0;
		String xingming=huikuan.getXingMing();
		double huikuan1=huikuan.getHuiKuan();
		List<Map<String,Object>> list=getDingDan(xingming);
		if(list.size()==0){//客户不存在订单欠款条目,更新总欠款表后退出
			num=GengXinQianKuan(xingming,0-huikuan1);
			return num;
		}
		int i=0;
		double qiankuan2=getQianKuan(xingming);
		num=GengXinQianKuan(xingming,qiankuan2-huikuan1);//更新总欠款表
		while(huikuan1!=0){
			Map<String, Object> map= list.get(i);
			System.out.println(xingming);
			double qiankuan=(double) map.get("欠款");
			System.out.println(qiankuan);
			int id=(int)map.get("id");
			System.out.println(id);
			if(huikuan1<=qiankuan){//还款金额小于第一笔欠款，更新第一条数据后跳出循环
				num=GengXinDingDanQianKuan(xingming,id,qiankuan-huikuan1);	
				System.out.println(i+"："+(qiankuan-huikuan1));
				huikuan1=0;
			}else{//还款金额大于第一笔欠款，欠款条目清零，继续循环更新
				num=GengXinDingDanQianKuan(xingming,id,0);
				i++;
				huikuan1=huikuan1-qiankuan;
				System.out.println(i+":"+huikuan1);
			}
		}
		
		return num;		
	}
	
}

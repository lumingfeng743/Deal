package com.natergy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.natergy.model.DingDan;
import com.natergy.model.HuiKuan;
import com.natergy.test.DealService;

/**
 * Servlet implementation class XinZengHuiKuan
 */
@WebServlet("/XinZengHuiKuan.svlt")
public class XinZengHuiKuan extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");//解决中文乱码
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); //创建字符输出流
		String kehu=request.getParameter("kehu");
		System.out.println(kehu);
		String huikuanriqi=request.getParameter("huikuanriqi");
		System.out.println(huikuanriqi);
		String huikuanliang2=new String(request.getParameter("huikuan").getBytes("ISO8859_1"),"utf-8");
		Double huikuanliang=Double.parseDouble(huikuanliang2);
		System.out.println(huikuanliang);
		HuiKuan huikuan=new HuiKuan();
		huikuan.setHuiKuan(huikuanliang);
		huikuan.setHuiKuanRiQi(huikuanriqi);
		huikuan.setXingMing(kehu);
		huikuan.setZhuangTai("未处理");
		DealService dealservice=new DealService();
		int num=dealservice.zengjiahuikuan(huikuan);
		out.print(num);
		out.flush();
		out.close();
	}

}

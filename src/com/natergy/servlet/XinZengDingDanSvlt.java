package com.natergy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.natergy.model.DingDan;
import com.natergy.test.DealService;

/**
 * Servlet implementation class XinZengDingDanSvlt
 */
@WebServlet("/XinZengDingDan.svlt")
public class XinZengDingDanSvlt extends HttpServlet {
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
		String xiadanriqi=request.getParameter("xiadanriqi");
		System.out.println(xiadanriqi);
		String yaohuoliang=new String(request.getParameter("yaohuoliang").getBytes("ISO8859_1"),"utf-8");
		Double dinghuoliang=Double.parseDouble(yaohuoliang);
		System.out.println(dinghuoliang);
		DingDan dingdan=new DingDan();
		dingdan.setXingMing(kehu);
		dingdan.setDingHuoLiang(dinghuoliang);
		dingdan.setRiQi(xiadanriqi);
		dingdan.setQianKuan(dinghuoliang);
		DealService dealservice=new DealService();
		int num=dealservice.zengjiadingdan(dingdan);
		out.print(num);
		out.flush();
		out.close();
	}

}

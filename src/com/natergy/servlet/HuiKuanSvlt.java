package com.natergy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.natergy.test.DealService;

import net.sf.json.JSONException;
import net.sf.json.util.JSONStringer;

/**
 * Servlet implementation class HuiKuanSvlt
 */
@WebServlet("/HuiKuan.Svlt")
public class HuiKuanSvlt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");//解决中文乱码
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); //创建字符输出流
		DealService dealservice=new DealService();
		List<Map<String, Object>> xinxi=dealservice.chaxunhuikuan();//创建列表		
		JSONStringer stringer = new JSONStringer();//使用JSON保存列表数据
		stringer.array();
		for(int i=0;i<xinxi.size();i++){
			Map<String, Object> xin= xinxi.get(i);
			try {
				stringer.object();
				Iterator<String> it = xin.keySet().iterator(); //创建迭代器
				while (it.hasNext()) {
					Object key = it.next();
					stringer.key((String) key).value(xin.get(key));//传递数据
				}
				stringer.endObject();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		stringer.endArray();
		System.out.println(stringer.toString());
		out.print(stringer.toString());//传回数据给前端页面
		out.flush();
		out.close();
	}

}

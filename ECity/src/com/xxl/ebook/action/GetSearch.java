package com.xxl.ebook.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xxl.ebook.biz.IEBookBiz;
import com.xxl.ebook.biz.impl.EBookBizImpl;
import com.xxl.ebook.pojo.BookListBean;
import com.xxl.ebook.utils.ELog;

public class GetSearch extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String content  = request.getParameter("content");
		content =URLDecoder.decode(content,"utf-8");
		ELog.i(content);
		//content="斗";
		IEBookBiz ebookBiz =new EBookBizImpl();
		BookListBean  bookListBean =new BookListBean(ebookBiz.getBooksByContent(content));
		Gson gson =new Gson();
	
		response.getWriter().print(gson.toJson(bookListBean));

	}

}

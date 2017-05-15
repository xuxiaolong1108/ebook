package com.xxl.ebook.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xxl.ebook.biz.IEBookBiz;
import com.xxl.ebook.biz.impl.EBookBizImpl;
import com.xxl.ebook.pojo.BooksBean;

public class GetDownloadBooks extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookid  = request.getParameter("bookid");
		IEBookBiz eBookBiz =new EBookBizImpl();
		BooksBean booksBean = eBookBiz.getBookById(Integer.parseInt(bookid));
		Gson gson =new Gson();
		
		response.getWriter().print(gson.toJson(booksBean));
		
	}

}

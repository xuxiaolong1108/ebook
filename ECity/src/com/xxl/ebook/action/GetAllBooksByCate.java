package com.xxl.ebook.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xxl.ebook.biz.impl.EBookBizImpl;
import com.xxl.ebook.pojo.BookListBean;

public class GetAllBooksByCate extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cateid = request.getParameter("cateid");
		EBookBizImpl eBookBizImpl = new EBookBizImpl();
		BookListBean bookListBean =new BookListBean(eBookBizImpl.getAllBooksByCate(Integer
				.parseInt(cateid)));

		Gson gson = new Gson();
		response.getWriter().print(gson.toJson(bookListBean));
	}

}

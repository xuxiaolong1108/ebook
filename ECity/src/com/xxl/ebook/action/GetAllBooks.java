package com.xxl.ebook.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxl.ebook.biz.impl.EBookBizImpl;

public class GetAllBooks extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		EBookBizImpl eBookBizImpl = new EBookBizImpl();

		response.getWriter().print(
				eBookBizImpl.getAllBooks().get(0).getBookId()
						+ eBookBizImpl.getAllBooks().get(0).getBookName());
	}

}

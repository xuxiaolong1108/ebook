package com.xxl.ebook.biz.impl;

import java.util.List;

import com.xxl.ebook.biz.IEBookBiz;
import com.xxl.ebook.dao.impl.EBookDaoImpl;
import com.xxl.ebook.pojo.BookBean;
import com.xxl.ebook.pojo.BooksBean;

public class EBookBizImpl implements IEBookBiz{
	EBookDaoImpl eBookDaoImpl =new EBookDaoImpl();

	@Override
	public List<BookBean> getAllBooks() {
		// TODO Auto-generated method stub
		return eBookDaoImpl.getAllBooks();
	}

	@Override
	public List<BooksBean> getAllBooksByCate(int cateId) {
		// TODO Auto-generated method stub
		return eBookDaoImpl.getAllBooksByCate(cateId);
	}

	@Override
	public List<BooksBean> getRankingList() {
		// TODO Auto-generated method stub
		return eBookDaoImpl.getRankingList();
	}

	@Override
	public BooksBean getBookById(int bookid) {
		// TODO Auto-generated method stub
		return eBookDaoImpl.getBookById(bookid);
	}

	@Override
	public List<BooksBean> getBooksByContent(String content) {
		// TODO Auto-generated method stub
		return eBookDaoImpl.getBooksByContent(content);
	}

}

package com.xxl.ebook.biz;

import java.util.List;

import com.xxl.ebook.pojo.BookBean;
import com.xxl.ebook.pojo.BooksBean;

public interface IEBookBiz {
	List<BookBean> getAllBooks();
	List<BooksBean> getAllBooksByCate(int cateId);
	List<BooksBean> getRankingList();
	BooksBean getBookById(int bookid);
	List<BooksBean> getBooksByContent(String content);
}

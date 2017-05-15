package com.xxl.ebook.dao;

import java.util.List;

import com.xxl.ebook.pojo.BookBean;
import com.xxl.ebook.pojo.BooksBean;

public interface IEBookDao {
	/**
	 * test 
	 * @return
	 */
	List<BookBean> getAllBooks();
	/**
	 * 通过图书分类id 获取所有图书
	 * @return
	 */
	List<BooksBean> getAllBooksByCate(int cateId);
	
	/**
	 * 获取排行榜
	 * @return
	 */
	List<BooksBean> getRankingList();
	/**
	 * 根据bookid 下载图书
	 * @return
	 */
	BooksBean getBookById(int bookid);
	/**
	 * 搜索
	 * @param content
	 * @return
	 */
	List<BooksBean> getBooksByContent(String content);

}

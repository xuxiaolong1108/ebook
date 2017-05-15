package com.xxl.ebook.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sun.jdbc.odbc.JdbcOdbc;

import com.xxl.ebook.dao.IEBookDao;
import com.xxl.ebook.pojo.BookBean;
import com.xxl.ebook.pojo.BooksBean;
import com.xxl.ebook.utils.ELog;
import com.xxl.ebook.utils.JDBCUtil;

public class EBookDaoImpl implements IEBookDao {

	@Override
	public List<BookBean> getAllBooks() {
		List<BookBean> bookList = new ArrayList<BookBean>();
		Connection conn = JDBCUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from books";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int bookId = rs.getInt("id");
				String bookName = rs.getString("bookname");
				bookList.add(new BookBean(bookId, bookName));
			}
		} catch (SQLException e) {
			ELog.i("数据库检索异常");
			e.printStackTrace();
		} finally {
			JDBCUtil.free(rs, stmt, conn);
		}

		return bookList;
	}

	@Override
	public List<BooksBean> getAllBooksByCate(int cateId) {
		List<BooksBean> booksList = new ArrayList<BooksBean>();
		String sql = "select * from books,bookcate,bookauthor where books.cateid = bookcate.id and books.authorid = bookauthor.id and bookcate.id ="
				+ cateId;
		Connection conn = JDBCUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int bookId = rs.getInt("id");
				String bookName = rs.getString("bookname");
				String bookAuthor = rs.getString("authorname");
				String bookCate = rs.getString("catename");
				String bookAddress = rs.getString("bookaddress");
				String picAddress = rs.getString("picaddress");
				booksList.add(new BooksBean(bookId, bookName, bookAuthor,
						bookCate, bookAddress, picAddress));
			}
		} catch (SQLException e) {
			ELog.i("数据库检索异常");
			e.printStackTrace();
		} finally {
			JDBCUtil.free(rs, stmt, conn);
		}
		return booksList;
	}

	@Override
	public List<BooksBean> getRankingList() {
		List<BooksBean> booksList = new ArrayList<BooksBean>();
		String sql = "select * from books ,bookcate,bookauthor,bookdownload "
				+ "where books.cateid = bookcate.id and books.authorid = bookauthor.id and books.id =bookdownload.bookid  "
				+ "order by bookdownload.downloadtimes desc LIMIT 0,10";
		Connection conn = JDBCUtil.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int bookId = rs.getInt("id");
				String bookName = rs.getString("bookname");
				String bookAuthor = rs.getString("authorname");
				String bookCate = rs.getString("catename");
				String bookAddress = rs.getString("bookaddress");
				String picAddress = rs.getString("picaddress");
				booksList.add(new BooksBean(bookId, bookName, bookAuthor,
						bookCate, bookAddress, picAddress));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.free(rs, stmt, conn);
		}
		return booksList;
	}

	@Override
	public BooksBean getBookById(int bookid) {
		// 1 先将数据库中下载量加+1
		// 2再将图书信息返回
		String sqlAdd = "update bookdownload set downloadtimes =downloadtimes+1 where bookid ="
				+ bookid;
		Connection conn = JDBCUtil.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(sqlAdd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.free(null, stmt, conn);
		}
		String sql = "select * from books ,bookcate,bookauthor"
				+ " where books.cateid = bookcate.id and books.authorid = bookauthor.id"
				+ " and books.id =" + bookid;
		Connection conn1 = JDBCUtil.getConnection();
		Statement stmt1 = null;
		ResultSet rs = null;
		BooksBean booksBean = null;
		try {
			stmt1 = conn1.createStatement();
			rs = stmt1.executeQuery(sql);
			while (rs.next()) {
				int bookId = rs.getInt("id");
				String bookName = rs.getString("bookname");
				String bookAuthor = rs.getString("authorname");
				String bookCate = rs.getString("catename");
				String bookAddress = rs.getString("bookaddress");
				String picAddress = rs.getString("picaddress");
				booksBean = new BooksBean(bookId, bookName, bookAuthor,
						bookCate, bookAddress, picAddress);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.free(rs, stmt1, conn1);
		}
		return booksBean;
	}

	@Override
	public List<BooksBean> getBooksByContent(String content) {
		List<BooksBean> booksList = new ArrayList<BooksBean>();
		String sql = " select * from books ,bookcate,bookauthor  where books.cateid = bookcate.id and books.authorid = bookauthor.id and (books.bookname LIKE '%"+content+"%' OR  bookauthor.authorname LIKE '%"+content+"%')";
		Connection conn = JDBCUtil.getConnection();
		Statement stmt =null;
		ResultSet rs =null;
		BooksBean booksBean = null;
		try {
			stmt = conn.createStatement();
			rs= stmt.executeQuery(sql);
			while (rs.next()) {
				int bookId = rs.getInt("id");
				String bookName = rs.getString("bookname");
				String bookAuthor = rs.getString("authorname");
				String bookCate = rs.getString("catename");
				String bookAddress = rs.getString("bookaddress");
				String picAddress = rs.getString("picaddress");
				booksBean = new BooksBean(bookId, bookName, bookAuthor,
						bookCate, bookAddress, picAddress);
				booksList.add(booksBean);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCUtil.free(rs, stmt, conn);
		}
		return booksList;
	}
}

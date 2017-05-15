package com.xxl.ebook.pojo;

public class BooksBean {
	private int bookId;
	private String bookName;
	private String bookAuthor;
	private String bookCate;
	private  String bookAddress;
	private String picAddress;
	
	
	public BooksBean(int bookId, String bookName, String bookAuthor,
			String bookCate, String bookAddress, String picAddress) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookCate = bookCate;
		this.bookAddress = bookAddress;
		this.picAddress = picAddress;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookCate() {
		return bookCate;
	}
	public void setBookCate(String bookCate) {
		this.bookCate = bookCate;
	}
	public String getBookAddress() {
		return bookAddress;
	}
	public void setBookAddress(String bookAddress) {
		this.bookAddress = bookAddress;
	}
	public String getPicAddress() {
		return picAddress;
	}
	public void setPicAddress(String picAddress) {
		this.picAddress = picAddress;
	}
	
	
}

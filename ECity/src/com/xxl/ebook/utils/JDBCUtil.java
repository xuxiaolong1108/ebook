package com.xxl.ebook.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/ebook";
	private static final String USER = "root";
	private static final String PSD = "123";
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			ELog.i("未找到 mysql的驱动");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USER, PSD);
		} catch (SQLException e) {
			ELog.i("获取数据库连接失败");
			e.printStackTrace();
		}
		return null;
	}

	public static void free(ResultSet rs, Statement stmt, Connection conn) {

		try {
			if (null != rs) {
				rs.close();
			}
		} catch (SQLException e) {
			ELog.i("ResultSet 释放失败");	
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				ELog.i("Statement 释放失败");
				e.printStackTrace();
			}finally{
				try {
					conn.close();
				} catch (SQLException e) {
					ELog.i("Connection 释放失败");
					e.printStackTrace();
				}
			}
		}
	}

}

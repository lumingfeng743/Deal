package com.natergy.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;



public class C3P0Util 
{
	private static ComboPooledDataSource dataSource = null;
	
	static 
	{
		dataSource = new ComboPooledDataSource();//读取默认配置文件
	}
	
	
    

    public static Connection getConnection()
    {
        try 
        {
            Connection connection = dataSource.getConnection();
            return connection;
        } 
        catch (SQLException e) 
        {
        	e.printStackTrace();
        	System.out.println(e.getMessage());
            return null;
        }
    }
    
    //多个ResultSet、Statement的应该用数组参数循环处理更合理，测出业务简单直接固定写死了
    public static void release(
    		ResultSet resultSet1, ResultSet resultSet2, 
    		Statement statement1, Statement statement2, 
    		Connection connection) 
    {
    	if (resultSet1 != null) 
    	{
            try 
            {
                resultSet1.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    	if (resultSet2 != null) 
    	{
            try 
            {
                resultSet2.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        if (statement1 != null) 
        {
            try 
            {
            	statement1.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        if (statement2 != null) 
        {
            try 
            {
            	statement2.close();
            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
        if (connection!= null)
        {
            try 
            {
            	//看Connection来自哪里, 如果Connection是从连接池里面获得的, close()方法其实是归还; 如果Connection是创建的, 就是销毁
                connection.close();
            }
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
		
	}




    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*ThreadLocal
     * 它为null表示没有事务
     * 它不为null表示有事务
     * 当事物开始时，需要给它赋值
     * 当事务结束时，需要给它赋null
     * 并且在开启事务时，让dao的多个方法共享这个Connection
     */
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    /**
     * 需要手动开始事务时
     * dao使用本方法来获取连接
     * @return
     * @throws SQLException
     */
    public static Connection t_getConnection() throws SQLException{
        Connection con = tl.get();
        if(con != null){
            return con;
        }
        return dataSource.getConnection();
    }
    
    /**
     * 开启事务
     * @throws SQLException 
     */
    public static void t_beginTransaction() throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if(con != null) throw new SQLException("已经开启了事务，不能重复开启！");
        con = dataSource.getConnection();//给con赋值，表示开启了事务
        con.setAutoCommit(false);//设置为手动提交
        tl.set(con);//把当前事务连接放到tl中
    }
    
    /**
     * 提交事务
     * @throws SQLException 
     */
    public static void t_commitTransaction() throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if(con == null) {
            throw new SQLException("没有事务不能提交！");
        }
        con.commit();//提交事务
        con.close();//关闭连接
        con = null;//表示事务结束！
        tl.remove();
    }
    
    /**
     * 回滚事务
     * @throws SQLException 
     */
    public static void t_rollbackTransaction() throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if(con == null) {
            throw new SQLException("没有事务不能回滚！");
        }
        con.rollback();
        con.close();
        con = null;
        tl.remove();
    }
    
    /**
     * 释放Connection
     * @param con
     * @throws SQLException 
     */
    public static void t_releaseConnection(Connection connection) throws SQLException {
        Connection con = tl.get();//获取当前线程的事务连接
        if(connection != con) {//如果参数连接，与当前事务连接不同，说明这个连接不是当前事务，可以关闭！
            if(connection != null &&!connection.isClosed()) {//如果参数连接没有关闭，关闭之！
                connection.close();
            }
        }
    }

}

package PServer;

import java.sql.*;
public class Connection 
{
	private java.sql.Connection conn;
	private Statement statement;
	private ResultSet St;
	public static boolean connectedToDataBase = false;
	public Connection() 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pizzashop","root","Pa$$w0rd");
			statement = conn.createStatement();
			setConnectedToDataBase(true);
		} catch (Exception e) 
		{
			setConnectedToDataBase(false);
			System.out.println(e.getMessage()+" connection method");
		}
	}
	public Connection(String port, String database, String user,String password) 
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/"+database+"",""+user+"",""+password+"");
			statement = conn.createStatement();
			setConnectedToDataBase(true);
		} catch (Exception e) 
		{
			setConnectedToDataBase(false);
			System.out.println(e.getMessage()+" connection method");
		}
	}
	public void CloseConnection()
	{
		try {
			setConnectedToDataBase(false);
			statement.close();
			statement.close();
			conn.clearWarnings();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage()+" closed");
		}
	}
	public void setResutStatement(ResultSet rs)
	{
		St = rs;
	}
	
	public ResultSet getResultSet()
	{
		return St;
	}
	public java.sql.Connection getConnection()
	{
		return conn;
	}
	public java.sql.Connection getConn() {
		return conn;
	}
	public void setConn(java.sql.Connection conn) {
		this.conn = conn;
	}
	public Statement getStatement() {
		return statement;
	}
	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public boolean isConnectedToDataBase() {
		return connectedToDataBase;
	}

	public void setConnectedToDataBase(boolean connectedToDataBase) {
		Connection.connectedToDataBase = connectedToDataBase;
	}
	
}

import java.sql.*;

public class DataBase {
	
	private Connection conn;
	private Statement stmt;
	
	public DataBase(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/test";
			conn = DriverManager.getConnection(url, "root", "root");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getStackTrace());
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
		}
	}//constructor
	
	//This method creates a new table
	public void createTable(String tableName, String fields){
	//Create new table
		
		String line = "CREATE TABLE "+tableName+" "+ fields;
		try {
			stmt.executeUpdate("DROP TABLE " + tableName);
			stmt.executeUpdate(line);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//createTable
	
	
	//Insert to table
	public void insertToTable(String tableName, String values){
		String line = "INSERT INTO "+tableName+values;
	try {
		stmt.executeUpdate(line);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}//insertToTable
}//class DataBase

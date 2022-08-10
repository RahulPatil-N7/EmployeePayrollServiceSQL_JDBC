package com.bridgelabz.JDBCTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 *Java program to connect payroll_service database.
 */
public class EmployeePayrollJDBC {

	public static final String retrieveData = "SELECT * FROM employee_payroll";

	public static void main(String[] args) throws SQLException {
		System.out.println("************Welcome To Employee Payroll Service************\n");
		// UC1 - Connecting to database
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String passWord = "Rahul@N77";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
			System.out.println("Connecting to database : " + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, passWord);
			System.out.println("Connection is successful!" + connection);
			Statement statement = connection.createStatement();
			/*
			 * UC2 - Retrieve employee data from table
			 */
			ResultSet resultSet = statement.executeQuery(retrieveData);
			while (resultSet.next()) {
				System.out.println("\nId :" + resultSet.getInt(1) + "\nName :" + resultSet.getString(2) + "\nPhone :"
						+ resultSet.getString(3) + "\nAddress : " + resultSet.getString(4) + "\nDepartment "
						+ resultSet.getString(5) + "\nGender :" + resultSet.getString(6) + "\nBasic_Pay :"
						+ resultSet.getDouble(7) + "\nJoin Date :" + resultSet.getDate(8));
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			connection.close();
		}
	}

}

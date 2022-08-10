package com.bridgelabz.JDBCTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *Java program to connect payroll_service database.
 */
public class EmployeePayrollJDBC {

	public static final String retrieveData = "SELECT * FROM employee_payroll";
	public static final String updateData = "UPDATE employee_payroll SET Basic_Pay = 300000 where Name = 'Terissa'";
	public static final String joindateData = "SELECT * FROM employee_payroll where Start_Date between cast('2022-07-01' as date) and date (now())";
	public static final String addData = "insert into employee_payroll values(10, 'Tom', 9967223344, 'Navi Mumbai', 'Marketing', 'M', 50000, '2022-08-10', 50000, 45000, 9000, 36000)";

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
			PreparedStatement preparedStatement = connection.prepareStatement(updateData);
			// UC 3,4 - Update data & sync with database using prepared statement
			preparedStatement.execute(updateData);
			/*
			 * UC5 - Retrieve all employees who joined on particular date range
			 */
			ResultSet resultSet = preparedStatement.executeQuery(joindateData);
			/*
			 * UC7 - Ability to add new employee to the payroll
			 */
			PreparedStatement preparedStatementInsert = connection.prepareStatement(addData);
			preparedStatementInsert.execute(addData);
			/*
			 * UC2 - Retrieve employee data from table
			 */
			System.out.println("\nRetrieving all data from table :");
			while (resultSet.next()) {
				System.out.println("\nId :" + resultSet.getInt(1) + "\nName :" + resultSet.getString(2) + "\nPhone :"
						+ resultSet.getString(3) + "\nAddress : " + resultSet.getString(4) + "\nDepartment "
						+ resultSet.getString(5) + "\nGender :" + resultSet.getString(6) + "\nBasic_Pay :"
						+ resultSet.getDouble(7) + "\nJoin Date :" + resultSet.getDate(8));
			}

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

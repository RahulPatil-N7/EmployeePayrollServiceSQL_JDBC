package com.bridgelabz.JDBCTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 *Java program to connect payroll_service database.
 */
public class EmployeePayrollJDBC {

	public static final String RETRIEVE_DATA = "SELECT * FROM employee_payroll";
	public static final String UPDATE_DATA = "UPDATE employee_payroll SET Basic_Pay = 300000 where Name = 'Rahul'";
	public static final String JOIN_DATE_DATA = "SELECT * FROM employee_payroll where Start_Date between cast('2022-07-01' as date) and date (now())";
	public static final String ADD_DATA = "insert into employee_payroll values(13, 'Bill', 9967223340, 'Mumbai', 'Marketing', 'M', 50000, '2022-08-10', 50000, 45000, 9000, 36000)";
	public static final String ADD_DEPARTMENT_DATA = "insert into employee_deparment values(13, 'Marketing')";
	private static Scanner scanner;

	// UC1 - Connecting to database
	public static Connection getConnection() {
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
			return connection;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println("************Welcome To Employee Payroll Service************\n");
		Statement statement = getConnection().createStatement();
		boolean exit = false;
		while (!exit) {
			System.out.println(
					"\nEmployee Payroll Service Database Menu.\n1. Retrive Data.\n2. Update Data.\n3. Join Date Data.\n4. Insert Data.\n5. Exit");
			scanner = new Scanner(System.in);
			int choice = scanner.nextInt();
			switch (choice) {
			case 1: /*
					 * UC2 - Retrieve employee data from table
					 */
				System.out.println("\nRetrieving all data from table :");
				ResultSet resultSet = statement.executeQuery(RETRIEVE_DATA);
				while (resultSet.next()) {
					System.out.println("\nId :" + resultSet.getInt(1) + "\nName :" + resultSet.getString(2)
							+ "\nPhone :" + resultSet.getString(3) + "\nAddress : " + resultSet.getString(4)
							+ "\nDepartment " + resultSet.getString(5) + "\nGender :" + resultSet.getString(6)
							+ "\nBasic_Pay :" + resultSet.getDouble(7) + "\nJoin Date :" + resultSet.getDate(8));
				}
				break;
			case 2: // UC 3,4 - Update data & sync with database using prepared statement
				PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_DATA);
				preparedStatement.execute(UPDATE_DATA);
				System.out.println("\nData Updated & Synced With Database!");
				break;
			case 3: // UC5 - Retrieve all employees who joined on particular date range
				ResultSet result = statement.executeQuery(JOIN_DATE_DATA);
				while (result.next()) {
					System.out.println("\nId :" + result.getInt(1) + "\nName :" + result.getString(2) + "\nPhone :"
							+ result.getString(3) + "\nAddress : " + result.getString(4) + "\nDepartment "
							+ result.getString(5) + "\nGender :" + result.getString(6) + "\nBasic_Pay :"
							+ result.getDouble(7) + "\nJoin Date :" + result.getDate(8));
				}
				break;
			case 4: // UC 7 - Ability to add new employee to the payroll
				try {
					statement.execute(ADD_DATA);
					statement.execute(ADD_DEPARTMENT_DATA); // UC 8 -Ability to add department Data to Both tables
					System.out.println("\nData Added & Synced With Database!");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Thanks !");
				exit = true;
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

}

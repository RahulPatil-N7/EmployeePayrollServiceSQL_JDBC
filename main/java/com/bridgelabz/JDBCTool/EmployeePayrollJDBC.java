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

	public static final String retrieveData = "SELECT * FROM employee_payroll";
	public static final String updateData = "UPDATE employee_payroll SET Basic_Pay = 300000 where Name = 'Vignesh'";
	public static final String joinDateData = "SELECT * FROM employee_payroll where Start_Date between cast('2022-07-01' as date) and date (now())";
	public static final String addData = "insert into employee_payroll values(10, 'Tom', 9967223344, 'Navi Mumbai', 'Marketing', 'M', 50000, '2022-08-10', 50000, 45000, 9000, 36000)";
	private static Scanner scanner;

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
					ResultSet resultSet = statement.executeQuery(retrieveData);
					while (resultSet.next()) {
						System.out.println("\nId :" + resultSet.getInt(1) + "\nName :" + resultSet.getString(2)
								+ "\nPhone :" + resultSet.getString(3) + "\nAddress : " + resultSet.getString(4)
								+ "\nDepartment " + resultSet.getString(5) + "\nGender :" + resultSet.getString(6)
								+ "\nBasic_Pay :" + resultSet.getDouble(7) + "\nJoin Date :" + resultSet.getDate(8));
					}
					break;
				case 2: // UC 3,4 - Update data & sync with database using prepared statement
					PreparedStatement preparedStatement = connection.prepareStatement(updateData);
					preparedStatement.execute(updateData);
					System.out.println("\nData Updated & Synced With Database!");
					break;
				case 3: // UC5 - Retrieve all employees who joined on particular date range
					ResultSet result = statement.executeQuery(joinDateData);
					while (result.next()) {
						System.out.println("\nId :" + result.getInt(1) + "\nName :" + result.getString(2) + "\nPhone :"
								+ result.getString(3) + "\nAddress : " + result.getString(4) + "\nDepartment "
								+ result.getString(5) + "\nGender :" + result.getString(6) + "\nBasic_Pay :"
								+ result.getDouble(7) + "\nJoin Date :" + result.getDate(8));
					}
					break;
				case 4: // UC 7 Ability to add new employee to the payroll
					try {
						statement.execute(addData);
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

		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			connection.close();
		}
	}

}

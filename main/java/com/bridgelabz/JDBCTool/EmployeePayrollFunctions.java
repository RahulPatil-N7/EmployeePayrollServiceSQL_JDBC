package com.bridgelabz.JDBCTool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeePayrollFunctions {
	private static Scanner scanner;

	public static void main(String[] args) throws Exception {
		scanner = new Scanner(System.in);
		boolean exit = false;
		while (!exit) {
			/*
			 * UC6 - Ability to find sum, average, min,max and number of male and female
			 * employees
			 */
			System.out.println(
					"\n<------- Choose Database Function -------> \n1 : Sum \n2 : Average \n3 : Minimum \n4 : Maximum \n5 : Count \n6 : Exit ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				connectDatabase("select sum(Basic_Pay) from employee_payroll where gender = 'M' group by gender");
				break;
			case 2:
				connectDatabase("select avg(Basic_Pay) from employee_payroll where gender = 'F' group by gender");
				break;
			case 3:
				connectDatabase("select min(Basic_Pay) from employee_payroll where gender = 'M' group by gender");
				break;
			case 4:
				connectDatabase("select max(Basic_Pay) from employee_payroll where gender = 'F' group by gender");
				break;
			case 5:
				System.out.println("Number of Male employees :");
				connectDatabase("select count(*) from employee_payroll where gender = 'M'");
				System.out.println("Number of Female employees :");
				connectDatabase("select count(*) from employee_payroll where gender = 'F'");
				break;
			case 6:
				System.out.println("Exiting Program, Thanks!");
				exit = true;
				break;
			default:
				System.out.println("Invalid Input ! Please enter valid input !");
			}
		}
	}

	// Method to connect to database & pass query statement
	private static void connectDatabase(String string) throws Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service?useSSL=false", "root",
					"Rahul@N77");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(string);
			System.out.println("Connected to payroll_sercice database!");
			while (resultSet.next()) {
				System.out.println(resultSet.getInt(1));
			}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			connection.close();
		}
	}
}

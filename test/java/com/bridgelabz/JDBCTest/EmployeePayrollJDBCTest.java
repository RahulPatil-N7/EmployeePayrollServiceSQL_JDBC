package com.bridgelabz.JDBCTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.JDBCTool.EmployeePayrollJDBC;

public class EmployeePayrollJDBCTest {

	String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	String userName = "root";
	String passWord = "Rahul@N77";
	Connection connection = null;
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
			System.out.println("Connecting to database : " + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL, userName, passWord);
			System.out.println("Connection is successful!" + connection);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	/*
	 * UC 3 - Test to compare results with payroll_service database
	 */
	@Test
	public void given_UpdateQueryStatementShouldReturnTrue_ResultShouldSyncWithDatabase() throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(EmployeePayrollJDBC.updateData);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM employee_payroll where Name = 'Rahul'");
		Double result = 0.0;
		while (resultSet.next()) {
			result = resultSet.getDouble(7);
		}
		Double expexted = 300000.0;
		Assert.assertEquals(expexted, result);
	}

	/*
	 * UC 4 - Test to compare results of prepared Statement with payroll_service
	 * database
	 */
	@Test
	public void given_UpdateQueryPreparedStatementShouldReturnTrue_ResultShouldSyncWithDatabase() throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(EmployeePayrollJDBC.updateData);
		preparedStatement.execute(EmployeePayrollJDBC.updateData);
		ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM employee_payroll where Name = 'Terissa'");
		Double result = 0.0;
		while (resultSet.next()) {
			result = resultSet.getDouble(7);
		}
		Double expexted = 300000.0;
		Assert.assertEquals(expexted, result);
	}

	/*
	 * UC 7 - Test to compare results of insert query prepared Statement with
	 * payroll_service database
	 */
	@Test
	public void given_InsertQueryPreparedStatementShouldReturnTrue_ResultShouldSyncWithDatabase() throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(EmployeePayrollJDBC.updateData);
		preparedStatement.execute(EmployeePayrollJDBC.updateData);
		ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM employee_payroll where Name = 'Tom'");
		String result = null;
		while (resultSet.next()) {
			result = resultSet.getString(2);
		}
		String expexted = "Tom";
		Assert.assertEquals(expexted, result);
	}

}

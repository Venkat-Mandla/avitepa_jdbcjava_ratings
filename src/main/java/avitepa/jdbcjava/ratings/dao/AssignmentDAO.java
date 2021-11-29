/**
 * 
 */
package avitepa.jdbcjava.ratings.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import avitepa.jdbcjava.ratings.Configuration;
import avitepa.jdbcjava.ratings.domain.Assignment;

/**
 * @author Vijay M
 *
 */
public class AssignmentDAO {

	private static final AssignmentDAO ASSIGNMENT_SERVICE = new AssignmentDAO();

	private AssignmentDAO() {
		// DO nothing
	}

	

	/**
	 * This method returns the instance of this (AssignmentService) class
	 * 
	 * @return AssignmentService
	 */
	public static AssignmentDAO getInstance() {
		return ASSIGNMENT_SERVICE;
	}

	/**
	 * This method saves student category record
	 * 
	 * @param assignment
	 * @return String
	 */
	public String save(Assignment assignment) {

		Connection connection = Configuration.getInstance().getConnection();

		String sql = "insert into assignments values (? ,?, ?, ?, ?, ?)";

		PreparedStatement preparedStatement;
		Integer flag = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, assignment.getSerialNumber());
			preparedStatement.setString(2, assignment.getStudentName());
			preparedStatement.setString(3, assignment.getSubject());
			preparedStatement.setString(4, assignment.getAssignmentCategory());
			preparedStatement.setDate(5, new Date(assignment.getDataOfSubmission().getTime()));
			preparedStatement.setInt(6, assignment.getPoints());
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String statusMsg;
		if (Objects.isNull(flag) || flag == 0) {
			statusMsg = "No records inserted. Student " + assignment.getStudentName() + " with assignment category : "
					+ assignment.getAssignmentCategory();
		} else {
			statusMsg = "Successfully enrolled student: " + assignment.getStudentName() + " with assignment category : "
					+ assignment.getAssignmentCategory();
		}
		return statusMsg;
	}
	
	
	/**
	 * This method updates student category record if available
	 * 
	 * @param assignment
	 * @return String
	 */
	public String update(Assignment assignment) {

		Connection connection = Configuration.getInstance().getConnection();
		

		String sql = "update assignments set serial_number= ?, date_of_submition=?, points =? where student_name =? and subject=? and assignment_category=?";

		PreparedStatement preparedStatement;
		Integer flag = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, assignment.getSerialNumber());
			preparedStatement.setDate(2, new Date(assignment.getDataOfSubmission().getTime()));
			preparedStatement.setInt(3, assignment.getPoints());
			preparedStatement.setString(4, assignment.getStudentName());
			preparedStatement.setString(5, assignment.getSubject());
			preparedStatement.setString(6, assignment.getAssignmentCategory());
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String statusMsg;
		if (Objects.isNull(flag) || flag == 0) {
			statusMsg = "No records updated. Student " + assignment.getStudentName() + " with assignment category : "
					+ assignment.getAssignmentCategory();
		} else {
			statusMsg = "Successfully updated record, student: " + assignment.getStudentName() + " with assignment category : "
					+ assignment.getAssignmentCategory();
		}
		return statusMsg;
	}

	/**
	 * This method deletes the student assignment category records if available
	 * 
	 * @param assignment
	 * @return String
	 */
	public String delete(Assignment assignment) {
		String sql = "delete from assignments where student_name=? and subject=? and assignment_category =?";

		Connection connection = Configuration.getInstance().getConnection();

		PreparedStatement preparedStatement;
		Integer flag = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, assignment.getStudentName());
			preparedStatement.setString(2, assignment.getSubject());
			preparedStatement.setString(3, assignment.getAssignmentCategory());
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String statusMsg;
		if (Objects.isNull(flag) || flag == 0) {
			statusMsg = "Assignment record not found for student " + assignment.getStudentName()
			+ " with assignment category : " + assignment.getAssignmentCategory();
		} else {
			statusMsg = "Successfully deleted assignment category record for student: " + assignment.getStudentName()
			+ " with assignment category : " + assignment.getAssignmentCategory();
		}
		return statusMsg;
	}

	/**
	 * This method returns all assignment records for student
	 * 
	 * @return Set<Assignment>
	 */
	public Set<Assignment> getAssignmentsByStudent(String student) {
		String sql = "select serial_number,student_name,subject,assignment_category,date_of_submition,points from assignments where student_name=?";
		Set<Assignment> assignments = getAssignments(student, sql);
		return assignments;
	}
	
	/**
	 * This method returns all assignment records for subject
	 * 
	 * @return Set<Assignment>
	 */
	public Set<Assignment> getAssignmentsBySubject(String subject) {
		String sql = "select serial_number,student_name,subject,assignment_category,date_of_submition,points from assignments where subject=?";
		Set<Assignment> assignments = getAssignments(subject, sql);
		return assignments;
	}

	private Set<Assignment> getAssignments(String input, String sql) {
		
		Set<Assignment> assignments = null;
		PreparedStatement preparedStatement;
		Connection connection =null;
		try {
			connection = Configuration.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, input);
			ResultSet resultSet = preparedStatement.executeQuery();
			assignments = new HashSet<>();
			while (resultSet.next()) {
				Assignment assignment = new Assignment();
				assignment.setSerialNumber(resultSet.getInt("serial_number"));
				assignment.setStudentName(resultSet.getString("student_name"));
				assignment.setSubject(resultSet.getString("subject"));
				assignment.setAssignmentCategory(resultSet.getString("assignment_category"));
				assignment.setDataOfSubmission(resultSet.getDate("date_of_submition"));
				assignment.setPoints(resultSet.getInt("points"));
				assignments.add(assignment);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assignments;
	}
	
	

}

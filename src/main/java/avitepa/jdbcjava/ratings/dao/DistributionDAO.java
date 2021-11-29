/**
 * 
 */
package avitepa.jdbcjava.ratings.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import avitepa.jdbcjava.ratings.Configuration;
import avitepa.jdbcjava.ratings.exception.AssignmentCategoryException;
import avitepa.jdbcjava.ratings.exception.JdbcConnectionException;

/**
 * @author Vijay M
 *
 */
public class DistributionDAO {

	private static final DistributionDAO DISTRIBUTION_SERVICE = new DistributionDAO();


	private Map<String, Integer> assignmentCategories;
	private DistributionDAO() {
		// Do nothing
	}

	public static DistributionDAO getInstance() {
		return DISTRIBUTION_SERVICE;
	}

	/**
	 * This method return the gets the assignment categories only once..(as this data is static in nature)
	 * 
	 * @return Map<String, Integer> 
	 */
	public Map<String, Integer> getAssignmentCategories() {
		
		if(Objects.nonNull(assignmentCategories) && !assignmentCategories.isEmpty()) {
			return assignmentCategories;
		}
		String sql = "select assignment_category,weight from distribution";

		Connection connection = Configuration.getInstance().getConnection();
		assignmentCategories=new HashMap<>();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet	resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				assignmentCategories.put(resultSet.getString("assignment_category"), resultSet.getInt("weight"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JdbcConnectionException("Unable to fetch distribution details", e);
		}
		return assignmentCategories;
	}

	/**
	 * This method adds the category if not already available
	 * 
	 * @param category
	 * @param weight
	 * @return String
	 * @throws AssignmentCategoryException
	 */
	public String addAssignmentCategory(String category, int weight) {
		String sql = "insert into distribution values (? ,?)";

		Connection connection = Configuration.getInstance().getConnection();

		PreparedStatement preparedStatement;
		Integer flag = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, category);
			preparedStatement.setInt(2, weight);
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String statusMsg;
		if (Objects.isNull(flag) || flag == 0) {
			statusMsg = "No records inserted. category " + category + " with assignment weight : " + weight;
		} else {
			statusMsg = "Successfully enrolled student: " + category + " with assignment weight : " + weight;
		}
		return statusMsg;

	}

	/**
	 * This method removes the category if available
	 * 
	 * @param category
	 * @return boolean
	 */
	public String removeAssignmentCategory(String category) {
		String sql = "delete from distribution where  assignment_category =?";

		Connection connection = Configuration.getInstance().getConnection();

		PreparedStatement preparedStatement;
		Integer flag = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, category);
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String statusMsg;
		if (Objects.isNull(flag) || flag == 0) {
			statusMsg = "No records deleted. category " + category ;
		} else {
			statusMsg = "Successfully deleted the record, category: "+ category;
		}
		return statusMsg;
	}

	/**
	 * 
	 * @param category
	 * @param weight
	 * @return
	 */
	public String modifyAssignmentCategory(String category, int weight) {
		String sql = "update distribution set weight=? where assignment_category =?";

		Connection connection = Configuration.getInstance().getConnection();

		PreparedStatement preparedStatement;
		Integer flag = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, weight);
			preparedStatement.setString(2, category);
			flag = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String statusMsg;
		if (Objects.isNull(flag) || flag == 0) {
			statusMsg = "No records updated. category " + category ;
		} else {
			statusMsg = "Successfully updated the record, category: "+ category;
		}
		return statusMsg;
	}
}

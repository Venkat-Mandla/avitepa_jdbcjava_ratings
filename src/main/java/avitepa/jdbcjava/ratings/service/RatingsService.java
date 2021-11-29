/**
 * 
 */
package avitepa.jdbcjava.ratings.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import avitepa.jdbcjava.ratings.dao.AssignmentDAO;
import avitepa.jdbcjava.ratings.dao.DistributionDAO;
import avitepa.jdbcjava.ratings.domain.Assignment;
import avitepa.jdbcjava.ratings.exception.ValidationException;

/**
 * @author Vijay M
 *
 */
public class RatingsService {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(RatingsService.class);

	// There are couple other ways to construct singleton obj, due to time
	// constraint following below
	private static final RatingsService RATING_SERVICE = new RatingsService();

	private AssignmentDAO assignmentService;
	
	private DistributionDAO distributionDAO;


	private RatingsService() {
		assignmentService = AssignmentDAO.getInstance();
		distributionDAO=DistributionDAO.getInstance();
	}

	/**
	 * This method returns the instance of RatingsService(singletone)
	 * 
	 * @return RatingsService
	 */
	public static RatingsService getIntance() {
		return RATING_SERVICE;
	}

	public void computeRatingByStudent(String studentName) {
		LOGGER.debug("starting computeRatingByStudent...");
		if (Objects.isNull(studentName)) {
			throw new ValidationException("Student name is mandatory to compute ratings by student");
		}

		Set<Assignment> assignments = assignmentService.getAssignmentsByStudent(studentName);

		// can also use multi field group by
		Map<String, List<Assignment>> studentAssignmentsMap = assignments.stream()
				.filter(assignment -> studentName.equalsIgnoreCase(assignment.getStudentName()))
				.collect(Collectors.groupingBy(Assignment::getSubject));

		if (Objects.isNull(studentAssignmentsMap) || studentAssignmentsMap.isEmpty()) {
			throw new ValidationException("Student NotFound: "+studentName);
		}
		//Did not use log4j just show formatted ouput
		System.out.println("Student: " + studentName);

		for (String subjectKey : studentAssignmentsMap.keySet()) {
			System.out.println("Subject: " + subjectKey);
			computeRatings(studentAssignmentsMap.get(subjectKey));
		}

	}
	
	private void computeRatings(List<Assignment> studentAssignments) {

		Map<String, List<Assignment>> assignmentByCategory = studentAssignments.stream()
				.collect(Collectors.groupingBy(Assignment::getAssignmentCategoryType));
		float overralScore=0;
		Map<String, Integer> assignmentCategories = distributionDAO.getAssignmentCategories();
		for(String category: assignmentCategories.keySet()) {
			int percentage = assignmentCategories.get(category);
			float categoryScore = 0;
			List<Assignment> categoryAssignment = assignmentByCategory.get(category);
			if(Objects.isNull(categoryAssignment) || categoryAssignment.isEmpty()) {
				System.out.print(category + " Score: NA ");
				continue;
			}
			for (Assignment assignment : categoryAssignment) {
				categoryScore += (percentage / categoryAssignment.size()) * assignment.getPoints();
			}
			float categoryScorePercentaze = categoryScore / 100;
			overralScore+=categoryScorePercentaze;
			System.out.print(category + " Score " + categoryScorePercentaze+" ");

		}
		System.out.println(" Overral Rating " + overralScore);

	}

	public void computeRatingBySubject(String subjectName) {

		if (Objects.isNull(subjectName)) {
			throw new ValidationException("subjectName name is mandatory to compute ratings by subjectName");
		}

		Set<Assignment> assignments = assignmentService.getAssignmentsBySubject(subjectName);

		// can also use multi field group by
		Map<String, List<Assignment>> studentAssignmentsMap = assignments.stream()
				.filter(assignment -> subjectName.equalsIgnoreCase(assignment.getSubject()))
				.collect(Collectors.groupingBy(Assignment::getStudentName));

		if (Objects.isNull(studentAssignmentsMap) || studentAssignmentsMap.isEmpty()) {
			throw new ValidationException("Subject NotFound: "+subjectName);
		}
		
		System.out.println("Subject: " + subjectName);

		for (String StudentKey : studentAssignmentsMap.keySet()) {
			System.out.println("Student: " + StudentKey);
			computeRatings(studentAssignmentsMap.get(StudentKey));
		}
		
	}
	

}

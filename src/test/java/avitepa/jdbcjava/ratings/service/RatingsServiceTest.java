package avitepa.jdbcjava.ratings.service;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import avitepa.jdbcjava.ratings.exception.ValidationException;

/**
 * 
 * @author Vijay M
 *
 */
@RunWith(JUnit4ClassRunner.class)
public class RatingsServiceTest {
	

	@Test(expected = ValidationException.class)
	public void testComputeRatingByStudent_noStudentName() {
		RatingsService ratingsService=RatingsService.getIntance();
		ratingsService.computeRatingByStudent(null);
	}
	
	@Test(expected = ValidationException.class)
	public void testComputeRatingByStudent_StudentNotFound() {
		RatingsService ratingsService=RatingsService.getIntance();
		ratingsService.computeRatingByStudent("Vijay");
	}
	
	@Test
	public void testComputeRatingByStudent() {
		RatingsService ratingsService=RatingsService.getIntance();
		ratingsService.computeRatingByStudent("Ananth");
	}
	
	@Test(expected = ValidationException.class)
	public void testComputeRatingBySubject_noSubjectName() {
		RatingsService ratingsService=RatingsService.getIntance();
		ratingsService.computeRatingBySubject(null);
	}
	
	@Test(expected = ValidationException.class)
	public void testComputeRatingBySubject_SubjectNotFound() {
		RatingsService ratingsService=RatingsService.getIntance();
		ratingsService.computeRatingBySubject("Computer");
	}
	
	@Test
	public void testComputeRatingBySubject() {
		RatingsService ratingsService=RatingsService.getIntance();
		ratingsService.computeRatingBySubject("Electro Fields");
	}
   
   
}

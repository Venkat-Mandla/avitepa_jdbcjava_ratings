package avitepa.jdbcjava.ratings;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import avitepa.jdbcjava.ratings.dao.AssignmentDAO;
import avitepa.jdbcjava.ratings.dao.DataLoader;
import avitepa.jdbcjava.ratings.domain.Assignment;
import avitepa.jdbcjava.ratings.service.RatingsService;

/**
 * 
 * @author Vijay M
 *
 */
public class App {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		
		DataLoader.loadData();

		RatingsService ratingsService = RatingsService.getIntance();

		LOGGER.info("***********************Rating by Student******************************");
		ratingsService.computeRatingByStudent("Ananth");


		LOGGER.info("***********************Rating by Subject******************************");
		ratingsService.computeRatingBySubject("Electro Fields");
		
		AssignmentDAO assignmentService=AssignmentDAO.getInstance();
		
		
		Assignment ananthElectroTest1Category = new Assignment();
		ananthElectroTest1Category.setAssignmentCategory("test_3");
		ananthElectroTest1Category.setStudentName("Ananth");
		ananthElectroTest1Category.setSubject("Electro Fields");
		ananthElectroTest1Category.setSerialNumber(1);
		ananthElectroTest1Category.setDataOfSubmission(new Date());
		ananthElectroTest1Category.setPoints(100);
		//TODO change student or category before uncommenting save method call.
		//assignmentService.save(ananthElectroTest1Category);
		
		LOGGER.info("***********************Rating by Student******************************");
		//ratingsService.computeRatingByStudent("Ananth");

	}
}

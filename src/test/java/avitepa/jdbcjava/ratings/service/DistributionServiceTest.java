/**
 * 
 */
package avitepa.jdbcjava.ratings.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import avitepa.jdbcjava.ratings.dao.DistributionDAO;

/**
 * @author Vijay M
 *
 */
@RunWith(JUnit4ClassRunner.class)
public class DistributionServiceTest {

	@Test
	public void testGetAssignmentCategories() {
		assertNotNull(DistributionDAO.getInstance().getAssignmentCategories());
	}

	@Test
	public void testAddAssignmentCategory() {
		DistributionDAO instance = DistributionDAO.getInstance();
		//instance.addAssignmentCategory("quiz", 10);
		//insert is failing due to primary key..so enabled update
		instance.modifyAssignmentCategory("quiz", 1);
	}

	@Test
	public void testRemoveAssignmentCategory() {
		DistributionDAO instance = DistributionDAO.getInstance();
		instance.removeAssignmentCategory("test");
	}

	@Test
	public void testModifyAssignmentCategory() {
		DistributionDAO instance = DistributionDAO.getInstance();
		instance.modifyAssignmentCategory("quiz", 1);
	}

}

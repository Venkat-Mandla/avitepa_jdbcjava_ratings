/**
 * 
 */
package avitepa.jdbcjava.ratings.domain;

import java.util.Date;
import java.util.Objects;

/**
 * @author Vijay M
 *
 */
public class Assignment {

	private int serialNumber;
	private String studentName;
	private String subject;
	private String assignmentCategory;
	private Date dataOfSubmission;
	private int points;

	public int getSerialNumber() {
		return serialNumber;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getSubject() {
		return subject;
	}

	public String getAssignmentCategory() {
		return assignmentCategory;
	}

	public Date getDataOfSubmission() {
		return dataOfSubmission;
	}

	public int getPoints() {
		return points;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setAssignmentCategory(String assignmentCategory) {
		this.assignmentCategory = assignmentCategory;
	}

	public void setDataOfSubmission(Date dataOfSubmission) {
		this.dataOfSubmission = dataOfSubmission;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getAssignmentCategoryType() {
		return Objects.nonNull(assignmentCategory)? assignmentCategory.substring(0, assignmentCategory.indexOf("_")):assignmentCategory;
	}


	@Override
	public int hashCode() {
		return Objects.hash(assignmentCategory, studentName, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assignment other = (Assignment) obj;
		return Objects.equals(assignmentCategory, other.assignmentCategory)
				&& Objects.equals(studentName, other.studentName) && Objects.equals(subject, other.subject);
	}

	@Override
	public String toString() {
		return "Assignment [serialNumber=" + serialNumber + ", studentName=" + studentName + ", subject=" + subject
				+ ", assignmentCategory=" + assignmentCategory + ", points=" + points + "]";
	}

}

package uo.ri.cws.extended.course;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.cws.domain.Course;

public class ConstructorValidationTests {

	private String code = "code";
	private int duration = 10;
	private String name = "name";
	private Date startDate = Dates.addDays( Dates.today(), 15);
	private Date endDate = Dates.addDays( startDate, 5);
	private String description = "description";

	/**
	 * code cannot be empty (nor null)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeNotEmpty() {
		new Course("", name, description, startDate, endDate, duration);
	}

	/**
	 * name cannot be empty (nor null)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNameNotEmpty() {
		new Course(code, "", description, startDate, endDate, duration);
	}

	/**
	 * description cannot be empty (nor null)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDescriptionNotEmpty() {
		new Course(code, name, "", startDate, endDate, duration);
	}

	/**
	 * startDate cannot be null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStartDateNotNull() {
		new Course(code, name, description, null, endDate, duration);
	}

	/**
	 * endDate cannot be null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEndDateNotNull() {
		new Course(code, name, description, startDate, null, duration);
	}

	/**
	 * endDate must be after startDate
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testEndDateAfterStartDate() {
		startDate = Dates.today();
		endDate = Dates.subDays( startDate, 1);
		
		new Course(code, name, description, startDate, endDate, duration);
	}

	/**
	 * startDate and endDate are copied
	 */
	@Test
	public void testDatesCopied() {
		Course c = new Course(code, name, description, 
				startDate, endDate, 
				duration);
 
		c.getStartDate().setTime( 0 );
		c.getEndDate().setTime( 0 );
		
		assertTrue( c.getStartDate().getTime() != 0 );
		assertTrue( c.getEndDate().getTime() != 0 );
	}

	/**
	 * duration cannot be zero 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDurationNonZero() {
		new Course(code, name, description, startDate, endDate, 0);
	}

	/**
	 * duration cannot be negative
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDurationnonNegative() {
		new Course(code, name, description, startDate, endDate, -1);
	}

}

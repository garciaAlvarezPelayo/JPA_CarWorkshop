package uo.ri.cws.extended.enrollment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import alb.util.reflection.ReflectionUtil;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class AttendancePassedTests {

	private Mechanic mechanic;
	private VehicleType car;
	private VehicleType truck;
	private Course course;

	@Before
	public void setUp() throws Exception {
		mechanic = new Mechanic("123");
		car = new VehicleType("car");
		truck = new VehicleType("truck");
		
		course = new Course("course", "1o1", "description",
				Dates.fromDdMmYyyy(11, 11, 2019),
				Dates.fromDdMmYyyy(25, 11, 2019),
				100 /* hours */
			);
		
		Map<VehicleType, Integer> dedications = new HashMap<>();
		dedications.put( car, 	25 /* % */);
		dedications.put( truck, 75 /* % */);
		course.addDedications(dedications);
	}

	/**
	 * The constructor sets passed to true and attendance if >= 85 %
	 */
	@Test
	public void testAttendedAndPassed() {
		boolean PASSED = true;
		int ENOUGH_TO_PASS = 85;
		
		Enrollment e = new Enrollment(mechanic, course, ENOUGH_TO_PASS, PASSED );
		
		assertTrue( e.isPassed() );
		assertEquals( ENOUGH_TO_PASS, e.getAttendance() );
	}

	/**
	 * The constructor sets passed to true and attendance if >= 85 %
	 */
	@Test
	public void testAttendedNotPassed() {
		boolean NOT_PASSED = false;
		int ENOUGH_TO_PASS = 85;
		
		Enrollment e = new Enrollment(mechanic, course, 
				ENOUGH_TO_PASS, 
				NOT_PASSED );
		
		assertTrue( e.isPassed() == false );
		assertEquals( ENOUGH_TO_PASS, e.getAttendance() );
	}

	/**
	 * The constructor throws IllegalArgumentException if attendance is < 85 %
	 * and the course is mark as passed
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorDoesNoAllowPassedWithLessThan85Attendance() {
		boolean PASSED = true;
		int NOT_ENOUGH_TO_PASS = 84;
		
		new Enrollment(mechanic, course, NOT_ENOUGH_TO_PASS, PASSED);
	}

	/**
	 * Enrollment has no use case for update. That is, it is an unmodifiable 
	 * entity. Therefore, there are no setters on this class.
	 */
	@Test
	public void testNoSetters() {
		try {
			ReflectionUtil.getMethodOfClass(
					Enrollment.class, 
					"setPassed", 
					boolean.class
				);
			fail("There should't be this setter on this class");
		} catch (IllegalStateException e) { 
			// expected result
		}
		
		try {
			ReflectionUtil.getMethodOfClass(
					Enrollment.class, 
					"setAttendance", 
					int.class
				);
			fail("There should't be this setter on this class");
		} catch (IllegalStateException e) { 
			// expected result
		}
	}

}

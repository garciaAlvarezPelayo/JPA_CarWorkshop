package uo.ri.cws.extended.enrollment;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class GetAttendedHoursForTests {

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
	 * Returns right attended hours for car with 100 % attendance
	 */
	@Test
	public void testRightForCar() {
		Enrollment e = new Enrollment(mechanic, course, 100, true);

		assertEquals( 25, e.getAttendedHoursFor( car ));
	}


	/**
	 * Returns right attended hours for truck with 100 % attendance 
	 */
	@Test
	public void testRightForTruck() {
		Enrollment e = new Enrollment(mechanic, course, 100, true);

		assertEquals( 75, e.getAttendedHoursFor( truck ));
	}


	/**
	 * Returns 0 attended hours for a non involved vehicle type with 100 % attendance 
	 */
	@Test
	public void testZeroForOtherType() {
		Enrollment e = new Enrollment(mechanic, course, 100, true);

		VehicleType other = new VehicleType("other");
		assertEquals( 0, e.getAttendedHoursFor( other ));
	}

	
	/**
	 * Returns right attended hours for car with 85 % attendance
	 */
	@Test
	public void testRightForCarWith85Attendance() {
		Enrollment e = new Enrollment(mechanic, course, 85, true);

		int expected = (int)(25 * 85 / 100);
		assertEquals( expected, e.getAttendedHoursFor( car ));
	}

	/**
	 * Returns right attended hours for truck with 85 % attendance 
	 */
	@Test
	public void testRightForTruckWith85Attendance() {
		Enrollment e = new Enrollment(mechanic, course, 85, true);

		int expected = (int)(75 * 85 / 100);
		assertEquals( expected, e.getAttendedHoursFor( truck ));
	}

}

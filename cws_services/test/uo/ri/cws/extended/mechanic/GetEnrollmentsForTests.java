package uo.ri.cws.extended.mechanic;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Enrollment;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class GetEnrollmentsForTests {

	private Mechanic mechanic;
	private VehicleType car;
	private VehicleType truck;
	private Course onlyTruckCourse;
	private Course carAndTruckCourse;
	private Enrollment c1Enrollment;
	private Enrollment c2Enrollment;

	@Before
	public void setUp() throws Exception {
		mechanic = new Mechanic("123");
		car = new VehicleType("car");
		truck = new VehicleType("truck");
		
		onlyTruckCourse = new Course("C1", "truck", "truck description",
				Dates.fromDdMmYyyy(11, 11, 2019),
				Dates.fromDdMmYyyy(25, 11, 2019),
				100 /* hours */
			);
		Map<VehicleType, Integer> dedications = new HashMap<>();
		dedications.put( truck, 100 /* % */);
		onlyTruckCourse.addDedications(dedications);


		carAndTruckCourse = new Course("C2", "car&truck", "car&truck description",
				Dates.fromDdMmYyyy(11, 12, 2019),
				Dates.fromDdMmYyyy(25, 12, 2019),
				100 /* hours */
			);
		dedications.clear();
		dedications.put( car, 	25 /* % */);
		dedications.put( truck, 75 /* % */);
		carAndTruckCourse.addDedications(dedications);
		
		c1Enrollment = new Enrollment(mechanic, onlyTruckCourse, 100, true);
		c2Enrollment = new Enrollment(mechanic, carAndTruckCourse, 100, true);	
	}

	/**
	 * Returns the car course enrollment for "car"  
	 */
	@Test
	public void testReturnsOneForCar() {
		Set<Enrollment> es = mechanic.getEnrollmentsFor( car );
		
		assertTrue( es.size() == 1 );
		assertTrue( es.contains( c2Enrollment ) );
	}

	/**
	 * Returns both enrollments for truck
	 */
	@Test
	public void testReturnsTwoForTruck() {
		Set<Enrollment> es = mechanic.getEnrollmentsFor( truck );
		
		assertTrue( es.size() == 2 );
		assertTrue( es.contains( c1Enrollment ) );
		assertTrue( es.contains( c2Enrollment ) );
	}

	/**
	 * Returns empty set for other type
	 */
	@Test
	public void testReturnEmptyForOther() {
		VehicleType other = new VehicleType("other");
		
		Set<Enrollment> es = mechanic.getEnrollmentsFor( other );
		
		assertTrue( es.isEmpty() );
	}

	/**
	 * Returns empty set for null type
	 */
	@Test
	public void testReturnEmptyForNull() {
		Set<Enrollment> es = mechanic.getEnrollmentsFor( null );
		
		assertTrue( es.isEmpty() );
	}

}

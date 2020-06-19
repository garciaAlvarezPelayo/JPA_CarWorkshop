package uo.ri.cws.extended.course;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Dedication;
import uo.ri.cws.domain.VehicleType;

public class ClearDedicationsTests {

	private VehicleType car;
	private VehicleType truck;
	private Course course;

	@Before
	public void setUp() throws Exception {
		car = new VehicleType("car");
		truck = new VehicleType("truck");
		course = new Course("C1");
		
		Map<VehicleType, Integer> percentages = Map.of(car, 25, truck, 75);
		
		course.addDedications(percentages);
	}

	/**
	 * clearDedications() unlinks dedications
	 */
	@Test
	public void testClearDedicationsUnlinks() {
		Set<Dedication> dedications = course.getDedications(); // must be a copy
		
		course.clearDedications();
		
		assertTrue( course.getDedications().isEmpty() );
		assertTrue( car.getDedications().isEmpty() );
		assertTrue( truck.getDedications().isEmpty() );
		for(Dedication d: dedications) {
			assertTrue( d.getVehicleType() == null );
			assertTrue( d.getCourse() == null );
		}
	}

	/**
	 * clearDedications must be call before addDedications if there already are 
	 * previous dedications
	 */
	@Test
	public void testClearDedicationsBeforeAddDedications() {
		Map<VehicleType, Integer> percentages = Map.of(car, 100);
		
		course.clearDedications();
		course.addDedications(percentages);

		assertTrue( course.getDedications().size() == 1 );
	}

}

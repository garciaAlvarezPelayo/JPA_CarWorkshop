package uo.ri.cws.extended.course;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Course;
import uo.ri.cws.domain.Dedication;
import uo.ri.cws.domain.VehicleType;

public class AddDedicationsTests {

	private VehicleType car;
	private VehicleType truck;
	private Course course;

	@Before
	public void setUp() throws Exception {
		car = new VehicleType("car");
		truck = new VehicleType("truck");
		course = new Course("C1");
	}

	/**
	 * When percentages sums 100% dedications are created and linked 
	 */
	@Test
	public void testDedicationsAmount100() {
		Map<VehicleType, Integer> percentages = Map.of(
				car,   25, 
				truck, 75
			);
		
		course.addDedications(percentages);
		
		assertTrue( course.getDedications().size() == 2 );
		Set<Dedication> dedications = course.getDedications();
		for(Dedication d: dedications) {
			assertTrue( d.getCourse().equals( course ) );
			assertTrue( course.getDedications().contains( d ));
		}
	}
	
	/**
	 * If percentages are less than 100% throws exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDedicationsLessThan100() {
		Map<VehicleType, Integer> percentages = Map.of(
				car,   25,
				truck, 25
			);
		
		course.addDedications(percentages);
	}

	/**
	 * If percentages are greater than 100% throws exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDedicationsGreaterThan100() {
		Map<VehicleType, Integer> percentages = Map.of(
				car,   25,
				truck, 85
			);
		
		course.addDedications(percentages);
	}

	/**
	 * Cannot add dedications if course already has dedications
	 */
	@Test
	public void testAddMoreDedicationsThrowsException() {
		Map<VehicleType, Integer> percentages = Map.of(car, 100);
		course.addDedications(percentages);
		
		percentages = Map.of(car, 25, truck, 85);

		try {
			course.addDedications(percentages);
			fail("An IllegalStateException must be thrown");
			
		} catch (IllegalStateException expected) {
			// expected exception, all is well
			
		} catch (Exception notExpected) {
			fail("An IllegalStateException must be thrown");
		}
	}
	
}

package uo.ri.cws.extended.mechanic;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class IsCertifiedForTests {

	private Mechanic mechanic;
	private VehicleType car;
	private VehicleType truck;

	@Before
	public void setUp() throws Exception {
		mechanic = new Mechanic("123");
		car = new VehicleType("car");
		truck = new VehicleType("truck");
		
		new Certificate(mechanic, car);		// auto link
	}

	/**
	 * It is certified for car, but not for truck not null
	 */
	@Test
	public void testCertifiedForCarNotForTruck() {
		assertTrue( mechanic.isCertifiedFor( car ) );
		assertTrue( ! mechanic.isCertifiedFor( truck ) );
		
		assertTrue( ! mechanic.isCertifiedFor( null ) );
	}

}

package uo.ri.cws.extended.certificate;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.VehicleType;

public class ConstructorTests {

	private VehicleType vehicleType;
	private Mechanic mechanic;

	@Before
	public void setUp() throws Exception {
		mechanic = new Mechanic("123");
		vehicleType = new VehicleType("car");
	}

	
	/**
	 * Constructor takes the date of the system 
	 */
	@Test
	public void testBasicConstructor() {
		long before = new Date().getTime();
		Certificate c = new Certificate(mechanic, vehicleType);
		long after = new Date().getTime();
		
		long timeStamp = c.getDate().getTime();
		
		assertTrue( before <= timeStamp && timeStamp <= after );
	}
	
	/**
	 * Constructor links properly
	 */
	@Test
	public void testConstructorLinks() {
		Certificate c = new Certificate(mechanic, vehicleType);
			
		assertTrue( c.getMechanic().equals( mechanic ) );
		assertTrue( mechanic.getCertificates().contains( c ) );
	}
	

	/**
	 * Date is copied on return
	 */
	@Test
	public void testDateCopiedOnReturn() {
		Certificate c = new Certificate(mechanic, vehicleType);
		
		c.getDate().setTime( 0L );
		
		assertTrue( c.getDate().getTime() != 0  );
	}
}

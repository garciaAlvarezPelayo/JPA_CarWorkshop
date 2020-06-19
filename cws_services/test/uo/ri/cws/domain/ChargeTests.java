package uo.ri.cws.domain;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Voucher;

public class ChargeTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * A charge to a card increases the accumulated
	 */
	@Test
	public void testCargoTarjeta() {
		Date tomorrow = Dates.tomorrow();
		CreditCard t = new CreditCard("123", "visa", tomorrow);
		Invoice f = new Invoice( 123L );

		new Charge(f, t, 100.0);

		assertTrue(t.getAccumulated() == 100.0);
	}

	/**
	 * A charge to cash increases the accumulated
	 */
	@Test
	public void testCargoMetalico() {
		Cash m = new Cash(new Client("123", "n", "a"));
		Invoice f = new Invoice( 123L );

		new Charge(f, m, 100.0);

		assertTrue(m.getAccumulated() == 100.0);
	}

	/**
	 * A charge to a voucher increases the accumulated and decreases the
	 * available
	 */
	@Test
	public void testCargoBono() {
		Voucher b = new Voucher("123", 150.0, "For testing");
		Invoice f = new Invoice(123L);

		new Charge(f, b, 100.0);

		assertTrue(b.getAccumulated() == 100.0);
		assertTrue(b.getDisponible() == 50.0);
	}

	/**
	 * A credit card cannot be charged if its expiration date is over
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void tesChargeExpiredCard() {
		Date expired = Dates.yesterday();
		CreditCard t = new CreditCard("123", "TTT", expired);
		Invoice f = new Invoice(123L);

		new Charge(f, t, 100.0); // Card validity date expired exception
	}

	/**
	 * A voucher cannot be charged if it has no available money
	 * @throws IllegalStateException
	 */
	@Test(expected = IllegalStateException.class)
	public void testEmptyVoucherCannotBeCharged() {
		Voucher b = new Voucher("123", 150.0, "For testing");
		Invoice f = new Invoice(123L);

		new Charge(f, b, 151.0);  // Not enough money exception
	}

}

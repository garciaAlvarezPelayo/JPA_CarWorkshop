package uo.ri.cws.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Intervention;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Substitution;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;


public class InvoiceTests {

	private Mechanic mechanic;
	private WorkOrder workOrder;
	private Intervention intervention;
	private SparePart sparePart;
	private Vehicle vehicle;
	private VehicleType vehicleType;
	private Client client;

	@Before
	public void setUp() {
		client = new Client("dni-cliente", "nombre", "apellidos");
		vehicle = new Vehicle("1234 GJI", "ibiza", "seat");
		Associations.Own.link(client, vehicle);

		vehicleType = new VehicleType("coche", 50.0);
		Associations.Classify.link(vehicleType, vehicle);

		workOrder = new WorkOrder(vehicle, "falla la junta la trocla");
		mechanic = new Mechanic("dni-mecanico", "nombre", "apellidos");
		workOrder.assignTo(mechanic);

		intervention = new Intervention(mechanic, workOrder, 60);

		sparePart = new SparePart("R1001", "junta la trocla", 100.0);
		new Substitution(sparePart, intervention, 2);

		workOrder.markAsFinished();
	}

	/**
	 * Right computation of the invoice amount ( 260 € + IVA 21%)
	 */
	@Test
	public void testInvoiceAmount() {
		List<WorkOrder> workOrders = new ArrayList<>();
		workOrders.add( workOrder );
		Invoice invoice = new Invoice( 0L, workOrders );

		assertTrue( invoice.getAmount() ==  302.5 );
	}

	/**
	 * Constructor with two work orders added through the constructor
	 */
	@Test
	public void testAmountForTwoWorkOrders() {
		List<WorkOrder> workOrders = new ArrayList<>();
		workOrders.add( workOrder );
		workOrders.add( createAnotherWorkOrder() );
		Invoice invoice = new Invoice( 0L, workOrders );

		// amount = (137.5 new work order + 250.0 first one) * 21% iva
		assertTrue( invoice.getAmount() ==  468.88 ); // 2 cents rounded
	}

	/**
	 * Computation of the new amount after adding a new work order to the invoice
	 * Added by association
	 */
	@Test
	public void testNewAmountAfterAddingWorkOrder() {
		Invoice invoice = new Invoice( 0L ); // 0L as dummy invoice number
		invoice.addWorkOrder(workOrder);

		assertTrue( invoice.getAmount() ==  302.5 );
	}

	/**
	 * Computation for two work orders added by association
	 */
	@Test
	public void testInvoiceAmountAddingTwoWorkOrders() {
		Invoice invoice = new Invoice( 0L );
		invoice.addWorkOrder( workOrder );
		invoice.addWorkOrder( createAnotherWorkOrder() );

		assertTrue( invoice.getAmount() ==  468.88 ); // 2 cents rounding
	}

	/**
	 * A new invoice with work orders is in NOT_YET_PAID status
	 */
	@Test
	public void testNewInvoiceIsNotYetPaidStatus() {
		List<WorkOrder> workOrders = new ArrayList<>();
		workOrders.add( workOrder );
		Invoice invoice = new Invoice( 0L, workOrders );

		assertTrue( invoice.getStatus() ==  InvoiceStatus.NOT_YET_PAID );
	}

	/**
	 * If the date of the invoice is before 1/7/2012 the VAT (IVA) is 18% and
	 * it amounts 250€ + VAT 18%
	 */
	@Test
	public void testAmluntForInvoicesPriorJuly2012() {
		Date JUNE_6_2012 = Dates.fromString("15/6/2012");

		List<WorkOrder> workOrders = new ArrayList<>();
		workOrders.add( workOrder );
		Invoice invoice = new Invoice( 0L, JUNE_6_2012, workOrders ); // iva 18%

		assertTrue( invoice.getAmount() ==  295.0 );
	}

	/**
	 * A work order, when added to an invoice, changes its status to INVOICED
	 * Added through the constructor
	 */
	@Test
	public void testInvoicedWorkOrthersStatusInvoiced() {
		List<WorkOrder> workOrders = Arrays.asList( workOrder );
		new Invoice( 0L, workOrders );

		assertTrue( workOrder.getStatus() == WorkOrderStatus.INVOICED );
	}

	/**
	 * A work order, when added to an invoice, changes its status to INVOICED
	 * Added by association
	 */
	@Test
	public void testAveriasFacturadasAddAveria() {
		new Invoice( 0L ).addWorkOrder( workOrder );

		assertTrue( workOrder.getStatus() == WorkOrderStatus.INVOICED );
	}

	/**
	 * All the work orders changes its status to INVOICED when added to
	 * an invoice
	 */
	@Test
	public void testDosAveriasFacturadasAddAveria() {
		WorkOrder otherWorkOrther = createAnotherWorkOrder();

		Invoice f = new Invoice( 0L );
		f.addWorkOrder( workOrder );
		f.addWorkOrder( otherWorkOrther );

		assertTrue( workOrder.getStatus() == WorkOrderStatus.INVOICED );
		assertTrue( otherWorkOrther.getStatus() == WorkOrderStatus.INVOICED );
	}

	/**
	 * The returned date is a copy of the internal one
	 */
	@Test
	public void testGetFechaReturnsCopy() {
		Invoice f = new Invoice( 0L );
		Date one = f.getDate();
		Date another = f.getDate();

		assertTrue( one != another );
		assertTrue( one.equals( another ));
	}

	/**
	 * The date passed through the constructor is copied internally
	 */
	@Test
	public void testContructorCopiesDate() {
		Date date = new Date();
		Date copy = new Date( date.getTime() );

		Invoice f = new Invoice( 0L, date );
		date.setTime( 0L ); // 1/1/1970 00:00
		Date gotten = f.getDate();

		assertTrue( gotten != date );
		assertTrue( ! gotten.equals( date ) );
		assertTrue( gotten.equals( copy ) );
	}

	/**
	 * The date passed through the setter is copied internally
	 */
	@Test
	public void testSetterCopiesDate() {
		Date now = new Date();
		Invoice f = new Invoice( 0L );

		f.setDate( now );
		now.setTime( 0L );
		Date date = f.getDate();

		assertTrue( ! now.equals( date ) );
	}

	/**
	 * Creates a new invoiced witha delay of 100 milliseconds to avoid a
	 * collision in the dates field (same millisecond)
	 *
	 * It could be problematic if the identity of the work order depends on
	 * the date
	 * @return the newly created work order
	 */
	private WorkOrder createAnotherWorkOrder() {
		sleep( 100 );
		WorkOrder workOrder = new WorkOrder(vehicle, "falla la junta la trocla otra vez");
		workOrder.assignTo( mechanic );

		Intervention i = new Intervention(mechanic, workOrder, 45);
		new Substitution(sparePart, i, 1);

		workOrder.markAsFinished();

		// importe = 100 repuesto + 37.5 mano de obra
		return workOrder;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ignored) {
			// dont't care if this occurs
		}
	}

}

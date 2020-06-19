package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
									columnNames = { "VEHICLE_ID", "MECHANIC_ID",
											"INVOICE_ID" }) },
		name = "tworkorders")
public class WorkOrder extends BaseEntity {
	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String description;
	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	@ManyToOne
	private Vehicle vehicle;
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Invoice invoice;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {
	}

	public WorkOrder(Vehicle vehicle, Date date) {
		Argument.isNotNull(date);
		Argument.isNotNull(vehicle);
		this.date = new Date(date.getTime());
		Associations.Order.link(vehicle, this);
	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, new Date());
		Argument.isNotEmpty(description);
		this.description = description;
	}

	private void computeAmount() {
		this.amount = 0;
		for (Intervention each : interventions) {
			this.amount += each.getAmount();
		}
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not FINISHED, or - The work order is
	 *             not linked with the invoice
	 */
	public void markAsInvoiced() throws IllegalStateException {
		if (this.status == WorkOrderStatus.FINISHED) {
			try {
				Argument.isNotNull(invoice);
			} catch (IllegalArgumentException iae) {
				throw new IllegalStateException(
						"The work order has not an invoice");
			}
			this.status = WorkOrderStatus.INVOICED;
		} else {
			throw new IllegalStateException("The work order is not finished");
		}
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in ASSIGNED state, or - The work
	 *             order is not linked with a mechanic
	 */
	public void markAsFinished() throws IllegalStateException {
		if (this.status == WorkOrderStatus.ASSIGNED) {
			try {
				Argument.isNotNull(mechanic);
			} catch (IllegalArgumentException iae) {
				throw new IllegalStateException(
						"The work order has not a mechanic assigned");
			}
			this.status = WorkOrderStatus.FINISHED;
			computeAmount();
		} else {
			throw new IllegalStateException("The work order is not assigned");
		}
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not INVOICED, or - The work order is
	 *             still linked with the invoice
	 */
	public void markBackToFinished() throws IllegalStateException {
		if (this.status == WorkOrderStatus.INVOICED) {
			try {
				Argument.isNull(invoice);
			} catch (IllegalArgumentException iae) {
				throw new IllegalStateException(
						"The work order has an invoice");
			}
			this.status = WorkOrderStatus.FINISHED;
		} else {
			throw new IllegalStateException("The work order is not invoiced");
		}
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in OPEN status, or - The work
	 *             order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) throws IllegalStateException {
		if (this.status == WorkOrderStatus.OPEN) {
			try {
				Argument.isNull(this.mechanic);
			} catch (IllegalArgumentException iae) {
				throw new IllegalStateException(
						"The work order has a mechanic");
			}
			Associations.Assign.link(mechanic, this);
			this.status = WorkOrderStatus.ASSIGNED;
		} else {
			throw new IllegalStateException("The work order has to be open");
		}
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in ASSIGNED status
	 */
	public void desassign() throws IllegalStateException {
		if (this.status == WorkOrderStatus.ASSIGNED) {
			Associations.Assign.unlink(mechanic, this);
			this.status = WorkOrderStatus.OPEN;
		} else {
			throw new IllegalStateException("The work order is not assigned");
		}
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be
	 * moved back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if - The work order is not in FINISHED status
	 */
	public void reopen() throws IllegalStateException {
		if (this.status == WorkOrderStatus.FINISHED) {
			Associations.Assign.unlink(mechanic, this);
			this.status = WorkOrderStatus.OPEN;
		} else {
			throw new IllegalStateException(
					"The work order has to be finished");
		}
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public String toString() {
		return "WorkOrder date=" + date + ", description=" + description
				+ ", amount=" + amount + ", status=" + status + ", vehicle="
				+ vehicle;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

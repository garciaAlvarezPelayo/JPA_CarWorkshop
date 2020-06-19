package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.assertion.Argument;
import alb.util.date.Dates;
import alb.util.math.Round;

@Entity
@Table(name = "tinvoices")
public class Invoice extends BaseEntity {
	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	@Column(unique = true)
	private Long number;

	@Temporal(TemporalType.DATE)
	private Date date;
	private double amount;
	private double vat;
	@Enumerated(EnumType.STRING)
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy = "invoice")
	private Set<Charge> charges = new HashSet<>();

	Invoice() {
	}

	public Invoice(Long number) {
		this(number, new Date());
	}

	public Invoice(Long number, Date date) {
		Argument.isNotNull(number);
		Argument.isNotNull(date);
		this.date = new Date(date.getTime());
		this.number = number;
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, new Date());
		for (WorkOrder each : workOrders) {
			Argument.isNotNull(each);
			addWorkOrder(each);
		}
	}

	public Invoice(Long number, Date date, List<WorkOrder> workOrders) {
		this(number, new Date(date.getTime()));
		for (WorkOrder each : workOrders) {
			Argument.isNotNull(each);
			addWorkOrder(each);
		}
	}

	/**
	 * Computed amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		this.amount = 0;
		for (WorkOrder each : workOrders) {
			this.amount += each.getAmount();
		}

		this.vat = vatPercentage(this.date);
		this.amount += this.amount * vat / 100;
		this.amount = Round.twoCents(this.amount);
	}

	/**
	 * Computes the vat for an invoice
	 * 
	 * @param totalInvoice
	 *            amount of the invoice
	 * @param dateInvoice
	 *            date of the invoice
	 * @return vat percentage
	 */
	private double vatPercentage(Date dateInvoice) {
		return (Dates.fromString("1/7/2012").before(dateInvoice) ? 21.0 : 18.0);
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) throws IllegalStateException {
		if (!this.status.equals(InvoiceStatus.NOT_YET_PAID))
			throw new IllegalStateException("The invoice is paid");
		Associations.ToInvoice.link(workOrder, this);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see State diagrams on the problem statement document
	 * @throws IllegalStateException
	 *             if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder)
			throws IllegalStateException {
		if (!this.status.equals(InvoiceStatus.NOT_YET_PAID))
			throw new IllegalStateException("The invoice is paid");
		Associations.ToInvoice.unlink(workOrder, this);
		workOrder.markBackToFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException
	 *             if - Is already settled - Or the amounts paid with charges to
	 *             payment means do not cover the total of the invoice
	 */
	public void settle() throws IllegalStateException{
		if (this.status.equals(InvoiceStatus.PAID))
			throw new IllegalStateException("The invoice is settled");
		double result = 0;
		for (Charge each : charges) {
			result += each.getPaymentMean().getAccumulated();
		}
		if(result==this.amount)
			throw new IllegalStateException();
		this.status = InvoiceStatus.PAID;
	}

	public Long getNumber() {
		return number;
	}

	public void setDate(Date now) {
		this.date = new Date(now.getTime());
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	@Override
	public String toString() {
		return "Invoice number=" + number + ", date=" + date + ", amount="
				+ amount + ", vat=" + vat + ", status=" + status;
	}

}

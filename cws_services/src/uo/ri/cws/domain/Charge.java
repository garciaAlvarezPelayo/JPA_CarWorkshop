package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(
		uniqueConstraints = { @UniqueConstraint(
												columnNames = { "INVOICE_ID",
														"PAYMENTMEAN_ID" }) },
		name = "tcharges")
public class Charge extends BaseEntity {

	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;
	private double amount;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean) {
		Argument.isNotNull(invoice);
		Argument.isNotNull(paymentMean);
		Associations.Charges.link(invoice, this, paymentMean);
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this(invoice, paymentMean);
		Argument.isTrue(amount >= 0);
		this.amount = amount;
		paymentMean.pay(this.amount);
	}

	/**
	 * Unlinks this charge and restores the value to the payment mean
	 * 
	 * @throws IllegalStateException
	 *             if the invoice is already settled
	 */
	public void rewind() throws IllegalStateException {
		if (this.invoice.getStatus() == Invoice.InvoiceStatus.PAID)
			throw new IllegalStateException("The invoice is already settled.");

		paymentMean.pay(-this.amount);
		Associations.Charges.unlink(this);
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	@Override
	public String toString() {
		return "Charge invoice=" + invoice + ", paymentMean=" + paymentMean
				+ ", amount=" + amount;
	}

}

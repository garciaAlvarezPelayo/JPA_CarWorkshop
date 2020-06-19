package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tvouchers")
public class Voucher extends PaymentMean {
	@Column(unique = true)
	private String code;
	private double available;
	private String description;

	Voucher() {
	}

	public Voucher(String code) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public Voucher(String code, double available, String description) {
		this(code);
		Argument.isTrue(available >= 0);
		Argument.isNotEmpty(description);
		this.available = available;
		this.description = description;
	}

	public Voucher(String code, double available) {
		this(code);
		Argument.isTrue(available >= 0);
		this.available = available;
	}

	/**
	 * Augments the accumulated and decrements the available
	 * 
	 * @throws IllegalStateException
	 *             if not enough available to pay
	 */
	@Override
	public void pay(double amount) throws IllegalStateException{
		if (this.available - amount >= 0) {
			this.available -= amount;
			super.pay(amount);
		} else {
			throw new IllegalStateException(
					"Not enough available to pay");
		}
	}

	public String getCode() {
		return code;
	}

	public double getDisponible() {
		return available;
	}

	public void setDescripcion(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Voucher code=" + code + ", available=" + available
				+ ", description=" + description + ", toString()="
				+ super.toString();
	}

}

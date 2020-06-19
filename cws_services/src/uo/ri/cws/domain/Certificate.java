package uo.ri.cws.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
									columnNames = { "VEHICLETYPE_ID",
											"MECHANIC_ID" }) },
		name = "tcertificates")
public class Certificate extends BaseEntity {

	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private VehicleType vehicleType;
	@Temporal(TemporalType.DATE)
	private Date date;

	public Certificate() {
	}

	public Certificate(Mechanic mechanic, VehicleType vehicleType) {
		Argument.isNotNull(mechanic);
		Argument.isNotNull(vehicleType);
		this.date = new Date();
		Associations.Certificates.link(mechanic, this, vehicleType);
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

}

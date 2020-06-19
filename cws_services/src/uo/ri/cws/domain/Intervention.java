package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(
		uniqueConstraints = { @UniqueConstraint(
												columnNames = { "WORKORDER_ID",
														"MECHANIC_ID" }) },
		name = "tinterventions")
public class Intervention extends BaseEntity {
	@ManyToOne
	private WorkOrder workOrder;
	@ManyToOne
	private Mechanic mechanic;

	@OneToMany(mappedBy = "intervention")
	private Set<Substitution> substitutions = new HashSet<>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private int minutes;

	Intervention() {
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, Date date) {
		Argument.isNotNull(mechanic);
		Argument.isNotNull(workOrder);
		Argument.isNotNull(date);
		this.date = new Date(date.getTime());
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder, new Date());
		Argument.isTrue(minutes >= 0);
		this.minutes = minutes;
	}

	public double getAmount() {
		double accumulatedPriceSpareParts = 0;
		for (Substitution substitution : this.substitutions)
			accumulatedPriceSpareParts += substitution.getImporte();

		double vehicleTypePrice = this.workOrder.getVehicle().getVehicleType()
				.getPricePerHour();
		return vehicleTypePrice * this.minutes / 60
				+ accumulatedPriceSpareParts;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	public int getMinutes() {
		return minutes;
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public String toString() {
		return "Intervention workOrder=" + workOrder + ", mechanic=" + mechanic
				+ ", date=" + date + ", minutes=" + minutes;
	}

}

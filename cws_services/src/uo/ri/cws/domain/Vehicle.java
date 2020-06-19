package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tvehicles")
public class Vehicle extends BaseEntity {
	@Column(unique = true)
	private String plateNumber;
	private String make;
	private String model;

	@ManyToOne
	private Client client;
	@ManyToOne
	private VehicleType vehicleType;
	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workOrders = new HashSet<>();

	Vehicle() {
	}

	public Vehicle(String plateNumber) {
		Argument.isNotEmpty(plateNumber);
		this.plateNumber = plateNumber;
	}

	public Vehicle(String plateNumber, String make, String model) {
		this(plateNumber);
		Argument.isNotEmpty(make);
		Argument.isNotEmpty(model);
		this.make = make;
		this.model = model;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	@Override
	public String toString() {
		return "Vehicle plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model;
	}

	public Client getClient() {
		return client;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}
}

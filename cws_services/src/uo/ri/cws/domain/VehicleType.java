package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tvehicletypes")
public class VehicleType extends BaseEntity {
	@Column(unique = true)
	private String name;
	private double pricePerHour;
	private int minTrainingHours;

	@OneToMany(mappedBy = "vehicleType")
	private Set<Vehicle> vehicles = new HashSet<>();
	@OneToMany(mappedBy = "vehicleType")
	private Set<Certificate> certificates = new HashSet<>();
	@OneToMany(mappedBy = "vehicleType")
	private Set<Dedication> dedications = new HashSet<>();

	VehicleType() {
	}

	public VehicleType(String name) {
		Argument.isNotEmpty(name);
		this.name = name;
	}

	public VehicleType(String name, double pricePerHour) {
		this(name);
		Argument.isTrue(pricePerHour >= 0);
		this.pricePerHour = pricePerHour;
	}

	public VehicleType(String name, double pricePerHour, int minTrainingHours) {
		this(name, pricePerHour);
		Argument.isTrue(minTrainingHours >= 0);
		this.minTrainingHours = minTrainingHours;
	}

	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public int getMinTrainingHours() {
		return minTrainingHours;
	}

	@Override
	public String toString() {
		return "VehicleType name=" + name + ", pricePerHour=" + pricePerHour;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public Set<Certificate> getCertificates() {
		return certificates;
	}

	Set<Certificate> _getCertificates() {
		return certificates;
	}

	public Set<Dedication> getDedications() {
		return dedications;
	}

	Set<Dedication> _getDedications() {
		return dedications;
	}
}

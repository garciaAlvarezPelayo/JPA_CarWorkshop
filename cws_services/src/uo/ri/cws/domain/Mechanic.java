package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tmechanics")
public class Mechanic extends BaseEntity {
	@Column(unique = true)
	private String dni;
	private String surname;
	private String name;

	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Certificate> certificates = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Enrollment> enrollments = new HashSet<>();

	Mechanic() {
	}

	public Mechanic(String dni) {
		Argument.isNotEmpty(dni);
		this.dni = dni;
	}

	public Mechanic(String dni, String name, String surname) {
		this(dni);
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(surname);
		this.name = name;
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurname() {
		return surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Mechanic dni=" + dni + ", surname=" + surname + ", name="
				+ name;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Certificate> getCertificates() {
		return new HashSet<>(certificates);
	}

	Set<Certificate> _getCertificates() {
		return certificates;
	}

	public Set<Enrollment> getEnrollmentsFor(VehicleType car) {
		Set<Enrollment> list = new HashSet<>();
		for (Enrollment enrollment : enrollments) {
			for (Dedication dedication : enrollment.getCourse()
					._getDedications()) {
				if (dedication.getVehicleType().equals(car))
					list.add(enrollment);
			}
		}

		return list;
	}

	Set<Enrollment> _getEnrollments() {
		return enrollments;
	}

	public boolean isCertifiedFor(VehicleType car) {
		for (Certificate certificate : certificates) {
			if (certificate.getVehicleType().equals(car))
				return true;
		}

		return false;
	}

	public Set<Enrollment> getEnrollments() {
		return new HashSet<>(enrollments);
	}
}

package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tclients")
public class Client extends BaseEntity {

	@Column(unique = true)
	private String dni;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;

	@OneToMany(mappedBy = "client")
	private Set<Vehicle> vehicles = new HashSet<>();
	@OneToMany(mappedBy = "client")
	private Set<PaymentMean> paymentMeans = new HashSet<>();

	Client() {
	}

	public Client(String dni) {
		Argument.isNotEmpty(dni);
		this.dni = dni;
	}

	public Client(String dni, String name, String surname) {
		this(dni);
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(surname);
		this.name = name;
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	@Override
	public String toString() {
		return "Client dni=" + dni + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + ", address="
				+ address;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}
}

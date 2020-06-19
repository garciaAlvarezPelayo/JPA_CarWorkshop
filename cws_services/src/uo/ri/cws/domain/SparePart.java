package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tspareparts")
public class SparePart extends BaseEntity {
	@Column(unique = true)
	private String code;
	private String description;
	private double price;

	@OneToMany(mappedBy = "sparePart")
	private Set<Substitution> substitutions = new HashSet<>();

	SparePart() {
	}

	public SparePart(String code) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public SparePart(String code, String description, double price) {
		this(code);
		Argument.isNotEmpty(description);
		Argument.isTrue(price >= 0);
		this.price = price;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Set<Substitution> getSustituciones() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public String toString() {
		return "SparePart code=" + code + ", description=" + description
				+ ", price=" + price;
	}
}

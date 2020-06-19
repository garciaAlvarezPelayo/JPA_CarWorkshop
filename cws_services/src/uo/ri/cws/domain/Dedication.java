package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.Argument;

@Entity
@Table(
		uniqueConstraints = { @UniqueConstraint(
												columnNames = { "COURSE_ID",
														"VEHICLETYPE_ID" }) },
		name = "tdedications")
public class Dedication extends BaseEntity {

	@ManyToOne
	private Course course;
	@ManyToOne
	private VehicleType vehicleType;
	private int percentage;

	Dedication() {
	}

	Dedication(VehicleType vehicleType, Course course, Integer percentage) {
		this(vehicleType, course);
		Argument.isTrue(percentage > 0);
		this.percentage = percentage;
	}

	Dedication(VehicleType vehicleType, Course course) {
		Argument.isNotNull(vehicleType);
		Argument.isNotNull(course);
		Associations.Dedicate.link(vehicleType, this, course);
	}

	public int getPercentage() {
		return percentage;
	}

	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	void _setCourse(Course course) {
		this.course = course;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public Course getCourse() {
		return course;
	}

}

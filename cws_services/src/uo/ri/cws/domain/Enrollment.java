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
														"MECHANIC_ID" }) },
		name = "tenrollments")
public class Enrollment extends BaseEntity {

	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Course course;
	private int attendance;
	private boolean passed;

	public Enrollment() {
	}

	public Enrollment(Mechanic mechanic, Course course, int attendance,
			boolean passed) {
		Argument.isNotNull(mechanic);
		Argument.isNotNull(course);
		Argument.isTrue(attendance > 0);
		if (attendance < 85 && passed)
			throw new IllegalArgumentException();
		Associations.Enroll.link(mechanic, this, course);
		this.attendance = attendance;
		this.passed = passed;
	}

	public int getAttendance() {
		return attendance;
	}

	public boolean isPassed() {
		return passed;
	}

	public int getAttendedHoursFor(VehicleType car) {
		int result = 0;
		for (Dedication dedication : course._getDedications()) {
			if (dedication.getVehicleType().equals(car))
				result = dedication.getPercentage();
		}

		return (int) (Double.valueOf(attendance) / 100.0
				* Double.valueOf(result) / 100.0
				* Double.valueOf(course.getHours()));
	}

	public Course getCourse() {
		return course;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setCourse(Course course) {
		this.course = course;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

}

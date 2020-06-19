package uo.ri.cws.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import alb.util.assertion.Argument;

@Entity
@Table(name = "tcourses")
public class Course extends BaseEntity {

	@Column(unique = true)
	private String code;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	private int hours;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@OneToMany(mappedBy = "course")
	private Set<Dedication> dedications = new HashSet<>();
	@OneToMany(mappedBy = "course")
	private Set<Enrollment> enrollments = new HashSet<>();

	public Course() {
	}

	public Course(String code) {
		Argument.isNotEmpty(code);
		this.code = code;
	}

	public Course(String code, String name, String description, Date startDate,
			Date endDate, int duration) {
		this(code);
		Argument.isNotEmpty(name);
		Argument.isNotEmpty(description);
		Argument.isNotNull(startDate);
		Argument.isNotNull(endDate);
		Argument.isTrue(duration > 0);
		Argument.isTrue(startDate.before(endDate));
		this.name = name;
		this.description = description;
		this.startDate = new Date(startDate.getTime());
		this.endDate = new Date(endDate.getTime());
		this.hours = duration;
	}

	public void addDedications(Map<VehicleType, Integer> percentages) {
		if (!dedications.isEmpty())
			throw new IllegalStateException();

		int total = 0;
		for (Integer each : percentages.values()) {
			total += each;
		}
		if (total > 100 || total < 100)
			throw new IllegalArgumentException();

		for (Entry<VehicleType, Integer> entry : percentages.entrySet()) {
			dedications.add(
					new Dedication(entry.getKey(), this, entry.getValue()));
		}
	}

	public void clearDedications() {
		while (!dedications.isEmpty()) {
			Associations.Dedicate.unlink((Dedication) dedications.toArray()[0]);
		}
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Date getEndDate() {
		return new Date(endDate.getTime());
	}

	public int getHours() {
		return hours;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return new Date(startDate.getTime());
	}

	public Set<Dedication> getDedications() {
		return new HashSet<>(dedications);
	}

	Set<Dedication> _getDedications() {
		return dedications;
	}

	Set<Enrollment> _getEnrollments() {
		return enrollments;
	}

}

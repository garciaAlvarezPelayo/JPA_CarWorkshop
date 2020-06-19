package uo.ri.cws.application.service.training;

import uo.ri.cws.application.service.mechanic.MechanicDto;

public class EnrollmentDto {

	public String id;
	public long version;

	public String mechanicId;
	public String courseId;
	public int attendance; // percentage 0..100
	public boolean passed;

	// Just for listing purposes
	public MechanicDto mechanic;
	public CourseDto course;

}

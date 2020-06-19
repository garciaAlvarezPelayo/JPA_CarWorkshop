package uo.ri.cws.application.service.training;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CourseDto {

	public String id;
	public long version;
	
	public String code;
	public String name;
	public String description;
	public Date startDate;
	public Date endDate;
	public int hours;
	
	/**
	 * A map of the form:
	 * 	Vehicle type id -> percentage
	 * 	 asd-234-sdc -> 25%
	 * 	 fcd-346-tyc -> 25%
	 * 	 cdy-469-ycf -> 50%
	 */
	public Map<String, Integer> percentages = new HashMap<>();
}

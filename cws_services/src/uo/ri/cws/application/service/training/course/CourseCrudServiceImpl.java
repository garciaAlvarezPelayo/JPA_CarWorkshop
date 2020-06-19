package uo.ri.cws.application.service.training.course;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;

public class CourseCrudServiceImpl implements CourseCrudService {

	@Override
	public CourseDto registerNew(CourseDto dto) throws BusinessException {
		return null;
	}

	@Override
	public void updateCourse(CourseDto dto) throws BusinessException {

	}

	@Override
	public void deleteCourse(String id) throws BusinessException {

	}

	@Override
	public List<CourseDto> findAllCourses() throws BusinessException {
		return null;
	}

	@Override
	public List<VehicleTypeDto> findAllVehicleTypes() throws BusinessException {
		return null;
	}

	@Override
	public Optional<CourseDto> findCourseById(String cId)
			throws BusinessException {
		return null;
	}

}

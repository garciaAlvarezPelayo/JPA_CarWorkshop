package uo.ri.cws.application.service.training.attendance;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.service.training.EnrollmentDto;

public class CourseAttendanceServiceImpl implements CourseAttendanceService {

	@Override
	public EnrollmentDto registerNew(EnrollmentDto dto)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAttendace(String id) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EnrollmentDto> findAttendanceByCourseId(String id)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseDto> findAllActiveCourses() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicDto> findAllActiveMechanics() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}

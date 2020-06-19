package uo.ri.cws.application.service;

import uo.ri.cws.application.ServiceFactory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.invoice.CreateInvoiceService;
import uo.ri.cws.application.service.invoice.SettleInvoiceService;
import uo.ri.cws.application.service.invoice.create.CreateInvoiceServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.sparepart.SparePartCrudService;
import uo.ri.cws.application.service.training.CertificateService;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.cws.application.service.training.CourseReportService;
import uo.ri.cws.application.service.training.attendance.CourseAttendanceServiceImpl;
import uo.ri.cws.application.service.training.certificate.CertificateServiceImpl;
import uo.ri.cws.application.service.training.course.CourseCrudServiceImpl;
import uo.ri.cws.application.service.training.report.CourseReportServiceImpl;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.VehicleCrudServiceImpl;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.AssignWorkOrderService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;
import uo.ri.cws.application.service.workorder.assign.AssignWorkOrderServiceImpl;
import uo.ri.cws.application.service.workorder.crud.WorkOrderCrudServiceImpl;

public class BusinessFactory implements ServiceFactory {

	@Override
	public MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	@Override
	public CreateInvoiceService forCreateInvoiceService() {
		return new CreateInvoiceServiceImpl();
	}

	@Override
	public WorkOrderCrudService forWorkOrderService() {
		return new WorkOrderCrudServiceImpl();
	}

	@Override
	public CloseWorkOrderService forClosingBreakdown() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public VehicleCrudService forVehicleCrudService() {
		return new VehicleCrudServiceImpl();
	}

	@Override
	public CourseCrudService forCourseCrudService() {
		return new CourseCrudServiceImpl();
	}

	@Override
	public CourseAttendanceService forCourseAttendanceService() {
		return new CourseAttendanceServiceImpl();
	}

	@Override
	public CourseReportService forCourseReportService() {
		return new CourseReportServiceImpl();
	}

	@Override
	public CertificateService forCertificateService() {
		return new CertificateServiceImpl();
	}

	@Override
	public VehicleTypeCrudService forVehicleTypeCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public AssignWorkOrderService forAssignWorkOrderService() {
		return new AssignWorkOrderServiceImpl();
	}

	@Override
	public ClientCrudService forClientCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public SparePartCrudService forSparePartCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public SettleInvoiceService forSettleInvoiceService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ClientHistoryService forClientHistoryService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
		throw new RuntimeException("Not yet implemented");
	}

}

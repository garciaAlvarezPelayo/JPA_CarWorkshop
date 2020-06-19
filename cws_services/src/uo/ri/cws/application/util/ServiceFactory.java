package uo.ri.cws.application.util;

import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.invoice.CreateInvoiceService;
import uo.ri.cws.application.service.invoice.SettleInvoiceService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.sparepart.SparePartCrudService;
import uo.ri.cws.application.service.training.CertificateService;
import uo.ri.cws.application.service.training.CourseAttendanceService;
import uo.ri.cws.application.service.training.CourseCrudService;
import uo.ri.cws.application.service.training.CourseReportService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.AssignWorkOrderService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;

public interface ServiceFactory {

	// Manager use cases
	VehicleTypeCrudService forVehicleTypeCrudService();

	MechanicCrudService forMechanicCrudService();

	SparePartCrudService forSparePartCrudService();

	CourseCrudService forCourseCrudService();

	CourseAttendanceService forCourseAttendanceService();

	CourseReportService forCourseReportService();

	CertificateService forCertificateService();

	// Cash use cases
	CreateInvoiceService forCreateInvoiceService();

	SettleInvoiceService forSettleInvoiceService();

	// Foreman use cases
	WorkOrderCrudService forWorkOrderService();

	AssignWorkOrderService forAssignWorkOrderService();

	VehicleCrudService forVehicleCrudService();

	ClientCrudService forClientCrudService();

	ClientHistoryService forClientHistoryService();

	// Mechanic use cases
	CloseWorkOrderService forClosingBreakdown();

	ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService();

}

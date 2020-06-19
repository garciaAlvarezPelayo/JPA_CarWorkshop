package uo.ri.cws.application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.cws.application.service.client.ClientDto;
import uo.ri.cws.application.service.invoice.CardDto;
import uo.ri.cws.application.service.invoice.CashDto;
import uo.ri.cws.application.service.invoice.InvoiceDto;
import uo.ri.cws.application.service.invoice.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.VoucherDto;
import uo.ri.cws.application.service.mechanic.MechanicDto;
import uo.ri.cws.application.service.training.CertificateDto;
import uo.ri.cws.application.service.training.CourseDto;
import uo.ri.cws.application.service.training.EnrollmentDto;
import uo.ri.cws.application.service.vehicle.VehicleDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderDto;
import uo.ri.cws.domain.Certificate;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.domain.*;

public class DtoAssembler {

	public static ClientDto toDto(Client c) {
		ClientDto dto = new ClientDto();

		dto.id = c.getId();
		dto.version = c.getVersion();

		dto.dni = c.getDni();
		dto.name = c.getName();
		dto.surname = c.getSurname();

		return dto;
	}

	public static List<ClientDto> toClientDtoList(List<Client> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for (Client c : clientes) {
			res.add(DtoAssembler.toDto(c));
		}
		return res;
	}

	public static MechanicDto toDto(Mechanic m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.dni = m.getDni();
		dto.name = m.getName();
		dto.surname = m.getSurname();
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mechanic> list) {
		List<MechanicDto> res = new ArrayList<>();
		for (Mechanic m : list) {
			res.add(toDto(m));
		}
		return res;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Voucher> list) {
		List<VoucherDto> res = new ArrayList<>();
		for (Voucher b : list) {
			res.add(toDto(b));
		}
		return res;
	}

	public static VoucherDto toDto(Voucher v) {
		VoucherDto dto = new VoucherDto();
		dto.id = v.getId();
		dto.version = v.getVersion();

		dto.clientId = v.getClient().getId();
		dto.accumulated = v.getAccumulated();
		dto.code = v.getCode();
		dto.description = v.getDescription();
		dto.available = v.getDisponible();
		return dto;
	}

	public static CardDto toDto(CreditCard cc) {
		CardDto dto = new CardDto();
		dto.id = cc.getId();
		dto.version = cc.getVersion();

		dto.clientId = cc.getClient().getId();
		dto.accumulated = cc.getAccumulated();
		dto.cardNumber = cc.getNumber();
		dto.cardExpiration = cc.getValidThru();
		dto.cardType = cc.getType();
		return dto;
	}

	public static CashDto toDto(Cash m) {
		CashDto dto = new CashDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.clientId = m.getClient().getId();
		dto.accumulated = m.getAccumulated();
		return dto;
	}

	public static InvoiceDto toDto(Invoice invoice) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = invoice.getId();
		dto.version = invoice.getVersion();

		dto.number = invoice.getNumber();
		dto.date = invoice.getDate();
		dto.total = invoice.getAmount();
		dto.vat = invoice.getVat();
		dto.status = invoice.getStatus().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(
			List<PaymentMean> list) {
		return list.stream().map(mp -> toDto(mp)).collect(Collectors.toList());
	}

	private static PaymentMeanDto toDto(PaymentMean mp) {
		if (mp instanceof Voucher) {
			return toDto((Voucher) mp);
		} else if (mp instanceof CreditCard) {
			return toDto((CreditCard) mp);
		} else if (mp instanceof Cash) {
			return toDto((Cash) mp);
		} else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static WorkOrderDto toDto(WorkOrder a) {
		WorkOrderDto dto = new WorkOrderDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.vehicleId = a.getVehicle().getId();
		dto.description = a.getDescription();
		dto.date = a.getDate();
		dto.total = a.getAmount();
		dto.status = a.getStatus().toString();

		dto.invoiceId = a.getInvoice() == null ? null : a.getInvoice().getId();

		return dto;
	}

	public static VehicleDto toDto(Vehicle v) {
		VehicleDto dto = new VehicleDto();
		dto.id = v.getId();
		dto.version = v.getVersion();

		dto.plate = v.getPlateNumber();
		dto.clientId = v.getClient().getId();
		dto.make = v.getMake();
		dto.vehicleTypeId = v.getVehicleType().getId();
		dto.model = v.getModel();

		return dto;
	}

	public static List<WorkOrderDto> toWorkOrderDtoList(List<WorkOrder> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static List<CertificateDto> toCertificateDtoList(
			List<Certificate> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static CertificateDto toDto(Certificate c) {
		CertificateDto dto = new CertificateDto();
		dto.id = c.getId();
		dto.version = c.getVersion();

		dto.mechanic = toDto(c.getMechanic());
		dto.vehicleType = toDto(c.getVehicleType());
		dto.obtainedAt = c.getDate();

		return dto;
	}

	public static VehicleTypeDto toDto(VehicleType vt) {
		VehicleTypeDto dto = new VehicleTypeDto();

		dto.id = vt.getId();
		dto.version = vt.getVersion();

		dto.name = vt.getName();
		dto.pricePerHour = vt.getPricePerHour();
		dto.minTrainigHours = vt.getMinTrainingHours();

		return dto;
	}

	public static CourseDto toDto(Course c) {
		CourseDto dto = new CourseDto();

		dto.id = c.getId();
		dto.version = c.getVersion();

		dto.code = c.getCode();
		dto.name = c.getName();
		dto.description = c.getDescription();
		dto.startDate = c.getStartDate();
		dto.endDate = c.getEndDate();

		for (Dedication d : c.getDedications()) {
			dto.percentages.put(d.getVehicleType().getId(), d.getPercentage());
		}

		return dto;
	}

	public static List<CourseDto> toCourseDtoList(List<Course> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static List<VehicleTypeDto> toVehicleTypeDtoList(
			List<VehicleType> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static List<EnrollmentDto> toEnrollmentDtoList(
			List<Enrollment> list) {
		return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
	}

	public static EnrollmentDto toDto(Enrollment e) {
		EnrollmentDto dto = new EnrollmentDto();

		dto.id = e.getId();
		dto.version = e.getVersion();

		dto.courseId = e.getCourse().getId();
		dto.mechanicId = e.getMechanic().getId();
		dto.attendance = e.getAttendance();
		dto.passed = e.isPassed();

		dto.mechanic = toDto(e.getMechanic());
		dto.course = toDto(e.getCourse());

		return dto;
	}

}

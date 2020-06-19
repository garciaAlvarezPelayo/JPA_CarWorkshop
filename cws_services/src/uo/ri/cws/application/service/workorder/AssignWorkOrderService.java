package uo.ri.cws.application.service.workorder;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.training.CertificateDto;

public interface AssignWorkOrderService {

	/**
	 * Assigns the work order to mechanic so the he/she can see what work has to
	 * today/next. Only work orders with OPEN status can be assigned. Work
	 * orders changes to ASSIGNED status when assigned.
	 *
	 * @param woId,
	 *            the work order id
	 * @param mechanicId,
	 *            the mechanic one
	 *
	 * @throws BusinessException
	 *             if: - the mechanic does not exist, or - the work order does
	 *             not exist, or - the work order is not in OPEN status
	 */
	void assignWorkOrderToMechanic(String woId, String mechanicId)
			throws BusinessException;

	/**
	 * Returns a list of certificates (i.e, certified mechanics) for the vehicle
	 * type. Every certificate includes full mechanic data (@see MechanicDto).
	 *
	 * @param id
	 *            of the vehicle type
	 *
	 * @return the list. It might be empty if no mechanic is certified for the
	 *         specified vehicle type.
	 * @throws BusinessException
	 */
	List<CertificateDto> findCertificatesByVehicleTypeId(String id)
			throws BusinessException;

	/**
	 * @return a list of all work orders either in the OPEN or ASSIGN status
	 * @throws BusinessException,
	 *             DOES NOT
	 */
	List<WorkOrderDto> findUnfinishedWorkOrders() throws BusinessException;

}

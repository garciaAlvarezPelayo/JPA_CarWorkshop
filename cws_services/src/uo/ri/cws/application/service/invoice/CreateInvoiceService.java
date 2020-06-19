package uo.ri.cws.application.service.invoice;

import java.util.List;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.workorder.WorkOrderDto;

/**
 * This service is intended to be used by the Cashier It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface CreateInvoiceService {

	InvoiceDto createInvoiceFor(List<String> workOrderIds)
			throws BusinessException;

	List<WorkOrderDto> findWorkOrdersByClientDni(String dni)
			throws BusinessException;

}

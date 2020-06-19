package uo.ri.cws.application.service.invoice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;

/**
 * This service is intended to be used by the Cashier It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public interface SettleInvoiceService {

	Optional<InvoiceDto> findInvoice(Long number) throws BusinessException;

	List<PaymentMeanDto> findPayMethodsForInvoice(String invoiceId)
			throws BusinessException;

	void settleInvoice(String invoiceId, Map<Long, Double> charges)
			throws BusinessException;

}

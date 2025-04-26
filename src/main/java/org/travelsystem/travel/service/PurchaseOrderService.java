package org.travelsystem.travel.service;

import org.travelsystem.travel.DTO.PurchaseOrderDTO;

public interface PurchaseOrderService {
    PurchaseOrderDTO createOrder(PurchaseOrderDTO orderDTO);
    PurchaseOrderDTO getOrderById(Long id);
    PurchaseOrderDTO updatePaymentStatus(Long id, String status);
}
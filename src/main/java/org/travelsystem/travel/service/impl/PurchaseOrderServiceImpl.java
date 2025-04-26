package org.travelsystem.travel.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.travelsystem.travel.DTO.PurchaseOrderDTO;
import org.travelsystem.travel.respository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.travelsystem.travel.entity.PurchaseOrder;
import org.travelsystem.travel.mapper.PurchaseOrderMapper;
import org.travelsystem.travel.service.PurchaseOrderService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    @Qualifier("purchaseOrderMapperImpl")
    private final PurchaseOrderMapper mapper;

    @Override
    @Transactional
    public PurchaseOrderDTO createOrder(PurchaseOrderDTO orderDTO) {
        PurchaseOrder order = mapper.toEntity(orderDTO);
        order.setCreationTime(LocalDateTime.now());
        order.setPaymentStatus(PurchaseOrder.payment_status.未付款);
        PurchaseOrder savedOrder = repository.save(order);
        return mapper.toDTO(savedOrder);
    }

    @Override
    public PurchaseOrderDTO getOrderById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional
    public PurchaseOrderDTO updatePaymentStatus(Long id, String status) {
        PurchaseOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentStatus(PurchaseOrder.payment_status.valueOf(status));
        order.setPaymentTime(LocalDateTime.now());
        return mapper.toDTO(repository.save(order));
    }
}
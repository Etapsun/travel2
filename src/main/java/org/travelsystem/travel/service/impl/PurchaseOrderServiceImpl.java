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

    // 注入PurchaseOrderRepository，用于数据库操作
    private final PurchaseOrderRepository repository;
    // 注入PurchaseOrderMapper，用于对象转换
    @Qualifier("purchaseOrderMapper")
    private final PurchaseOrderMapper mapper;

    // 创建订单
    @Override
    @Transactional
    public PurchaseOrderDTO createOrder(PurchaseOrderDTO orderDTO) {
        // 将DTO转换为实体对象
        PurchaseOrder order = mapper.toEntity(orderDTO);
        // 设置订单创建时间
        order.setCreationTime(LocalDateTime.now());
        // 设置订单支付状态为未付款
        order.setPaymentStatus(PurchaseOrder.payment_status.未付款);
        // 保存订单到数据库
        PurchaseOrder savedOrder = repository.save(order);
        // 将保存后的实体对象转换为DTO并返回
        return mapper.toDTO(savedOrder);
    }

    // 根据ID获取订单
    @Override
    public PurchaseOrderDTO getOrderById(Long id) {
        // 根据ID查找订单，如果找不到则抛出异常
        return repository.findById(id)
                .map(mapper::toDTO) // 将实体对象转换为DTO
                .orElseThrow(() -> new RuntimeException("Order not found")); // 如果找不到订单，抛出异常
    }

    // 更新订单支付状态
    @Override
    @Transactional
    public PurchaseOrderDTO updatePaymentStatus(Long id, String status) {
        // 根据ID查找订单，如果找不到则抛出异常
        PurchaseOrder order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // 设置订单支付状态
        order.setPaymentStatus(PurchaseOrder.payment_status.valueOf(status));
        // 设置订单支付时间
        order.setPaymentTime(LocalDateTime.now());
        // 保存更新后的订单到数据库
        return mapper.toDTO(repository.save(order));
    }
}
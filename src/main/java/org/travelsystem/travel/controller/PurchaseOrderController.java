package org.travelsystem.travel.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travelsystem.travel.DTO.PurchaseOrderDTO;
import org.travelsystem.travel.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseOrderController {

    // 注入PurchaseOrderService服务，用于处理采购订单相关的业务逻辑
    private final PurchaseOrderService service;
    // 处理POST请求，用于创建新的采购订单
    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createOrder(@RequestBody PurchaseOrderDTO dto) {
        // 调用服务层的createOrder方法创建订单，并返回创建的订单信息
        PurchaseOrderDTO createdOrder = service.createOrder(dto);
        // 返回201 Created状态码和创建的订单信息
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // 处理GET请求，根据订单ID获取订单信息
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getOrderById(@PathVariable Long id) {
        // 调用服务层的getOrderById方法获取订单信息，并返回订单信息
        return ResponseEntity.ok(service.getOrderById(id));
    }

    // 处理PUT请求，更新指定订单的支付状态
    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderDTO> updateStatus(
            @PathVariable Long id, // 从路径中获取订单ID
            @RequestParam String status // 从请求参数中获取新的支付状态
    ) {
        // 调用服务层的updatePaymentStatus方法更新订单的支付状态，并返回更新后的订单信息
        return ResponseEntity.ok(service.updatePaymentStatus(id, status));
    }
}

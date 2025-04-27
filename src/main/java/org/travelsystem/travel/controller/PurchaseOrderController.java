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

    private final PurchaseOrderService service;
    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createOrder(@RequestBody PurchaseOrderDTO dto) {
        PurchaseOrderDTO createdOrder = service.createOrder(dto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(service.updatePaymentStatus(id, status));
    }
}

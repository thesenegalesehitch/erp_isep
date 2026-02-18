package com.isep.controller;

import com.isep.model.Payment;
import com.isep.service.PaymentService;
import com.isep.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);
        Payment savedPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(convertToDTO(savedPayment));
    }
    
    @PostMapping("/{paymentId}/process")
    public ResponseEntity<PaymentDTO> processPayment(
            @PathVariable Long paymentId,
            @RequestBody ProcessPaymentRequest request) {
        Payment payment = paymentService.processPayment(paymentId, request.getPaymentMethod());
        return ResponseEntity.ok(convertToDTO(payment));
    }
    
    @GetMapping("/parent/{parentId}/overdue")
    public ResponseEntity<List<PaymentDTO>> getOverduePayments(@PathVariable Long parentId) {
        List<Payment> payments = paymentService.getOverduePayments(parentId);
        return ResponseEntity.ok(payments.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<PaymentDTO>> getUpcomingPayments(@RequestParam(defaultValue = "7") int daysAhead) {
        List<Payment> payments = paymentService.getUpcomingPayments(daysAhead);
        return ResponseEntity.ok(payments.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<Page<PaymentDTO>> getParentPayments(
            @PathVariable Long parentId,
            Pageable pageable) {
        Page<Payment> payments = paymentService.getParentPayments(parentId, pageable);
        return ResponseEntity.ok(payments.map(this::convertToDTO));
    }
    
    @GetMapping("/parent/{parentId}/total")
    public ResponseEntity<BigDecimal> getTotalPaymentsByParent(
            @PathVariable Long parentId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        BigDecimal total = paymentService.getTotalPaymentsByParentInPeriod(parentId, start, end);
        return ResponseEntity.ok(total);
    }
    
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<PaymentDTO>> getSchoolPayments(@PathVariable Long schoolId) {
        List<Payment> payments = paymentService.getSchoolPayments(schoolId);
        return ResponseEntity.ok(payments.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }
    
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentDTO> refundPayment(
            @PathVariable Long paymentId,
            @RequestBody RefundRequest request) {
        Payment payment = paymentService.refundPayment(paymentId, request.getReason());
        return ResponseEntity.ok(convertToDTO(payment));
    }
    
    @PostMapping("/apply-late-fees")
    public ResponseEntity<Void> applyLateFees() {
        paymentService.applyLateFees();
        return ResponseEntity.ok().build();
    }
    
    private Payment convertToEntity(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setCurrency(dto.getCurrency());
        payment.setPaymentType(dto.getPaymentType());
        payment.setDescription(dto.getDescription());
        payment.setDueDate(dto.getDueDate());
        return payment;
    }
    
    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setParentId(payment.getParent().getId());
        dto.setStudentId(payment.getStudent().getId());
        dto.setSchoolId(payment.getSchool().getId());
        dto.setStudentName(payment.getStudent().getFullName());
        dto.setSchoolName(payment.getSchool().getName());
        dto.setAmount(payment.getAmount());
        dto.setCurrency(payment.getCurrency());
        dto.setPaymentType(payment.getPaymentType());
        dto.setDescription(payment.getDescription());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setDueDate(payment.getDueDate());
        dto.setLateFee(payment.getLateFee());
        dto.setCreatedAt(payment.getCreatedAt());
        return dto;
    }
    
    public static class ProcessPaymentRequest {
        private String paymentMethod;
        
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    }
    
    public static class RefundRequest {
        private String reason;
        
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}

package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordResponse;
import com.oopsw.kostaerpserver.dto.salesrecord.SalesRecordRequest;
import com.oopsw.kostaerpserver.service.entity.salesrecord.SalesRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sales")
public class SalesRecordRestController {
    private final SalesRecordService salesRecordService;

    @GetMapping("/search")
    public List<SalesRecordResponse> getSalesByDate(

            @RequestParam
            String startDate,

            @RequestParam
            String endDate
    ) {
        return salesRecordService.getSalesByDate(
                startDate,
                endDate
        );
    }

    @PostMapping
    public ResponseEntity<Void> addSale(
            @RequestBody
            SalesRecordRequest request
    ) {

        salesRecordService.addSale(
                request.getMenuId(),
                request.getSaleMenuCount(),
                request.getBId(),
                request.getPayment()
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        salesRecordService.deleteSale(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public SalesRecordResponse getSale(@PathVariable String id) {
        return salesRecordService.getSalesById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody SalesRecordRequest request) {
        salesRecordService.updateSale(
                id,
                request.getMenuId(),
                request.getSaleMenuCount(),
                request.getPayment()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public Page<SalesRecordResponse> getSalesList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        int pageNum = (page < 0) ? 0 : page;
        return salesRecordService.getSalesList(pageNum, size);
    }
}

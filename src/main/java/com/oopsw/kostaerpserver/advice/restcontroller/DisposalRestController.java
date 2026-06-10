package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.dto.*;
import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import com.oopsw.kostaerpserver.vo.Disposal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/disposal-items")
public class DisposalRestController {
    private final DisposalService disposalService;
    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    @GetMapping
    public List<DisposalListResponse> getDisposalItems(DisposalSearchRequest request) {
        List<DisposalListResponse> list = null;

        if (hasText(request.getCategory())) {
            list = disposalService.getDisposalsByCategoryAndBId(request.getCategory(), request.getBId());
        } else {
            list = disposalService.getDisposalsFilteredPaging(
                    request.getBId(),
                    request.getPage(),
                    request.getSize());
        }
        if (list != null && hasText(request.getType())) {
            list = list.stream()
                    .filter(disposal -> request.getType().equals(disposal.getFoodMaterialType()))
                    .toList();
        }
        if (list != null && hasText(request.getReason())) {
            list = list.stream()
                    .filter(disposal -> request.getReason().equals(disposal.getReason()))
                    .toList();
        }
        return list;
    }

    @PatchMapping("/{id}/reason")
    public DisposalReasonUpdateResponse updateReason(
            @PathVariable("id") String disposalId,
            @RequestBody DisposalReasonUpdateRequest request
    ){
        boolean success = disposalService.updateReason(disposalId, request.getReasonId());
        return new DisposalReasonUpdateResponse(success);
    }

    @PostMapping
    public DisposalCreateResponse insertDisposalItem(
            @RequestBody DisposalCreateRequest request) {
        boolean success = disposalService.insertDisposal(request);
        return new DisposalCreateResponse(success);
    }
}

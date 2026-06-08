package com.oopsw.kostaerpserver.restcontroller;

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
    public List<DisposalListResponse> getDisposalItems(
            DisposalSearchRequest request
    ){
        List<DisposalListResponse> list;

        if(hasText(request.getCategory())) {
            list = disposalService.getDisposalsByCategoryAndBId(request.getCategory(), request.getBId());
        }else{
            list = disposalService.getDisposalsFilteredPaging(
                    request.getBId(),
                    request.getPage(),
                    request.getSize());
        }

        if(hasText(request.getType())) {
            list = list.stream()
                    .filter(disposal -> request.getType().equals(disposal.getFoodMaterialType()))
                    .toList();
        }

        if(hasText(request.getReason())) {
            list = list.stream()
                    .filter(disposal -> request.getReason().equals(disposal.getReason()))
                    .toList();
        }
        return list;
    }

    @GetMapping(params = "category")
    public List<DisposalListResponse> getByCategory(
            DisposalCategoryRequest request
    ) {
        return disposalService.getDisposalsByCategoryAndBId(
                request.getCategory(),
                request.getBId()
        );
    }

    @GetMapping(params = "type")
    public List<DisposalListResponse> getByType(
            DisposalTypeRequest request
    ){
        List<DisposalListResponse> list = disposalService.getDisposalsPaging(request.getBId(), 1, 1000);

        return list.stream().filter(disposal -> request.getType().equals(disposal.getFoodMaterialType())).toList();
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

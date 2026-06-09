package com.oopsw.kostaerpserver.restcontroller;

import com.oopsw.kostaerpserver.auth.ErpUserDetails;
import com.oopsw.kostaerpserver.dto.disposal.*;
import com.oopsw.kostaerpserver.dto.statistics.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public DisposalPageResponse getDisposalItems(
            DisposalSearchRequest request,
            @ModelAttribute StatisticsRequest statisticsRequest,
            @AuthenticationPrincipal ErpUserDetails erpUserDetails) {
        String bId = erpUserDetails.getLoginUser().getBId();
        List<DisposalListResponse> list = null;

        if (hasText(request.getCategory())) {
            list = disposalService.getDisposalsByCategoryAndBId(request.getCategory(), bId);
        } else {
            list = disposalService.getDisposalsFilteredPaging(
                    bId,
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

        int totalCount = disposalService.getTotalCount(bId);
        int totalPages = (int) Math.ceil((double) totalCount / request.getSize());
        if (totalPages < 1) totalPages = 1;

        return new DisposalPageResponse(list, request.getPage(), totalPages);
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

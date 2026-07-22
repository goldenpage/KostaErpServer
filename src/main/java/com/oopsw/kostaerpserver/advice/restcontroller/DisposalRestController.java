package com.oopsw.kostaerpserver.advice.restcontroller;

import com.oopsw.kostaerpserver.auth.userdetails.AccountDetails;
import com.oopsw.kostaerpserver.dto.disposal.*;
import com.oopsw.kostaerpserver.dto.statistics.StatisticsRequest;
import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            @AuthenticationPrincipal AccountDetails accountDetails) {

        String bId = accountDetails.getAccount().getUsername();

        List<DisposalListResponse> filteredList =
                disposalService.getDisposalsPaging(bId, 1, Integer.MAX_VALUE);

        if (hasText(request.getCategory())) {
            filteredList = filteredList.stream()
                    .filter(disposal -> request.getCategory().equals(disposal.getFoodCategory()))
                    .toList();
        }

        if (hasText(request.getType())) {
            filteredList = filteredList.stream()
                    .filter(disposal -> request.getType().equals(disposal.getFoodMaterialType()))
                    .toList();
        }

        if (hasText(request.getReason())) {
            filteredList = filteredList.stream()
                    .filter(disposal -> request.getReason().equals(disposal.getReason()))
                    .toList();
        }

        int size = request.getSize() <= 0 ? 5 : request.getSize();
        int page = Math.max(request.getPage(), 1);

        int totalCount = filteredList.size();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        if (totalPages < 1) totalPages = 1;

        int fromIndex = Math.min((page - 1) * size, totalCount);
        int toIndex = Math.min(fromIndex + size, totalCount);
        List<DisposalListResponse> pageList = filteredList.subList(fromIndex, toIndex);

        return new DisposalPageResponse(
                pageList,
                page,
                totalPages,
                disposalService.getCategories(bId),
                disposalService.getReasons()
        );
    }

    @GetMapping("/filters")
    public Map<String, List<String>> getDisposalFilters(
            @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = accountDetails.getAccount().getUsername();

        return Map.of(
                "categories", disposalService.getCategories(bId),
                "reasons", disposalService.getReasons()
        );
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
            @RequestBody DisposalCreateRequest request,
            @AuthenticationPrincipal AccountDetails accountDetails
    ) {
        String bId = accountDetails.getAccount().getUsername();
        boolean success = disposalService.insertDisposal(request, bId);
        return new DisposalCreateResponse(success);
    }
}
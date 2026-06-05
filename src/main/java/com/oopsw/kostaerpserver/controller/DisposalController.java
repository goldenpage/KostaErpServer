package com.oopsw.kostaerpserver.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oopsw.kostaerpserver.service.Interface.DisposalService;
import com.oopsw.kostaerpserver.vo.Disposal;

@Controller
@RequiredArgsConstructor
public class DisposalController {

    private final DisposalService disposalService;

    @GetMapping("/disposal-items")
    public String disposalItemsPage(
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String reason,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,

            Model model){

        int totalCount = disposalService.getTotalCount(bId);

        List<Disposal> list;
        if (category != null && !category.isBlank()) {
            list = disposalService.getDisposalsByCategoryAndBId(category, bId);
        } else {
            list = disposalService.getDisposalsPaging(bId, 1, totalCount > 0 ? totalCount : size);
        }

        if (reason != null && !reason.isBlank()) {
            list = list.stream()
                    .filter(disposal -> reason.equals(disposal.getReason())).toList();
        }
        totalCount = list.size();

        int totalPages = (int) Math.ceil((double) totalCount / size);
        int fromIndex = (page - 1) * size;
        int toIndex = fromIndex + size;

        if (toIndex > totalCount) {
            toIndex = totalCount;
        }

        if (fromIndex < totalCount) {
            list = list.subList(fromIndex, toIndex);
        } else {
            list = java.util.Collections.emptyList();
        }

        model.addAttribute("bId", bId);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedReason", reason);
        model.addAttribute("list", list);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("reasons", disposalService.getReasons());
        List<String> categories = disposalService.getCategories().stream().distinct().toList();
        model.addAttribute("categories", categories);

        return "disposalItems";
    }
}


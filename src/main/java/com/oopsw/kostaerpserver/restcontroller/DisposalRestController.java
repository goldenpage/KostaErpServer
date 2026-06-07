package com.oopsw.kostaerpserver.restcontroller;

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
    @GetMapping
    public List<Disposal> getDisposalItems(
            @RequestParam(defaultValue = "0000000000") String bId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, name = "type") String type,
            @RequestParam(required = false) String reason,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        List<Disposal> list;

        if(category != null && !category.isBlank()) {
            list = disposalService.getDisposalsByCategoryAndBId(category, bId);
        }else{
            list = disposalService.getDisposalsPaging(bId, page, size);
        }

        if(type != null && !type.isBlank()) {
            list = list.stream().filter(disposal -> type.equals(disposal.getFoodMaterialType())).toList();
        }

        if(reason != null && !reason.isBlank()) {
            list = list.stream().filter(disposal -> reason.equals(disposal.getReason())).toList();
        }
        return list;
    }

    @GetMapping(params = "category")
    public List<Disposal> getByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0000000000") String bId
    ) {
        return disposalService.getDisposalsByCategoryAndBId(category, bId);
    }

    @GetMapping(params = "type")
    public List<Disposal> getByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0000000000") String bId
    ){
        List<Disposal> list = disposalService.getDisposalsPaging(bId, 1, 1000);

        return list.stream().filter(disposal -> type.equals(disposal.getFoodMaterialType())).toList();
    }

    @PatchMapping("/{id}/reason")
    public boolean updateReason(
            @PathVariable("id") String disposalId,
            @RequestParam String reasonId
    ){
        return disposalService.updateReason(disposalId, reasonId);
    }

    @PostMapping
    public boolean insertDisposalItem(
            @RequestBody Disposal disposal) {
        return disposalService.insertDisposal(disposal);
    }
}

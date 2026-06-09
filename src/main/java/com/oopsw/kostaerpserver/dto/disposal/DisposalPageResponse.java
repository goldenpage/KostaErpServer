package com.oopsw.kostaerpserver.dto.disposal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DisposalPageResponse {
    private List<DisposalListResponse> list;
    private int currentPage;
    private int totalPages;
}

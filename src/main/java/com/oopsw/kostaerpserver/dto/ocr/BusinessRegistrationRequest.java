package com.oopsw.kostaerpserver.dto.ocr;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BusinessRegistrationRequest(
    List<Business> businesses
) {

    public static BusinessRegistrationRequest of(
        String businessNumber,
        String startDate,
        String representativeName,
        String companyName
    ) {
        return new BusinessRegistrationRequest(List.of(new Business(
            businessNumber,
            startDate,
            representativeName,
            companyName
        )));
    }

    public record Business(
        @JsonProperty("b_no") String businessNumber,
        @JsonProperty("start_dt") String startDate,
        @JsonProperty("p_nm") String representativeName,
        @JsonProperty("b_nm") String companyName
    ) {

    }
}

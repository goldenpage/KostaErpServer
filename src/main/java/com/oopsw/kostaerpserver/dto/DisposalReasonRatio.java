package com.oopsw.kostaerpserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisposalReasonRatio{
    private String reasonId;
    private String reason;
    private int reasonCount;
    private Double reasonRatio;

}

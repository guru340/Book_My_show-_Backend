package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payementdto {
    private Long Id;
    private String transctionId;
    private Double amount;
    private LocalDateTime paymentTime;
    private String paymentMethod;
    private String status;
}

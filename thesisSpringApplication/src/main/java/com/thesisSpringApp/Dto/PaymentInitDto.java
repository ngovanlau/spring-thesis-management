package com.thesisSpringApp.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInitDto {
	private long amount;
	private int thesisId;
}

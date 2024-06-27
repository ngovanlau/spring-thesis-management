package com.thesisSpringApp.service;

import com.thesisSpringApp.pojo.Paymentvnpaydetail;
import com.thesisSpringApp.pojo.User;

import java.util.Map;

public interface PaymentvnpaydetailService {
	void saveVnPay(Paymentvnpaydetail paymentvnpaydetail);

	Map<String, String> getInfo(String s);

	boolean checkPayment(String orderId, User user);
}

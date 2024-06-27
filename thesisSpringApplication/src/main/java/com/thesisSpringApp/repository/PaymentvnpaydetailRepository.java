package com.thesisSpringApp.repository;

import com.thesisSpringApp.pojo.Paymentvnpaydetail;
import com.thesisSpringApp.pojo.User;

public interface PaymentvnpaydetailRepository {
	void saveVnPay(Paymentvnpaydetail paymentvnpaydetail);

	Paymentvnpaydetail getPaymentVNPayDetail(String orderId, User user);
}

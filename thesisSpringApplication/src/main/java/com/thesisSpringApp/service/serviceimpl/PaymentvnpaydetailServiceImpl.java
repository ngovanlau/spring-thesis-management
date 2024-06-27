package com.thesisSpringApp.service.serviceimpl;

import com.thesisSpringApp.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thesisSpringApp.pojo.Paymentvnpaydetail;
import com.thesisSpringApp.repository.PaymentvnpaydetailRepository;
import com.thesisSpringApp.service.PaymentvnpaydetailService;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentvnpaydetailServiceImpl implements PaymentvnpaydetailService {

	@Autowired
	private PaymentvnpaydetailRepository paymentvnpaydetailRepository;

	@Override
	public void saveVnPay(Paymentvnpaydetail paymentvnpaydetail) {
		paymentvnpaydetailRepository.saveVnPay(paymentvnpaydetail);
	}

	@Override
	public Map<String, String> getInfo(String input) {
		String[] words = input.trim().split("\\s+");
		Map<String, String> info = new HashMap<>();
		info.put("thesisId", words[0]);
		info.put("username", words[1]);

		return info;
	}

	@Override
	public boolean checkPayment(String orderId, User user) {

		if (!orderId.isEmpty() && user != null) {
			Paymentvnpaydetail paymentvnpaydetail = paymentvnpaydetailRepository.getPaymentVNPayDetail(orderId, user);
            return paymentvnpaydetail != null;
		}

		return false;
	}

}

package com.thesisSpringApp.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thesisSpringApp.Dto.PaymentInitDto;
import com.thesisSpringApp.config.PaymentVnPayConfig;
import com.thesisSpringApp.pojo.Paymentvnpaydetail;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.PaymentvnpaydetailService;
import com.thesisSpringApp.service.UserService;

@RestController
@RequestMapping("/api/payment")
public class PaymentVNPAYApiController {

	@Autowired
	private HttpServletRequest req;
	@Autowired
	private HttpServletResponse resp;
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentvnpaydetailService paymentvnpaydetailService;

	@PostMapping(path = "/")
	@CrossOrigin
	public ResponseEntity<String> payment(@RequestBody PaymentInitDto paymentInitDto)
			throws UnsupportedEncodingException {

		User user = userService.getCurrentLoginUser();


		String vnp_Version = "2.1.0"; // phiên bản
		String vnp_Command = "pay";
		String orderType = "billpayment"; // loại thanh toán
		long amount = paymentInitDto.getAmount() * 100;
		String bankCode = "";

		String vnp_TxnRef = PaymentVnPayConfig.getRandomNumber(8); // tạo id random
		String vnp_IpAddr = PaymentVnPayConfig.getIpAddress(req);

		String vnp_TmnCode = PaymentVnPayConfig.vnp_TmnCode; // mã bí mật trong file .properties

		Map<String, String> vnp_Params = new HashMap<>(); // tạo chuỗi param để gửi cùng url vnp
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		if (bankCode != null && !bankCode.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCode); // nếu bankcode rỗng thì chọn ngân hàng
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", paymentInitDto.getThesisId() + " " + user.getUsername());
		vnp_Params.put("vnp_OrderType", orderType);

		String locate = req.getParameter("language");
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", PaymentVnPayConfig.vnp_ReturnUrl); // url return sau khi
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(
						URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = PaymentVnPayConfig.hmacSHA512(PaymentVnPayConfig.secretKey,
				hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = PaymentVnPayConfig.vnp_PayUrl + "?" + queryUrl; // trả về url cuối cùng
																			// kèm params

		return new ResponseEntity<	>(paymentUrl, HttpStatus.OK);
	}

	@GetMapping("/payment_return/") // xử lý dữ liệu trả về
	@CrossOrigin
	public void payment_return(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String vnpResponseCode = params.get("vnp_ResponseCode");
		Long amount = Long.parseLong(params.get("vnp_Amount")) / 100;
		Map<String, String> info = paymentvnpaydetailService.getInfo(params.get("vnp_OrderInfo"));
		String username = info.get("username");
		User user = userService.getUserByUsername(username);
		int thesisId = Integer.parseInt(info.get("thesisId"));

		if (vnpResponseCode.equals("00")) {
			Paymentvnpaydetail paymentvnpaydetail = new Paymentvnpaydetail();
			paymentvnpaydetail.setOrderId(params.get("vnp_TxnRef"));
			paymentvnpaydetail.setAmount(amount);
			paymentvnpaydetail.setOrderDesc(String.format("Thanh toán phí download file PDF khóa luận có mã %d của sinh vien %s", thesisId, username));
			paymentvnpaydetail.setVnpTransactionNo(params.get("vnp_TransactionNo"));
			paymentvnpaydetail.setVnpResponseCode(params.get("vnp_ResponseCode"));
			paymentvnpaydetail.setUserId(user);
			paymentvnpaydetailService.saveVnPay(paymentvnpaydetail);
			response.sendRedirect(String.format("http://localhost:3000/theses/%d/payment/%s", thesisId, paymentvnpaydetail.getOrderId()));
		} else  {
			response.sendRedirect(String.format("http://localhost:3000/theses/%d/payment/fail", thesisId));
		}
	}

	@GetMapping("/check-payment/{orderId}/")
	@CrossOrigin
	public ResponseEntity<?> checkPayment(@PathVariable(value = "orderId") String orderId) {
		User user = userService.getCurrentLoginUser();
		if (user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		boolean success = paymentvnpaydetailService.checkPayment(orderId, user);

		return new ResponseEntity<>(success, HttpStatus.OK);
	}
}

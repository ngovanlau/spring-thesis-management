package com.thesisSpringApp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.exceptions.IOException;
import com.thesisSpringApp.Dto.PDFInitDto;
import com.thesisSpringApp.service.DownloadPDFService;

@RestController
@RequestMapping("/api/pdf")
public class DownloadPDFApiController {

	@Autowired
	private DownloadPDFService downloadPDFService;


	@PostMapping(path = "/generate/", consumes = {
			MediaType.APPLICATION_JSON_VALUE
	}, produces = {
			MediaType.APPLICATION_JSON_VALUE
	})
	@CrossOrigin
	public ResponseEntity<byte[]> generate(@RequestBody PDFInitDto pdfInitDto)
			throws java.io.IOException {
		try {

			byte[] pdfBytes = downloadPDFService.generatePdf(pdfInitDto);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline");
			return ResponseEntity.ok().headers(headers)
					.contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(pdfBytes);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}

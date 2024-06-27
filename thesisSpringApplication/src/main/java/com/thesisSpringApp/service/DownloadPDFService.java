package com.thesisSpringApp.service;

import com.itextpdf.io.exceptions.IOException;
import com.thesisSpringApp.Dto.PDFInitDto;

public interface DownloadPDFService {
	public byte[] generatePdf(PDFInitDto pdfInitDto)
			throws IOException, java.io.IOException;
}

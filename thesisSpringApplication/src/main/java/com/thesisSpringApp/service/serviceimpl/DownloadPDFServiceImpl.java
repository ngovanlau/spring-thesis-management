package com.thesisSpringApp.service.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.thesisSpringApp.Dto.PDFInitDto;
import com.thesisSpringApp.pojo.Committee;
import com.thesisSpringApp.pojo.CommitteeUser;
import com.thesisSpringApp.pojo.Score;
import com.thesisSpringApp.pojo.Thesis;
import com.thesisSpringApp.pojo.ThesisUser;
import com.thesisSpringApp.pojo.User;
import com.thesisSpringApp.service.CommitteeService;
import com.thesisSpringApp.service.CommitteeUserService;
import com.thesisSpringApp.service.DownloadPDFService;
import com.thesisSpringApp.service.ScoreService;
import com.thesisSpringApp.service.ThesisService;
import com.thesisSpringApp.service.ThesisUserService;

@Service
@PropertySource("classpath:config.properties")
public class DownloadPDFServiceImpl implements DownloadPDFService {

	private ThesisService thesisService;
	private CommitteeService committeeService;
	private ThesisUserService thesisUserService;
	private CommitteeUserService committeeUserService;
	private ScoreService scoreService;
	private Environment env;

	@Autowired
	public DownloadPDFServiceImpl(ThesisService thesisService,
			CommitteeService committeeService,
			ThesisUserService thesisUserService, CommitteeUserService committeeUserService,
			ScoreService scoreService, Environment environment) {
		super();
		this.thesisService = thesisService;
		this.committeeService = committeeService;
		this.thesisUserService = thesisUserService;
		this.committeeUserService = committeeUserService;
		this.scoreService = scoreService;
		this.env = environment;
	}


	@Override
	public byte[] generatePdf(PDFInitDto pdfInitDto) throws IOException, java.io.IOException {

		File fontvuArialFile = new File(env.getProperty("spring.fonts.fontvuArialFile"));
		File fontvuArialBoldFile = new File(
				env.getProperty("spring.fonts.fontvuArialBoldFile"));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(byteArrayOutputStream);
		com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(
				writer);
		Document document = new Document(pdfDoc);
		PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");

		PdfFont vuArialfont = PdfFontFactory.createFont(
				fontvuArialFile.getAbsolutePath(),
				PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

		PdfFont vuArialBoldfont = PdfFontFactory.createFont(
				fontvuArialBoldFile.getAbsolutePath(),
				PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);

		float[] columnWidths = { 1, 1 };
		Table table = new Table(columnWidths);
		table.setWidth(UnitValue.createPercentValue(100));

		Cell leftCell = new Cell()
				.add(new Paragraph("BỘ GIÁO DỤC VÀ ĐÀO TẠO").setFont(vuArialBoldfont));
		leftCell.setTextAlignment(TextAlignment.LEFT);
		leftCell.setBorder(null);
		Cell rightCell = new Cell()
				.add(new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM").setFont(vuArialBoldfont));
		rightCell.setTextAlignment(TextAlignment.RIGHT);
		rightCell.setBorder(null);

		Cell leftCell1 = new Cell()
				.add(new Paragraph("TRƯỜNG ĐẠI HỌC MỞ TP.HCM").setFont(vuArialBoldfont));
		leftCell1.setTextAlignment(TextAlignment.LEFT);
		leftCell1.setBorder(null);

		Cell rightCell1 = new Cell()
				.add(new Paragraph("ĐỘC LẬP-TỰ DO-HẠNH PHÚC").setFont(vuArialBoldfont));
		rightCell1.setTextAlignment(TextAlignment.RIGHT);
		rightCell1.setBorder(null);

		Cell centerCell = new Cell(1, 2)
				.add(new Paragraph("KẾT QUẢ KHÓA LUẬN TỐT NGHIỆP").setFont(vuArialBoldfont)
						.setTextAlignment(TextAlignment.CENTER));
		centerCell.setTextAlignment(TextAlignment.CENTER);
		centerCell.setBorder(null);
		
		Thesis thesis = thesisService.getThesisById(pdfInitDto.getThesisId());
		Cell centerCell1 = new Cell(1, 2)
				.add(new Paragraph("ĐỀ TÀI : " + thesis.getName()).setFont(vuArialfont)
						.setTextAlignment(TextAlignment.CENTER));
		centerCell1.setTextAlignment(TextAlignment.CENTER);
		centerCell1.setBorder(null);

		Cell leftCell2 = new Cell()
				.add(new Paragraph("Các sinh viên thực hiện và giảng viên hướng dẫn")
						.setFont(vuArialBoldfont));
		leftCell2.setTextAlignment(TextAlignment.LEFT);
		leftCell2.setBorder(null);

		table.addCell(leftCell);
		table.addCell(rightCell);
		table.addCell(leftCell1);
		table.addCell(rightCell1);
		table.addCell(centerCell);
		table.addCell(centerCell1);
		table.addCell(leftCell2);
		document.add(table);


		float[] columnWidths1 = { 2, 3, 3 };
		Table table1 = new Table(columnWidths1);
		table1.setWidth(UnitValue.createPercentValue(100));

		table1.addCell(
				new Cell().add(new Paragraph("Mã sinh viên").setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table1.addCell(new Cell().add(new Paragraph("Họ tên").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));
		table1.addCell(new Cell().add(new Paragraph("Ngành").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));

		List<ThesisUser> thesisUsers = thesisUserService.getUserByThesis(thesis);
		for (int i = 0; i < thesisUsers.size(); i++) {
			User user = thesisUsers.get(i).getUserId();
			Cell ic1 = new Cell()
					.add(new Paragraph(user.getUseruniversityid()).setFont(vuArialfont));
			ic1.setTextAlignment(TextAlignment.CENTER);
			Cell ic2 = new Cell()
					.add(new Paragraph(user.getFirstName() + " " + user.getLastName())
							.setFont(vuArialfont).setTextAlignment(TextAlignment.CENTER));
			ic2.setTextAlignment(TextAlignment.CENTER);
			Cell ic3 = new Cell()
					.add(new Paragraph(user.getFacultyId().getName()).setFont(vuArialfont));
			ic3.setTextAlignment(TextAlignment.CENTER);

			table1.addCell(ic1);
			table1.addCell(ic2);
			table1.addCell(ic3);

		}

		document.add(table1);

		float[] columnWidths2 = { 1, 1 };
		Table table2 = new Table(columnWidths2);
		table2.setWidth(UnitValue.createPercentValue(100));

		Cell leftCell3 = new Cell()
				.add(new Paragraph("Các thành viên trong hội đồng chấm điểm : ")
						.setFont(vuArialBoldfont));
		leftCell3.setTextAlignment(TextAlignment.LEFT);
		leftCell3.setBorder(null);
		table2.addCell(leftCell3);

		document.add(table2);

		float[] columnWidths3 = { 2, 3, 3 };
		Table table3 = new Table(columnWidths3);
		table3.setWidth(UnitValue.createPercentValue(100));

		Committee committee = committeeService.getCommitteeById(pdfInitDto.getCommitteeId());
		List<CommitteeUser> committeeUsers = committeeUserService
				.getCommitteeUserByCommittee(committee);

		table3.addCell(
				new Cell()
						.add(new Paragraph("Vai trò trong hội đồng").setFont(vuArialBoldfont)
								.setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table3.addCell(new Cell().add(new Paragraph("Họ tên").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));
		table3.addCell(new Cell().add(new Paragraph("Ngành").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));

		for (int i = 0; i < committeeUsers.size(); i++) {
			User user = committeeUsers.get(i).getUserId();
			Cell ic1 = new Cell()
					.add(new Paragraph(committeeUsers.get(i).getRole()).setFont(vuArialfont));
			ic1.setTextAlignment(TextAlignment.CENTER);
			Cell ic2 = new Cell()
					.add(new Paragraph(user.getFirstName() + " " + user.getLastName())
							.setFont(vuArialfont).setTextAlignment(TextAlignment.CENTER));
			ic2.setTextAlignment(TextAlignment.CENTER);
			Cell ic3 = new Cell()
					.add(new Paragraph(user.getFacultyId().getName()).setFont(vuArialfont));
			ic3.setTextAlignment(TextAlignment.CENTER);

			table3.addCell(ic1);
			table3.addCell(ic2);
			table3.addCell(ic3);
		}

		document.add(table3);

		float[] columnWidths4 = { 1, 1 };
		Table table4 = new Table(columnWidths4);
		table2.setWidth(UnitValue.createPercentValue(100));

		Cell leftCell4 = new Cell()
				.add(new Paragraph("Chi tiết chấm điểm : ")
						.setFont(vuArialBoldfont));
		leftCell4.setTextAlignment(TextAlignment.LEFT);
		leftCell4.setBorder(null);
		table4.addCell(leftCell4);

		document.add(table4);

		float[] columnWidths5 = { 2, 3, 3, 3 };
		Table table5 = new Table(columnWidths5);
		table5.setWidth(UnitValue.createPercentValue(100));

		table5.addCell(
				new Cell().add(new Paragraph("Tên người chấm").setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table5.addCell(new Cell().add(new Paragraph("Tiêu chí").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));
		table5.addCell(
				new Cell().add(new Paragraph("Điểm đạt được").setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table5.addCell(
				new Cell().add(new Paragraph("Điểm tối đa").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));

		List<Score> scores = scoreService.getScoresByThesisId(thesis.getId());
		for (int i = 0; i < scores.size(); i++) {
			User committeeUser = scores.get(i).getCommitteeUserId().getUserId();
			Cell ic1 = new Cell()
					.add(new Paragraph(
							committeeUser.getFirstName() + " " + committeeUser.getLastName())
							.setFont(vuArialfont));
			ic1.setTextAlignment(TextAlignment.CENTER);
			Cell ic2 = new Cell()
					.add(new Paragraph(scores.get(i).getCriteriaId().getName())
							.setFont(vuArialfont).setTextAlignment(TextAlignment.CENTER));
			ic2.setTextAlignment(TextAlignment.CENTER);
			Cell ic3 = new Cell()
					.add(new Paragraph(String.valueOf(scores.get(i).getScore()))
							.setFont(vuArialfont));
			ic3.setTextAlignment(TextAlignment.CENTER);

			Cell ic4 = new Cell()
					.add(new Paragraph("10")
							.setFont(vuArialfont));
			ic4.setTextAlignment(TextAlignment.CENTER);

			table5.addCell(ic1);
			table5.addCell(ic2);
			table5.addCell(ic3);
			table5.addCell(ic4);

		}

		document.add(table5);

		float[] columnWidths6 = { 2, 3, 3 };
		Table table6 = new Table(columnWidths6);
		table2.setWidth(UnitValue.createPercentValue(100));

		Cell leftCell6 = new Cell()
				.add(new Paragraph("Kết quả tổng kết :")
						.setFont(vuArialBoldfont));
		leftCell6.setTextAlignment(TextAlignment.LEFT);
		leftCell6.setBorder(null);
		table6.addCell(leftCell6);

		document.add(table6);

		float[] columnWidths7 = { 2, 3, 3, 3 };
		Table table7 = new Table(columnWidths7);
		table7.setWidth(UnitValue.createPercentValue(100));

		table7.addCell(
				new Cell().add(new Paragraph("Mã đồ án").setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table7.addCell(new Cell().add(new Paragraph("Tên đồ án").setFont(vuArialBoldfont).setBold())
				.setTextAlignment(TextAlignment.CENTER));
		table7.addCell(
				new Cell().add(new Paragraph("Điểm trung bình").setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table7.addCell(
				new Cell().add(new Paragraph("Kết quả đầu ra").setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));

		table7.addCell(
				new Cell()
						.add(new Paragraph(thesis.getId().toString()).setFont(vuArialBoldfont)
								.setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table7.addCell(
				new Cell().add(new Paragraph(thesis.getName()).setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table7.addCell(
				new Cell()
						.add(new Paragraph(String.valueOf(thesis.getScore()))
								.setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));
		table7.addCell(
				new Cell()
						.add(new Paragraph(thesis.getScore() >= 4.0 ? "Đạt" : "Không đạt")
								.setFont(vuArialBoldfont).setBold())
						.setTextAlignment(TextAlignment.CENTER));

		document.add(table7);

		float[] columnWidths8 = { 1, 2 };
		Table table8 = new Table(columnWidths6);
		table2.setWidth(UnitValue.createPercentValue(100));

		Cell leftCell8 = new Cell()
				.add(new Paragraph("Ký tên xác nhận các thành viên liên quan :")
						.setFont(vuArialBoldfont));
		leftCell8.setTextAlignment(TextAlignment.LEFT);
		leftCell8.setBorder(null);
		table8.addCell(leftCell8);

		document.add(table8);

		document.setFont(vuArialfont);
		document.close();

		return byteArrayOutputStream.toByteArray();
	}


}

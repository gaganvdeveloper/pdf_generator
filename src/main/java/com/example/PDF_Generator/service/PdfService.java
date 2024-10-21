package com.example.PDF_Generator.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.pdf.entity.Invoice;
import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {

	private final TemplateEngine templateEngine;

	public PdfService(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String generatePdf(Invoice invoice) throws Exception {
		// Create Thymeleaf Context and add data to it
		Context context = new Context();
		context.setVariable("invoice", invoice);

		// Process the HTML template with Thymeleaf
		String htmlContent = templateEngine.process("invoice", context);

		// Generate PDF file using OpenPDF
		String fileName = UUID.randomUUID().toString() + ".pdf";
		File pdfFile = new File("C:\\Users\\gagan\\Desktop\\cda 2pm react\\" + fileName);

		try (OutputStream outputStream = new FileOutputStream(pdfFile)) {
			// Create PDF document
			Document document = new Document();
			PdfWriter.getInstance(document, outputStream);

			// Open document to write content
			document.open();

			// Convert HTML to PDF (in-memory conversion)
			ByteArrayInputStream htmlStream = new ByteArrayInputStream(htmlContent.getBytes());
			convertHtmlToPdf(htmlStream, document);

			// Close the document
			document.close();
		}

		return pdfFile.getAbsolutePath();
	}

	private void convertHtmlToPdf(ByteArrayInputStream htmlStream, Document document) {
		// You can use libraries like `com.lowagie.text.html.simpleparser` to help parse
		// HTML to the PDF document.
		// This is a placeholder for HTML-to-PDF parsing logic.
		try {
			// Use OpenPDF's HTMLWorker to parse the HTML content and add it to the document
			StyleSheet styles = new StyleSheet();
			HTMLWorker worker = new HTMLWorker(document);

			// Parse HTML from the input stream
			worker.parse(new InputStreamReader(htmlStream, StandardCharsets.UTF_8));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getPdf(String filePath) throws Exception {
		return Files.readAllBytes(Paths.get(filePath));
	}
}

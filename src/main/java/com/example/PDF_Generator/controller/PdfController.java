package com.example.PDF_Generator.controller;

import java.io.File;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.PDF_Generator.service.PdfService;
import com.example.pdf.entity.Invoice;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody Invoice invoice) {
        try {
            String filePath = pdfService.generatePdf(invoice);
            return ResponseEntity.ok("PDF generated and stored at: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf(@RequestParam("filePath") String filePath) {
        try {
            byte[] pdfContent = pdfService.getPdf(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", new File(filePath).getName());
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

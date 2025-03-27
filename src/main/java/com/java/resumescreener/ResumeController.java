package com.java.resumescreener;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Tag(name = "Resume Analyzer API", description = "Endpoints for analyzing resumes")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Operation(
        summary = "Analyze Resume",
        description = "Uploads a resume file and returns analysis results.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully analyzed resume"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
        }
    )

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Async("taskExecutor") 
    public CompletableFuture<ResponseEntity<?>> analyzeResume(@RequestParam("file") MultipartFile file) {  
    System.out.println("Received file: " + file.getOriginalFilename());  // Debugging log  

    return CompletableFuture.supplyAsync(() -> {
        try {
            String extractedText = resumeService.extractText(file);
            ResumeAnalysisResult result = resumeService.analyzeResume(extractedText);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing resume: " + e.getMessage());
        }
    });
}


    @PostMapping("/ask-ai")
    public ResponseEntity<?> askOpenAI(@RequestParam("question") String question) {
        String response = resumeService.callOpenAI(question);
        return ResponseEntity.ok(response);
    }
}

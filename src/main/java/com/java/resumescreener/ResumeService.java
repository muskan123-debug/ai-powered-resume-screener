package com.java.resumescreener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.Tika;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;



@Service
public class ResumeService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String OPENAI_URL;
    
    public String extractText(MultipartFile file) throws Exception {
        Tika tika = new Tika();
        return tika.parseToString(file.getInputStream());
    }

    public ResumeAnalysisResult analyzeResume(String resumeText,String jobDescription) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Construct the conversation messages
    JSONArray messages = new JSONArray();
    messages.put(new JSONObject().put("role", "system").put("content", 
        "You are an AI trained to analyze resumes and provide a match score (1-100%) with feedback."));
    messages.put(new JSONObject().put("role", "user").put("content", 
        "Compare the following resume against this job description:\n\n" +
        "Job Description:\n" + jobDescription + "\n\n" +
        "Resume:\n" + resumeText + 
        "\n\nProvide a match score (1-100%) and key feedback."));

    // Construct the OpenAI API request body
    JSONObject requestBody = new JSONObject();
    requestBody.put("model", "gpt-4");
    requestBody.put("max_tokens", 300);
    requestBody.put("temperature", 0.3);
    requestBody.put("messages", messages);  // Corrected to use "messages"

        //Send request to OpenAI
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(
        OPENAI_URL,
        HttpMethod.POST,
        entity,
        String.class
    );

        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray choices = jsonResponse.getJSONArray("choices");
        String feedback = choices.getJSONObject(0).getJSONObject("message").getString("content");
        int matchScore = extractMatchScore(feedback);  // Extract the correct match score
        System.out.print("score::"+matchScore+" ,matchScore::"+feedback);
        return new ResumeAnalysisResult(matchScore, feedback);
    }

    private int extractMatchScore(String feedback) {
        // Basic logic to extract a match score (this depends on how GPT-4 formats its response)
        Pattern pattern = Pattern.compile("Match Score: (\\d+)%");
        Matcher matcher = pattern.matcher(feedback);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0; 
    }

    public String callOpenAI(String userInput) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Prepare request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Create JSON request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4");
            requestBody.put("max_tokens", 75);

            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."));
            messages.put(new JSONObject().put("role", "user").put("content", userInput));

            requestBody.put("messages", messages);

            // Send POST request
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity, String.class);

            return response.getBody();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

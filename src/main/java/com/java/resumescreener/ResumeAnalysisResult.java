package com.java.resumescreener;

public class ResumeAnalysisResult {
    
    private int matchScore;
    private String feedback;

    public ResumeAnalysisResult(int matchScore, String feedback) {
        this.matchScore = matchScore;
        this.feedback = feedback;
    }

    public int getMatchScore() { return matchScore; }
    public String getFeedback() { return feedback; }

    public ResumeAnalysisResult(String feedback){
        this.feedback=feedback;
    }
}

# 🚀 AI-Powered Resume Screener

## 🌟 Overview
AI-Powered Resume Screener is an intelligent Chrome extension integrated with a Spring Boot backend. It leverages OpenAI's capabilities to analyze resumes, evaluate their relevance for job roles, and provide actionable improvement suggestions.

## 🔥 Key Features
- **Seamless Resume Upload**: Upload and analyze resumes directly from a Chrome extension.
- **AI-Driven Analysis**: Extracts key insights and scores resumes based on job relevance.
- **Smart Suggestions**: Provides personalized feedback to enhance resume effectiveness.
- **Backend Powered by Spring Boot**: Robust API for resume processing and AI integration.
- **Scalable & Extensible**: Future-ready architecture with MongoDB support.

---

## 🖥️ 1️⃣ Chrome Extension (Frontend)
### 📂 Folder Structure
```
resume-screener-extension/
│── manifest.json
│── popup.html
│── popup.js
│── content.js
│── background.js
│── styles.css
│── icons/
│── README.md
```
### 🚀 Setup & Installation
1. Open `chrome://extensions/` in Chrome.
2. Enable **Developer mode**.
3. Click **Load Unpacked** and select the `resume-screener-extension` folder.
4. Click on the extension icon to upload and analyze a resume instantly.

---

## 🔗 2️⃣ Spring Boot Backend (API)
### 📂 Folder Structure
```
resume-screener-backend/
│── src/main/java/com/resumescreener/
│   ├── ResumeScreenerApplication.java
│   ├── controller/ResumeController.java
│   ├── service/ResumeService.java
│   ├── model/ResumeAnalysisResult.java
│   ├── utils/OCRUtil.java
│── resources/application.properties
│── pom.xml
```
### ⚡ Setup & Running Locally
#### Prerequisites
- Java 17+
- Maven
- MongoDB (optional for storing analysis results)

#### 🚀 Steps to Run
1. Clone the repository.
2. Navigate to `resume-screener-backend`.
3. Update the **OpenAI API key** in `ResumeService.java`.
4. Start the backend:
   ```sh
   mvn spring-boot:run
   ```

---

## 🔍 3️⃣ API Endpoints
### 1️⃣ Resume Analysis
**Endpoint:** `POST /api/analyze`
- **Request:**
  - Form-data: `file` (PDF/DOC/DOCX)
- **Response:**
  ```json
  {
      "matchScore": 87,
      "feedback": "Your resume has strong technical skills but could highlight more leadership experience."
  }
  ```

---

## 🔮 Future Enhancements
- 📄 **Extract resumes directly from LinkedIn/Naukri.**
- 💾 **Store analysis history in MongoDB.**
- ☁️ **Deploy backend on AWS/GCP for scalability.**
- 🤖 **Enhance AI feedback using LangChain/OpenAI.**

---

*Muskan Kishore*
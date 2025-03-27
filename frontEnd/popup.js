document.getElementById("analyzeResume").addEventListener("click", async () => {
    const fileInput = document.getElementById("resumeInput");
    const jobDescription = document.getElementById("jobDescription").value.trim();

    if (fileInput.files.length === 0) {
        alert("Please upload a resume.");
        return;
    }
    if (jobDescription === "") {
        alert("Please enter a job description.");
        return;
    }

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    formData.append("jobDescription", jobDescription);

    try {
        const response = await fetch("http://localhost:8080/api/analyze", {
            method: "POST",
            body: formData,
            credentials: "include", // ✅ Fix for CORS
            headers: {
                "Accept": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const result = await response.json();
        document.getElementById("result").innerText = `Match Score: ${result.matchScore}%\n${result.feedback}`;
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to analyze resume.");
    }
});

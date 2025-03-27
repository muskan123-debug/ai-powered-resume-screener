document.getElementById("analyzeResume").addEventListener("click", async () => {
    const fileInput = document.getElementById("resumeInput");
    const jobDescription = document.getElementById("jobDescription").value.trim();
    const resultDiv = document.getElementById("result");
    const loadingDiv = document.getElementById("loading");

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

    // Show loading animation and clear previous results
    loadingDiv.style.display = "block";
    resultDiv.innerText = "";

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
        resultDiv.innerHTML = `<p>Match Score: <strong>${result.matchScore}%</strong></p>
                               <p>${result.feedback}</p>`;
    } catch (error) {
        console.error("Error:", error);
        alert("Failed to analyze resume.");
    } finally {
        // Hide loading animation
        loadingDiv.style.display = "none";
    }
});

console.log("Background script loaded");

// Example: Listen for extension startup
chrome.runtime.onInstalled.addListener(() => {
    console.log("AI Resume Screener installed!");
});

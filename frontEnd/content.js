chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
    if (message.action === "extractResume") {
        let text = document.body.innerText;
        sendResponse({ text });
    }
});

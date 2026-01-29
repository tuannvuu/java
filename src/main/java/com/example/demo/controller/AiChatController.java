package com.example.demo.controller;

import com.example.demo.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final GeminiService geminiService;

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> req) {
        System.out.println("=== [CONTROLLER] /api/ai/chat HIT ===");
        System.out.println("[CONTROLLER] Request body: " + req);

        try {
            String message = req.get("message");
            System.out.println("[CONTROLLER] Message: " + message);

            if (message == null || message.trim().isEmpty()) {
                System.out.println("[CONTROLLER] ❌ Empty message");
                return Map.of("reply", "Vui lòng nhập tin nhắn!");
            }

            String reply = geminiService.chat(message);
            System.out.println("[CONTROLLER] Reply received: " + (reply != null ? "✅" : "❌ null"));

            return Map.of("reply", reply != null ? reply : "Lỗi khi gọi AI");

        } catch (Exception e) {
            System.err.println("[CONTROLLER] ❌ ERROR: " + e.getMessage());
            e.printStackTrace();
            return Map.of("reply", "❌ Lỗi server: " + e.getMessage());
        }
    }
}
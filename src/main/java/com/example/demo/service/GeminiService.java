package com.example.demo.service;

import com.example.demo.config.GeminiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

        private final GeminiProperties geminiProperties;

        public GeminiService(GeminiProperties geminiProperties) {
                this.geminiProperties = geminiProperties;
        }

        // 🔥 DÙNG MODEL MỚI NHẤT: gemini-2.5-flash
        private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

        private final RestTemplate restTemplate = new RestTemplate();
        private final ObjectMapper objectMapper = new ObjectMapper();

        public String chat(String message) {
                try {
                        System.out.println("[GEMINI] Calling API with message: " + message);

                        Map<String, Object> body = Map.of(
                                        "contents", List.of(
                                                        Map.of("parts", List.of(
                                                                        Map.of("text", message)))));

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

                        ResponseEntity<String> response = restTemplate.postForEntity(
                                        URL + geminiProperties.getKey(),
                                        request,
                                        String.class);

                        JsonNode root = objectMapper.readTree(response.getBody());

                        String reply = root
                                        .path("candidates")
                                        .path(0)
                                        .path("content")
                                        .path("parts")
                                        .path(0)
                                        .path("text")
                                        .asText();

                        System.out.println("[GEMINI] ✅ Reply: " + reply);
                        return reply;

                } catch (Exception e) {
                        System.err.println("[GEMINI] ❌ Error: " + e.getMessage());
                        return "❌ Lỗi khi gọi Gemini API: " + e.getMessage();
                }
        }
}
package com.ingsw.backend.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        filterChain.doFilter(wrappedRequest, response);

        byte[] buf = wrappedRequest.getContentAsByteArray();
        if (buf.length > 0) {
            String payload = new String(buf, 0, buf.length, wrappedRequest.getCharacterEncoding());

            String formattedPayload;
            try {
                Object json = objectMapper.readValue(payload, Object.class);
                formattedPayload = objectMapper.writeValueAsString(json);
            } catch (JsonProcessingException e) {
                formattedPayload = payload;
            }

            Path logDir = Paths.get("logs");
            if (!Files.exists(logDir)) {
                Files.createDirectory(logDir);
            }

            try (FileWriter writer = new FileWriter(logDir.resolve("body.txt").toFile(), true)) {
                writer.write(formattedPayload + "\n");
            }
        }
        */
    }
}
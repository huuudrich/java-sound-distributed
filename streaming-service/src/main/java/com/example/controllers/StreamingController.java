package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public class StreamingController {
    @GetMapping("/stream/{filename}")
    public StreamingResponseBody stream(@PathVariable("filename") String filename) throws FileNotFoundException {
        File file = new File("path/to/save/" + filename);
        InputStream inputStream = new FileInputStream(file);
        return outputStream -> {
            readAndWrite(inputStream, outputStream);
        };
    }
}

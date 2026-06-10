package com.oopsw.kostaerpserver.restcontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.oopsw.kostaerpserver.advice.restcontroller.OcrRestController;
import com.oopsw.kostaerpserver.dto.ocr.OcrResponse;
import com.oopsw.kostaerpserver.service.OcrServiceImpl;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class OcrRestControllerTest {

    @Test
    void returnsOcrResponseAsJson() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "document.png",
            MediaType.IMAGE_PNG_VALUE,
            "image-content".getBytes()
        );
        OcrResponse response = new OcrResponse("V2", "test-request", 1L, List.of());
        OcrServiceImpl ocrService = new OcrServiceImpl(null, null, null) {
            @Override
            public OcrResponse requestOcr(MultipartFile multipartFile) {
                return response;
            }
        };
        MockMvc mockMvc =
            MockMvcBuilders.standaloneSetup(new OcrRestController(ocrService)).build();

        mockMvc.perform(multipart("/api/ocr").file(file))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.version").value("V2"))
            .andExpect(jsonPath("$.requestId").value("test-request"));
    }
}

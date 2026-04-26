package ua.kpi.bank.presentation;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateDepositWithdrawAndReturnCorrectBalance() throws Exception {

        String createResponse = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 100,
                                  "currency": "USD"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = JsonPath.read(createResponse, "$.id");


        mockMvc.perform(post("/accounts/" + id + "/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 50,
                                  "currency": "USD"
                                }
                                """))
                .andExpect(status().isOk());


        mockMvc.perform(post("/accounts/" + id + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 30,
                                  "currency": "USD"
                                }
                                """))
                .andExpect(status().isOk());


        mockMvc.perform(get("/accounts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(120)) // 100 + 50 - 30
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void shouldFailWithdrawTooMuch() throws Exception {
        String create = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "amount": 100,
                          "currency": "USD"
                        }
                    """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = JsonPath.read(create, "$.id");

        mockMvc.perform(post("/accounts/" + id + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "amount": 200,
                          "currency": "USD"
                        }
                    """))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/accounts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100));
    }

    @Test
    void shouldReturn404WhenAccountNotFound() throws Exception {
        mockMvc.perform(get("/accounts/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
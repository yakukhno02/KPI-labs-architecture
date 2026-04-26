package ua.kpi.bank.presentation;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAccount() throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 100,
                                  "currency": "USD"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance").value(100))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void shouldDepositMoney() throws Exception {
        String createJson = """
        {
          "amount": 100,
          "currency": "USD"
        }
    """;

        String response = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = JsonPath.read(response, "$.id");

        String depositJson = """
        {
          "amount": 50,
          "currency": "USD"
        }
    """;

        mockMvc.perform(post("/accounts/" + id + "/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(depositJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(150))
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    void shouldWithdrawMoney() throws Exception {
        String createJson = """
        {
          "amount": 100,
          "currency": "USD"
        }
    """;

        String response = mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = JsonPath.read(response, "$.id");

        String withdrawJson = """
        {
          "amount": 40,
          "currency": "USD"
        }
    """;

        mockMvc.perform(post("/accounts/" + id + "/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withdrawJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(60))
                .andExpect(jsonPath("$.currency").value("USD"));
    }
}
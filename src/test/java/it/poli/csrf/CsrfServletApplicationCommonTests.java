package it.poli.csrf;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.poli.csrf.model.health.HealthModel;
import it.poli.csrf.model.loggers.LoggerLevelsDescriptorModel;
import it.poli.csrf.model.loggers.LoggersDescriptorModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class CsrfServletApplicationCommonTests {

  @Autowired private WebApplicationContext context;

  @Autowired ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  final void testHealthGetOk() throws Exception {
    String jsonResponse =
        mockMvc
            .perform(get("/actuator/health"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(jsonResponse, "null jsonResponse");

    HealthModel health = objectMapper.readValue(jsonResponse, HealthModel.class);
    assertNotNull(health, "null health");
  }

  @Test
  final void testLogsGetOk() throws Exception {
    String jsonResponse =
        mockMvc
            .perform(get("/actuator/loggers"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    assertNotNull(jsonResponse, "null jsonResponse");

    LoggersDescriptorModel loggersDescriptor =
        objectMapper.readValue(jsonResponse, LoggersDescriptorModel.class);
    assertNotNull(loggersDescriptor, "loggersDescriptor health");
  }

  @Test
  final void testLogsGetLoggers() throws Exception {
    LoggerLevelsDescriptorModel loggerLevelsDescriptor = this.getLoggerLevelsDescriptor("it.poli");
    assertNotNull(loggerLevelsDescriptor.getConfiguredLevel(), "null configuredLevel");
  }

  protected LoggerLevelsDescriptorModel getLoggerLevelsDescriptor(String logger) throws Exception {
    String jsonResponse =
        mockMvc
            .perform(get("/actuator/loggers"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    LoggersDescriptorModel loggersDescriptor =
        objectMapper.readValue(jsonResponse, LoggersDescriptorModel.class);
    assertNotNull(loggersDescriptor, "null loggersDescriptor");
    assertNotNull(loggersDescriptor.getLoggers(), "null loggers");
    LoggerLevelsDescriptorModel loggerLevelsDescriptor = loggersDescriptor.getLoggers().get(logger);
    assertNotNull(loggerLevelsDescriptor, "null loggerLevelsDescriptor");

    return loggerLevelsDescriptor;
  }
}

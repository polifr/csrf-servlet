package it.poli.csrf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.poli.csrf.configuration.CsrfSecurityConfiguration;
import it.poli.csrf.model.loggers.LoggerLevelsDescriptorModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"secure"})
@Import({CsrfSecurityConfiguration.class})
class CsrfServletApplicationSecureTests extends CsrfServletApplicationCommonTests {

  @ParameterizedTest
  @ValueSource(strings = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF"})
  final void testSetLoggerLevelForbidden(String level) throws Exception {
    this.setLoggerLevel("it.poli", level).andExpect(status().isForbidden());
  }

  @ParameterizedTest
  @ValueSource(strings = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF"})
  final void testSetLoggerLevelOk(String level) throws Exception {
    this.setLoggerLevelWithCsrf("it.poli", level).andExpect(status().is2xxSuccessful());
    LoggerLevelsDescriptorModel loggerLevelsDescriptor = this.getLoggerLevelsDescriptor("it.poli");
    assertNotNull(loggerLevelsDescriptor, "null loggerLevelsDescriptor");
    assertEquals(level, loggerLevelsDescriptor.getConfiguredLevel(), "incongruent logger lever");
  }
}

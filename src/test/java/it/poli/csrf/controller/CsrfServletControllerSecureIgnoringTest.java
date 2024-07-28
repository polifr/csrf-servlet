package it.poli.csrf.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.poli.csrf.configuration.CsrfSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CsrfServletController.class)
@ActiveProfiles(profiles = {"secure-ignoring"})
@Import({CsrfSecurityConfiguration.class})
class CsrfServletControllerSecureIgnoringTest {

  @Autowired private WebApplicationContext context;

  private MockMvc mockMvc;

  @MockBean JwtDecoder jwtDecoder;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  void testUnauthorizedGet() throws Exception {
    this.mockMvc.perform(get("/csrf/test-get")).andDo(print()).andExpect(status().isUnauthorized());
  }

  @Test
  void testUnauthorizedPost() throws Exception {
    this.mockMvc
        .perform(post("/csrf/test-post").with(csrf()))
        .andDo(print())
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void testGetOk() throws Exception {
    this.mockMvc.perform(get("/csrf/test-get")).andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void testForbiddenPost() throws Exception {
    this.mockMvc.perform(post("/csrf/test-post")).andDo(print()).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser
  void testPostOk() throws Exception {
    this.mockMvc
        .perform(post("/csrf/test-post").with(csrf()))
        .andDo(print())
        .andExpect(status().isOk());
  }
}

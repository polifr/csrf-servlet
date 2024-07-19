package it.poli.csrf;

import it.poli.csrf.configuration.NoCsrfSecurityConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"insecure"})
@Import({NoCsrfSecurityConfiguration.class})
class CsrfServletApplicationInsecureTests extends CsrfServletApplicationCommonTests {}

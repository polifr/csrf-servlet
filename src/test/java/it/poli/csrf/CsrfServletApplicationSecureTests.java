package it.poli.csrf;

import it.poli.csrf.configuration.CsrfSecurityConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"secure"})
@Import({CsrfSecurityConfiguration.class})
class CsrfServletApplicationSecureTests extends CsrfServletApplicationCommonTests {}

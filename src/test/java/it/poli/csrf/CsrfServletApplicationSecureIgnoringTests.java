package it.poli.csrf;

import it.poli.csrf.configuration.CsrfIgnoringSecurityConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"secure-ignoring"})
@Import({CsrfIgnoringSecurityConfiguration.class})
class CsrfServletApplicationSecureIgnoringTests extends CsrfServletApplicationCommonTests {}

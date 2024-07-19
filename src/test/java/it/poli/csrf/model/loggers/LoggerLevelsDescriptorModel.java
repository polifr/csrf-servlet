package it.poli.csrf.model.loggers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.actuate.logging.LoggersEndpoint.LoggerLevelsDescriptor;

/**
 * @see LoggerLevelsDescriptor
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoggerLevelsDescriptorModel {

  private String configuredLevel;
  private String effectiveLevel;
}

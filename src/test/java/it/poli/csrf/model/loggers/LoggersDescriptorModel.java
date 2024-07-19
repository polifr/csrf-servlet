package it.poli.csrf.model.loggers;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.actuate.logging.LoggersEndpoint.LoggersDescriptor;

/**
 * @see LoggersDescriptor
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class LoggersDescriptorModel {

  private List<String> levels;

  private Map<String, LoggerLevelsDescriptorModel> loggers;
}

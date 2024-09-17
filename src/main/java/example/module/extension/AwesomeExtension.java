package example.module.extension;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.tooling.AgentExtension;
import io.opentelemetry.sdk.autoconfigure.spi.ConfigProperties;
import net.bytebuddy.agent.builder.AgentBuilder;

@AutoService(AgentExtension.class)
@SuppressWarnings("unused")
public class AwesomeExtension implements AgentExtension {

    @Override
    public AgentBuilder extend(AgentBuilder agentBuilder, ConfigProperties configProperties) {
        return agentBuilder;
    }

    @Override
    public String extensionName() {
        return "awesome-extension";
    }
}

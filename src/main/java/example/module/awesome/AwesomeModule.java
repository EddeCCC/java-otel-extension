package example.module.awesome;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.List;
import java.util.logging.Logger;

@AutoService(InstrumentationModule.class)
@SuppressWarnings("unused")
public class AwesomeModule extends InstrumentationModule {

    private static final Logger log = Logger.getLogger(AwesomeModule.class.getName());

    public AwesomeModule() {
        super("awesome-instrumentation", "awesome-instrumentation-1.0");
        log.info("Awesome module loaded");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return List.of(new AwesomeInstrumentation());
    }
}

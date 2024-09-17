package example.module.awesome;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;

import java.util.List;

@AutoService(InstrumentationModule.class)
@SuppressWarnings("unused")
public class AwesomeModule extends InstrumentationModule {

    public AwesomeModule() {
        super("awesome-instrumentation", "awesome-instrumentation-1.0");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        return List.of(new AwesomeInstrumentation());
    }
}

package example.module.simple;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//@AutoService(InstrumentationModule.class)
@SuppressWarnings("unused")
public class MyModule extends InstrumentationModule {

    public MyModule() {
        super("my-simple-instrumentation", "my-simple-instrumentation-1.0");
    }

    @Override
    public List<TypeInstrumentation> typeInstrumentations() {
        System.out.println("LOADING MY SIMPLE INSTRUMENTATION");
        return Collections.singletonList(new MyInstrumentation());
    }
//
//    @Override
//    public List<String> getAdditionalHelperClassNames() {
//        List<String> helpers = new LinkedList<>();
//        helpers.add(MyInstrumentation.class.getName());
//        helpers.add( "io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation");
//        return helpers;
//    }
//
//    @Override
//    public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
//        return AgentElementMatchers.hasClassesNamed("com.example.demo.GreetingController");
//    }
}

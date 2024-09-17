package example.module.simple;

import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MyInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return named("com.example.demo.GreetingController");
    }

    @Override
    public void transform(TypeTransformer typeTransformer) {
        typeTransformer.applyAdviceToMethod(
                isMethod()
                        .and(named("greeting")),
                this.getClass().getName() + "$SimpleAdvice"
        );
    }

    @SuppressWarnings("unused")
    public static class SimpleAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onEnter(@Advice.AllArguments Object[] args,
                                   @Advice.This Object thiz) {
            System.out.println("ENTER FIRST ADVICE");
        }

        @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
        public static void onExit(@Advice.AllArguments Object[] args,
                                  @Advice.This Object thiz,
                                  @Advice.Thrown Throwable throwable) {
            System.out.println("EXIT FIRST ADVICE");
        }
    }
}

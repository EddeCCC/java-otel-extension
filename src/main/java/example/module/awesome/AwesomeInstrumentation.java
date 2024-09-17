package example.module.awesome;

import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;

public class AwesomeInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return ElementMatchers.nameEndsWith("Controller");
    }

    @Override
    public void transform(TypeTransformer typeTransformer) {
        typeTransformer.applyAdviceToMethod(
                isMethod(),
                this.getClass().getName() + "$AwesomeAdvice"
        );
    }

    @SuppressWarnings("unused")
    public static class AwesomeAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static void onEnter(@Advice.AllArguments Object[] args,
                                   @Advice.This Object thiz,
                                   @Advice.Origin("#m") String methodName) {
            System.out.println("ENTER TEST ADVICE");
            System.out.println("Class: " + thiz.getClass().getName());
            System.out.println("Method: " + methodName);
        }

        @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
        public static void onExit(@Advice.AllArguments Object[] args,
                                  @Advice.This Object thiz,
                                  @Advice.Thrown Throwable throwable) {
            System.out.println("EXIT TEST ADVICE");
        }
    }
}

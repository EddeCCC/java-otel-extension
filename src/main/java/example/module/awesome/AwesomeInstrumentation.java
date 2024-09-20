package example.module.awesome;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Objects;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class AwesomeInstrumentation implements TypeInstrumentation {

    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
        return ElementMatchers.nameEndsWith("Pet");
    }

    @Override
    public void transform(TypeTransformer typeTransformer) {
        typeTransformer.applyAdviceToMethod(
                nameStartsWith("get"),
                this.getClass().getName() + "$AwesomeAdvice"
        );
    }

    @SuppressWarnings("unused")
    public static class AwesomeAdvice {
        @Advice.OnMethodEnter(suppress = Throwable.class)
        public static Scope onEnter(@Advice.AllArguments Object[] args,
                                   @Advice.This Object thiz,
                                   @Advice.Origin("#m") String methodName,
                                   @Advice.Local("otelSpan") Span span) {
            System.out.println("ENTER METHOD");
            System.out.println("Class: " + thiz.getClass().getName());
            System.out.println("Method: " + methodName);

            Tracer tracer = GlobalOpenTelemetry.getTracer("awesome-instrumentation", "1");
            span = tracer.spanBuilder(methodName).startSpan();

            Scope scope = span.makeCurrent();
            return scope;
        }

        @Advice.OnMethodExit(suppress = Throwable.class, onThrowable = Throwable.class)
        public static void onExit(@Advice.AllArguments Object[] args,
                                  @Advice.This Object thiz,
                                  @Advice.Return(typing = Assigner.Typing.DYNAMIC) Object returnValue,
                                  @Advice.Thrown Throwable throwable,
                                  @Advice.Local("otelSpan") Span span,
                                  @Advice.Enter Scope scope) {
            System.out.println("EXIT METHOD");

            scope.close();
            span.setAttribute("return-value", returnValue.toString());
            span.end();
        }
    }
}

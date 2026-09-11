package erplite.ia.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

@Aspect
@Component
public class AIObserverAspect {

    //@Before("@annotation(AIObserver)") //Antes de la ejecucion del metodo
    @Around("@annotation(AIObserver)") //Durante la ejecucion del metodo
    //@After("@annotation(AIObserver)") //Despues la ejecucion del metodo
    public Object observe(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();

        String agentValue = "unknown";
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getName().equals("agent")) {
                agentValue = String.valueOf(args[i]);
                break;
            }
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedMs = System.currentTimeMillis() - start;

         IO.println("[AIObserver] Method: " + signature.getName()
                + " | agent = " + agentValue
                + " | execution time = " + elapsedMs + " ms");

        return result;
    }
}

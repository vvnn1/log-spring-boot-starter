package com.github.martvey.log.aspect;

import com.github.martvey.log.anatition.LogProperties;
import com.github.martvey.log.anatition.MethodLog;
import com.github.martvey.log.common.ThrConsumer;
import com.github.martvey.log.config.MethodLogConfigurerAdapter;
import com.github.martvey.log.interceptor.LogInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author vvnn1_
 * @version 1.0
 * @date 2020/9/10 10:44
 */
public class LogAspect implements LogInterceptorRegistry,LogContextVariablesRegistry, MethodInterceptor {
    private final static TemplateParserContext templateParserContext = new TemplateParserContext();
    private final Map<Method, MethodLog> methodAnnotationCache = new ConcurrentHashMap<>();
    private List<LogInterceptor> interceptorList = Collections.emptyList();
    private Supplier<Map<String, Object>> contextVariable = Collections::emptyMap;
    private static final String RESULT_PARAM = "result";
    private static final String EXCEPTION_PARAM = "error";
    private static final String PROPERTIES_PARAM = "properties";

    public LogAspect(MethodLogConfigurerAdapter configurer) {
        configurer.registryLogInterceptor(this);
        configurer.registryContextVariables(this);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object rootObject = invocation.getThis();
        Object[] arguments = invocation.getArguments();
        Method method = invocation.getMethod();

        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(rootObject, method, arguments, new DefaultParameterNameDiscoverer());
        evaluationContext.setVariables(contextVariable.get());
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

        MethodLog methodLog = methodAnnotationCache.computeIfAbsent(method, this::getMethodLogAnnotation);
        Boolean shouldDoLog = shouldDoLog(methodLog.condition(), spelExpressionParser, evaluationContext);
        if (!shouldDoLog){
            return invocation.proceed();
        }

        Object result;
        try {
            try {
                doLog(methodLog.before(), methodLog.properties(), spelExpressionParser, evaluationContext, LogInterceptor::doBeforeLog);
                result = invocation.proceed();
            } finally {
                doLog(methodLog.after(), methodLog.properties(), spelExpressionParser, evaluationContext, LogInterceptor::doAfterLog);
            }
            evaluationContext.setVariable(RESULT_PARAM,result);
            doLog(methodLog.afterReturning(), methodLog.properties(), spelExpressionParser, evaluationContext, LogInterceptor::doAfterReturningLog);
        } catch (Throwable throwable) {
            evaluationContext.setVariable(EXCEPTION_PARAM,throwable);
            doLog(methodLog.afterThrowing(), methodLog.properties(), spelExpressionParser, evaluationContext, LogInterceptor::doAfterThrowingLog);
            throw throwable;
        }
        return result;
    }

    private Boolean shouldDoLog(String condition, SpelExpressionParser spelExpressionParser, MethodBasedEvaluationContext evaluationContext){
        if (StringUtils.isEmpty(condition)) {
            return false;
        }
        if ("true".equalsIgnoreCase(condition) || "false".equalsIgnoreCase(condition)){
            return Boolean.parseBoolean(condition);
        }
        return spelExpressionParser.parseExpression(condition, templateParserContext).getValue(evaluationContext, Boolean.class);
    }

    private String resolveMessage(String messageFormat, SpelExpressionParser spelExpressionParser, MethodBasedEvaluationContext evaluationContext){
        if (StringUtils.isEmpty(messageFormat)) {
            return "";
        }
        if (!messageFormat.contains(templateParserContext.getExpressionPrefix())
                || !messageFormat.contains(templateParserContext.getExpressionSuffix())){
            return messageFormat;
        }
        return spelExpressionParser.parseExpression(messageFormat, templateParserContext).getValue(evaluationContext, String.class);
    }

    public void addLogInterceptor(LogInterceptor logInterceptor) {
        if (CollectionUtils.isEmpty(this.interceptorList)) {
            this.interceptorList = new LinkedList<>();
        }
        this.interceptorList.add(logInterceptor);
    }

    @Override
    public void addLogInterceptor(List<LogInterceptor> logInterceptorList) {
        if (CollectionUtils.isEmpty(this.interceptorList)) {
            this.interceptorList = new LinkedList<>();
        }
        this.interceptorList.addAll(logInterceptorList);
    }

    @Override
    public void setLogVariables(Supplier<Map<String, Object>> contextVariable) {
        this.contextVariable = contextVariable;
    }



    private void doLog(String message, LogProperties[] properties,
                             SpelExpressionParser spelExpressionParser, MethodBasedEvaluationContext evaluationContext,
                             ThrConsumer<LogInterceptor, String, Map<String, String>> consumer){
        Map<String, String> logProperties = resolveProperties(properties, spelExpressionParser, evaluationContext);
        evaluationContext.setVariable(PROPERTIES_PARAM, properties);

        String resolveMessage = resolveMessage(message, spelExpressionParser, evaluationContext);

        for (LogInterceptor logInterceptor : interceptorList) {
            consumer.accept(logInterceptor, resolveMessage, logProperties);
        }
    }



    private Map<String, String> resolveProperties(LogProperties[] properties, SpelExpressionParser spelExpressionParser, MethodBasedEvaluationContext evaluationContext){
        Map<String, String> propertyMap = new LinkedHashMap<>();
        for (LogProperties property : properties) {
            if (property.eval()) {
                propertyMap.put(property.key(), resolveMessage(property.value(), spelExpressionParser, evaluationContext));
            }else {
                propertyMap.put(property.key(), property.value());
            }
        }
        return propertyMap;
    }

    private MethodLog getMethodLogAnnotation(Method method){
        if (method.isAnnotationPresent(MethodLog.class)) {
            return method.getAnnotation(MethodLog.class);
        }

        Method superMethod = ClassUtils.getInterfaceMethodIfPossible(method);
        if (!superMethod.isAnnotationPresent(MethodLog.class)) {
            throw new RuntimeException("未标注" + Method.class + "注解");
        }

        return superMethod.getAnnotation(MethodLog.class);
    }
}

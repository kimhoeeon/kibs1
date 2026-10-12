package com.mtf.kibs.interceptor;

import com.mtf.kibs.annotation.EncryptField;
import com.mtf.kibs.util.AES256Util;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Intercepts({
        // INSERT, UPDATE 시 파라미터 세팅 전 개입
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
        // SELECT 시 결과 매핑 후 개입
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@Component
public class MyBatisEncryptInterceptor implements Interceptor {

    @Autowired
    private AES256Util aes256Util;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof ParameterHandler) {
            return handleParameter(invocation);
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            return handleResultSet(invocation);
        }
        return invocation.proceed();
    }

    // 1. 파라미터 암호화 처리
    private Object handleParameter(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Object parameterObject = parameterHandler.getParameterObject();

        if (parameterObject != null) {
            // @Param 어노테이션으로 여러 개를 Map 형태로 넘길 경우 대응
            if (parameterObject instanceof Map) {
                for (Object val : ((Map<?, ?>) parameterObject).values()) {
                    processFields(val, true);
                }
            } else {
                processFields(parameterObject, true);
            }
        }
        return invocation.proceed();
    }

    // 2. 조회 결과 복호화 처리
    private Object handleResultSet(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result instanceof List) {
            for (Object obj : (List<?>) result) {
                processFields(obj, false);
            }
        } else if (result != null) {
            processFields(result, false);
        }
        return result;
    }

    // DTO 내부를 스캔하여 @EncryptField 찾아 암호화/복호화 실행
    private void processFields(Object obj, boolean isEncrypt) throws IllegalAccessException {
        if (obj == null || obj instanceof String || obj instanceof Number || obj instanceof Boolean) return;

        Class<?> clazz = obj.getClass();
        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(EncryptField.class) && field.getType().equals(String.class)) {
                    field.setAccessible(true);
                    String value = (String) field.get(obj);
                    if (value != null && !value.isEmpty()) {
                        String newValue = isEncrypt ? aes256Util.encrypt(value) : aes256Util.decrypt(value);
                        field.set(obj, newValue);
                    }
                }
            }
            clazz = clazz.getSuperclass(); // 상속받은 부모 클래스의 필드까지 모두 검사
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
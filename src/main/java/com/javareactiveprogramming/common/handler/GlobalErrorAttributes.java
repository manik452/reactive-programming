package com.javareactiveprogramming.common.handler;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest serverRequest, ErrorAttributeOptions options) {

        Map<String, Object> errorMap = new HashMap<>();

        Throwable error = getError(serverRequest);
        errorMap.put("message", error.getMessage());
        errorMap.put("endpoint url", serverRequest.path());

        return errorMap;

    }

}

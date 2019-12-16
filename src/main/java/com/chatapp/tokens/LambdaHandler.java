//package com.chatapp.tokens;
//
//import com.amazonaws.serverless.exceptions.ContainerInitializationException;
//import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//public class LambdaHandler implements RequestStreamHandler {
//
//    private static SpringBootLambdaContainerHandler handler;
//
//    static {
//        try {
//            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
//        } catch (ContainerInitializationException e) {
//            // if we fail here. We re-throw the exception to force another cold start
//            e.printStackTrace();
//            throw new RuntimeException("Could not initialize Spring Boot application", e);
//        }
//    }
//
//    @Override
//    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
//        handler.proxyStream(input, output, context);
//    }
//
//}
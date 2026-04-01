package com.teckro.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestWatcher;

public class TestResultLogger implements TestWatcher, TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        context.getTestInstance()
            .filter(instance -> instance instanceof FailureCapture)
            .map(instance -> (FailureCapture) instance)
            .ifPresent(FailureCapture::markFailed);
        throw throwable;
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.println("========================================");
        System.out.println("  PASSED: " + context.getDisplayName());
        System.out.println("========================================");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.println("========================================");
        System.out.println("  FAILED: " + context.getDisplayName());
        System.out.println("  REASON: " + cause.getMessage());
        System.out.println("========================================");
    }
}

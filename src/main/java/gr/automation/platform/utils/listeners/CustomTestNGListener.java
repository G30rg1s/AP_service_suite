package gr.automation.platform.utils.listeners;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class CustomTestNGListener extends TestListenerAdapter {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("=== Starting TestNG: " + context.getName() + " ===");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("=== Executing: "
                + result.getTestClass().getRealClass().getSimpleName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("=== PASSED: "
                + result.getTestClass().getRealClass().getSimpleName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("=== FAILED: "
                + result.getTestClass().getRealClass().getSimpleName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("=== SKIPPED: "
                + result.getTestClass().getRealClass().getSimpleName());
    }
}
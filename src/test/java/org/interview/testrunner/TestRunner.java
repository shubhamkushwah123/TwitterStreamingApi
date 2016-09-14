package org.interview.testrunner;


import org.interview.test.TestOAuth;
import org.interview.test.TestTwitterStream;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
	
public static void main(String args[]){
		
		Result result = JUnitCore.runClasses(TestTwitterStream.class,TestOAuth.class);
		
		for(Failure failure : result.getFailures())
			{
			System.out.println(failure.toString());
			}
		
		System.out.println(result.wasSuccessful());
	
		/* TestSuite suite = new TestSuite(JunitTestRunner.class);
	      TestResult result = new TestResult();
	      suite.run(result);
	      System.out.println("Number of test cases = " + result.runCount());
	*/
	}

}

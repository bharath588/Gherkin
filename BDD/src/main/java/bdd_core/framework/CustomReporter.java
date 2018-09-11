package bdd_core.framework;


import com.aventstack.extentreports.Status;

import bdd_reporter.Reporter;
import gherkin.formatter.model.Result;
import cucumber.runtime.formatter.CucumberJSONFormatter;

public class CustomReporter extends CucumberJSONFormatter  {

	public CustomReporter(Appendable out) {
		super(out);
		// TODO Auto-generated constructor stub
	}
	@Override
   public void result(Result result) {
        if (result.getError()!=null) {
        	Reporter.logEvent(Status.FAIL, "runtime exception occured",result.getErrorMessage(), true);
        }
       // Result myResult = new Result(result.status, result.duration, errorMessage);
        // Log the truncated error to the JSON report
        super.result(result);
    }

}

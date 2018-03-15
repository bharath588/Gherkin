/**
 * 
 */
package testrunner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * @author srsksr
 *
 */
@CucumberOptions(
features="feature/PPTWeb_Investments_End_To_End_TestCase.feature",
glue={"stepDefination"},
monochrome = true,
tags = {"@Login,@LeftNav,@ChooseIndividualFunds,@VerifyUpdatedIndividualFunds"})
public class TestRunnerPPTWEB extends AbstractTestNGCucumberTests {

}

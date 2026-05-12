package br.com.fiap.mvp.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.com.fiap.mvp.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/html/index.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        }
)
public class CucumberTestRunner {
}

package io.github.jhipster.application.micro01.cucumber.stepdefs;

import io.github.jhipster.application.micro01.JhipMicroserviceGway01App;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = JhipMicroserviceGway01App.class)
public abstract class StepDefs {

    protected ResultActions actions;

}

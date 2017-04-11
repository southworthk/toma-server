package com.usana.autotom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerrysouthworth on 12/30/16.
 */

@SpringBootApplication
@EnableNeo4jRepositories
public class App
{
    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner demo(TomElementRepository tomElementRepository){
        return args -> {
            WebDriver driver = new FirefoxDriver();

            BaseURL baseURL = new BaseURL("Hub Login","https://www.usanatest.com/hub/");
            tomElementRepository.save(baseURL);
            Form hubLogin = new Form("hubLoginForm");
            tomElementRepository.save(hubLogin);
            baseURL.launches(hubLogin);
            tomElementRepository.save(baseURL);
            InputElement username = new InputElement("username");
            username.setMaxlength(100);
            username.setTagId("username");
            username.setType("text");
            tomElementRepository.save(username);
            InputElement password = new InputElement("password");
            password.setMaxlength(32);
            password.setTagId("password");
            password.setType("text");
            tomElementRepository.save(password);
            InputElement submit = new InputElement("submit");
            submit.setDescription("hubLoginForm submit");
            submit.setType("submit");
            tomElementRepository.save(submit);
            hubLogin.contains(username);
            hubLogin.contains(password);
            hubLogin.contains(submit);
            tomElementRepository.save(hubLogin);

            InfoPopup welcomeModule = new InfoPopup("Welcome Popup");
            welcomeModule.setKey("close");
            tomElementRepository.save(welcomeModule);

            Page hubLandingPage = new Page("hubLandingPage");
            tomElementRepository.save(hubLandingPage);
            hubLogin.launches(welcomeModule);
            tomElementRepository.save(hubLogin);
            welcomeModule.launches(hubLandingPage);
            tomElementRepository.save(welcomeModule);

            Sidebar sidebar = new Sidebar("Hub Landing Page Sidebar","left");
            Hyperlink enroll = new Hyperlink("Enroll","/frame/shopSecureForward.jsp?url=oe");
            tomElementRepository.save(enroll);
            sidebar.contains(enroll);
            tomElementRepository.save(sidebar);
            hubLandingPage.contains(sidebar);
            tomElementRepository.save(hubLandingPage);
            Form oeStartForm = new Form("oeStartForm");
            tomElementRepository.save(oeStartForm);
            enroll.launches(oeStartForm);
            tomElementRepository.save(enroll);

            SelectList country = new SelectList("Country");
            country.setTagId("countryCode");
            country.setValue("CA");
            tomElementRepository.save(country);
            SelectList accountType = new SelectList("Account Type");
            accountType.setTagId("customerType");
            accountType.setValue("D"); // Distributor
            tomElementRepository.save(accountType);
            SelectList language = new SelectList("Language Preference");
            language.setTagId("languageSelected");
            language.setValue("ENU");
            tomElementRepository.save(language);
            Button enrollSubmit = new Button("Online Enrollment","oeStartSubmit");
            tomElementRepository.save(enrollSubmit);

            oeStartForm.contains(country);
            oeStartForm.contains(accountType);
            oeStartForm.contains(language);
            oeStartForm.contains(enrollSubmit);
            tomElementRepository.save(oeStartForm);


            // simulate the selection of a customer based on user criteria
            Customer customer = new Customer();
            customer.setUsername("78867");
            customer.setPassword("spartan-117");
            customer.setCustomerType("D");

            // Query for the testing path and put the relevant elements in the testableElements array
            ArrayList<TomElement> testableElements = new ArrayList<>();

            List<TomElement> elements = tomElementRepository.getShortestPath();



            Form form;
            for(TomElement element : elements){
                if(element instanceof BaseURL){
                    BaseURL myBaseURL = new BaseURL(((BaseURL) element).getName(),((BaseURL) element).getUrl());
                    testableElements.add(myBaseURL);
                }
                if(element instanceof Hyperlink){
                    Hyperlink hyperlink = new Hyperlink(((Hyperlink) element).getName(),((Hyperlink) element).getHref());
                    testableElements.add(hyperlink);
                }
                if(element instanceof InfoPopup){
                    InfoPopup welcomePopup = new InfoPopup("Welcome Module");
                    welcomePopup.setKey(((InfoPopup) element).getKey());
                    testableElements.add(welcomePopup);
                }
                if(element instanceof Form){
                    form = new Form(((Form) element).getName());
                    // get related input elements
                    boolean hasElements = false;
                    String formName = form.getName();
                    if(formName.equals("hubLoginForm"))
                    {
                        List<InputElement> formElements = form.getInputElements(tomElementRepository);
                        for (InputElement formElement : formElements)
                        {
                            hasElements = true;
                            if (formElement.getType().equals("text"))
                            {
                                InputElement textElement = new InputElement(formElement.getName());
                                textElement.setKey(formElement.getTagId());
                                if (formElement.getTagId().equals("username"))
                                {
                                    textElement.setValue(customer.getUsername());
                                    testableElements.add(textElement);
                                }
                                else if (formElement.getTagId().equals("password"))
                                {
                                    textElement.setValue(customer.getPassword());
                                    testableElements.add(textElement);
                                }

                            }
                        }
                        // add submit last
                        if (hasElements)
                            testableElements.add(form);
                    } else if(formName.equals("oeStartForm")){
                        List<SelectList> formElements = form.getSelectElements(tomElementRepository);
                        for (SelectList formElement : formElements)
                        {
                            hasElements = true;
                            SelectList selectList = new SelectList(formElement.getName());
                            selectList.setTagId(formElement.getTagId());
                            selectList.setValue(formElement.getValue());
                            testableElements.add(selectList);
                        }
                        if(hasElements){
                            Button olButton = form.getFormButton(tomElementRepository);
                            Button button = new Button(olButton.getName(),olButton.getTagId());
                            testableElements.add(button);
                        }

                    }
                }
            }

            // execute the tests
            for(TomElement tomElement : testableElements){
                tomElement.execute(driver);
            }

            //driver.close();
        };


    }

}


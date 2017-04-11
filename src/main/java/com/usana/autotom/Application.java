package com.usana.autotom;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kerrysouthworth on 12/30/16.
 */

@SpringBootApplication
@EnableNeo4jRepositories
public class Application
{
    private final static Logger log = LoggerFactory.getLogger(Application.class);
    public static TomElementRepository tomElementRepository;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner demo(TomElementRepository tomElementRepository){
        return args -> {
        this.tomElementRepository = tomElementRepository;

            // set up the ESC graph
            //setupESCGraph();

            // set up the Hub graph
            //setupHubGraph();

            // simulate the selection of a customer based on user criteria

            //driver.close();
            executeTest();
        };

    }

    /**
     *  this method is called by the REST controller
     */
    public static void executeTest(){
        Customer customer = new Customer();
        customer.setUsername("dev");
        customer.setPassword("dev");
        //customer.setCustomerType("D");

        // Query for the testing path and put the relevant elements in the testableElements array
        ArrayList<TomElement> testableElements = new ArrayList<>();
        ArrayList <ValidInput> customerArr = new ArrayList<>();

        ArrayList<TomElement> elements = tomElementRepository.getShortestPath();
        TomElementIdSort tomElementIdSort = new TomElementIdSort();
        Collections.sort(elements,tomElementIdSort);

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
                if(formName.equals("escLoginForm")) // complete
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
                        testableElements.add(new Button("Login Button", "loginButton"));

                } else if(formName.equals("searchContainer")) // complete
                {
                    List<InputElement> formElements = form.getInputElements(tomElementRepository);
                    hasElements = false;

                    for (InputElement formElement : formElements)
                    {
                        hasElements = true;
                        if (formElement.getType().equals("text"))
                        {
                            InputElement textElement = new InputElement(formElement.getName());
                            textElement.setKey(formElement.getTagId());
                            if (formElement.getTagId().equals("headerCustomerIdSearchInput"))
                            {
                                ValidInput input  = textElement.getRandomValidInput(tomElementRepository);
                                textElement.setValue(input.getIdentifier());
                                testableElements.add(textElement);

                                // also create an array of customers that can be used to launch multiple threads
                                customerArr = (ArrayList<ValidInput>) textElement.getValidInputs(tomElementRepository);
                            }
                        }
                    }
                    // add submit last
                    if (hasElements)
                        testableElements.add(new Button("Go Button", "loadCust"));

                } else if(formName.equals("mlmSettings")) // complete
                {
                    List<SelectList> formElements = form.getSelectElements(tomElementRepository);
                    for (SelectList formElement : formElements)
                    {
                        hasElements = true;
                        SelectList selectList = new SelectList(formElement.getName());
                        selectList.setTagId(formElement.getTagId());
                        selectList.setValue(formElement.getValue());
                        selectList.setIndex(formElement.getIndex());
                        testableElements.add(selectList);

                    }
                    if(hasElements){
                        Button olButton = form.getFormButton(tomElementRepository);
                        Button button = new Button(olButton.getName(),olButton.getTagId());
                        testableElements.add(button);
                        Wait wait = new Wait(5);
                        testableElements.add(wait);
                    }

                } else if(formName.equals("Products")){
                    List<InputElement> formElements = form.getInputElements(tomElementRepository);
                    hasElements = false;

                    for (InputElement formElement : formElements)
                    {
                        hasElements = true;
                        if (formElement.getType().equals("text"))
                        {
                            InputElement textElement = new InputElement(formElement.getName());
                            textElement.setKey(formElement.getTagId());
                            if (formElement.getTagId().equals("sku"))
                            {
                                // get multiple items to test
                                ArrayList<ValidInput> validInputs = (ArrayList<ValidInput>) textElement.getValidInputs(tomElementRepository);
                                for (ValidInput input: validInputs)
                                {
                                    ValidInput product = new ValidInput("Product",input.getIdentifier());
                                    product.setKey("sku");
                                    testableElements.add(product);
                                    Wait wait = new Wait(2);
                                    testableElements.add(wait);
                                }
                            }
                        }
                    }

                    // add submit
                    if(hasElements){
                        Button olButton = form.getFormButton(tomElementRepository);
                        Button button = new Button(olButton.getName(),olButton.getTagId());
                        testableElements.add(button);
                    }
                } else if(formName.equals("Payment Information")){
/*                    List<SelectList> formElements = form.getSelectElements(tomElementRepository);
                    for (SelectList formElement : formElements)
                    {
                        hasElements = true;
                        SelectList selectList = new SelectList(formElement.getName());
                        selectList.setTagId(formElement.getTagId());
                        selectList.setValue(formElement.getValue());
                        selectList.setIndex(formElement.getIndex());
                        testableElements.add(selectList);
                    }
                    */
                    Button button = new Button("Cash/Travelers Cheque Button","pymtCashBtn");
                    testableElements.add(button);
                    InputElement tendered = new InputElement("Cash Tendered");
                    tendered.setType("text");
                    tendered.setTagId("cashTenderedInput");
                    tendered.setKey("cashTenderedInput");
                    tendered.setValue("1000");
                    testableElements.add(tendered);
                    Button cashSubmit = new Button("Submit Cash","cashSubmitBtn");
                    testableElements.add(cashSubmit);
                    PrintDialog print = new PrintDialog("Print Receipt","print-cancel");
                    testableElements.add(print);
                } else if(formName.equals("XYZ")){
                    Button button = new Button("Cash/Travelers Cheque Button","pymtCashBtn");
                    testableElements.add(button);
                    InputElement tendered = new InputElement("Cash Tendered");
                    tendered.setType("text");
                    tendered.setTagId("cashTenderedInput");
                    tendered.setValue("1000");
                    testableElements.add(tendered);
                    Button cashSubmit = new Button("Submit Cash","cashSubmitBtn");
                    testableElements.add(cashSubmit);
                    //PrintDialog print = new PrintDialog("Print Receipt","print-cancel");
                    //testableElements.add(print);
                }
            }
        }

        //executeTestThreads(testableElements, customerArr);
        executeSingleThreadTest(testableElements);

    }

    private static void executeSingleThreadTest(ArrayList<TomElement> testableElements)
    {
        WebDriver driver = new FirefoxDriver();

        // execute the tests
        int totalTests = testableElements.size();
        int counter = 1;
        int success = 0;
        for(TomElement tomElement : testableElements){
            long startTime = System.currentTimeMillis();
            try
            {
                tomElement.execute(driver);
                success++;
            }catch (Exception e){
                System.out.println("Failure: "+e.getMessage());
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Test "+counter+" of "+totalTests+": "+tomElement.getName()+", duration: "+(endTime-startTime)+" ms, uuid: "+tomElement.getUuid());
            counter++;
            // TODO: make sure each element is assigned an ID so that you can do comparisons from one trial to another

            // TODO: write test results to Firebase
            // see http://pscode.rs/firebase-and-spring-boot-integration/
            // github repository is here: https://github.com/savicprvoslav/Spring-Boot-starter
        }
        System.out.println("Total tests: "+totalTests+", successful: "+success+", failures: "+(totalTests-success));
        driver.close();
    }

    private static void executeTestThreads(ArrayList<TomElement> testableElements, ArrayList<ValidInput> customerArr)
    {
        ExecutionThread et = new ExecutionThread();
        et.setTestableElements(testableElements);
        for(ValidInput vi: customerArr)
        {
            Thread t = new Thread(et);
            t.setName(vi.getIdentifier()); // customer id
            t.start();
        }

    }

    private void setupTest(){
        Customer customer = new Customer();
        customer.setUsername("dev");
        customer.setPassword("dev");
        ArrayList<TomElement> testableElements = new ArrayList<>();

        List<TomElement> elements = tomElementRepository.getShortestPath();
        TomElementIdSort tomElementIdSort = new TomElementIdSort();
        Collections.sort(elements, tomElementIdSort);

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
                if(formName.equals("escLoginForm"))
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

                } else if(formName.equals("bciForm")){
                    List<SelectList> formElements = form.getSelectElements(tomElementRepository);
                    for (SelectList formElement : formElements)
                    {
                        hasElements = true;
                        SelectList selectList = new SelectList(formElement.getName());
                        selectList.setTagId(formElement.getTagId());
                        selectList.setValue(formElement.getValue());
                        testableElements.add(selectList);
                    }
                }
            }
        }

        SelectList languageList = new SelectList("Primary Spoken Language");
        languageList.setTagId("primarySpokenLanguage_en");
        languageList.setValue("ENU");
        testableElements.add(languageList);

    }

    private void setupESCGraph(){
        BaseURL escBaseURL = new BaseURL("ESC Login","https://prjf.node.env.uhs/");
        //BaseURL escBaseURL = new BaseURL("ESC Login","http://odyssey.usanadev.com/");
        tomElementRepository.save(escBaseURL);
        Form escLogin = new Form("escLoginForm");
        tomElementRepository.save(escLogin);
        escBaseURL.launches(escLogin);
        tomElementRepository.save(escBaseURL);
        InputElement escUsername = new InputElement("username");
        escUsername.setMaxlength(100);
        escUsername.setTagId("username");
        escUsername.setType("text");
        tomElementRepository.save(escUsername);
        InputElement escPassword = new InputElement("password");
        escPassword.setMaxlength(32);
        escPassword.setTagId("password");
        escPassword.setType("text");
        tomElementRepository.save(escPassword);
        Button loginSubmit = new Button("ESC Login","loginButton");
        tomElementRepository.save(loginSubmit);
        escLogin.contains(escUsername);
        escLogin.contains(escPassword);
        escLogin.contains(loginSubmit);
        tomElementRepository.save(escLogin);

        Form searchContainer = new Form("searchContainer");
        tomElementRepository.save(searchContainer);
        escLogin.launches(searchContainer);
        tomElementRepository.save(escLogin);
        InputElement customerId = new InputElement("customerId");
        customerId.setMaxlength(100);
        customerId.setTagId("headerCustomerIdSearchInput");
        customerId.setType("text");
        customerId.accepts(new ValidInput("customerId","78867"));
        // add valid inputs for customer id
        String[] customers = {"197317", "219325", "2112141", "7725600", "8450678", "5080432", "8073724", "7400120", "6000700",
                "7081926", "6282356", "7734544", "7939483", "9199643", "6190403", "7097931", "8346533", "5165949", "8403329",
                "6557589", "7197567", "5616441", "6138083", "5839385"};
        for(String customer: customers)
        {
            ValidInput valid = new ValidInput("Customer",customer);
            tomElementRepository.save(valid);
            customerId.accepts(valid);
            tomElementRepository.save(customerId);
        }

        tomElementRepository.save(customerId);
        Button goButton = new Button("Go Button","loadCust");
        tomElementRepository.save(goButton);
        searchContainer.contains(customerId);
        searchContainer.contains(goButton);
        tomElementRepository.save(searchContainer);

        Form mlmSettings = new Form("mlmSettings");
        tomElementRepository.save(mlmSettings);
        searchContainer.launches(mlmSettings);
        tomElementRepository.save(searchContainer);

        SelectList businessCenter = new SelectList("Business Center");
        businessCenter.setTagId("mlmBusinessCenterSelect");
        businessCenter.setValue("2: Object");
        businessCenter.setIndex(1);
        tomElementRepository.save(businessCenter);
        SelectList sideSelect = new SelectList("Placement Side");
        sideSelect.setTagId("mlmPlacementSideSelect");
        businessCenter.setValue("2: Object");
        sideSelect.setIndex(1);
        tomElementRepository.save(sideSelect);
        Button nextButton = new Button("NextButton Button","mlmNextButton");
        tomElementRepository.save(nextButton);

        mlmSettings.contains(businessCenter);
        mlmSettings.contains(sideSelect);
        mlmSettings.contains(nextButton);
        tomElementRepository.save(mlmSettings);

        Form addProductPage = new Form("Products");
        tomElementRepository.save(addProductPage);
        mlmSettings.launches(addProductPage);
        tomElementRepository.save(mlmSettings);

        InputElement searchSku = new InputElement("Search Sku");
        searchSku.setTagId("sku");
        searchSku.setType("text");
        searchSku.setMaxlength(50);
        tomElementRepository.save(searchSku);
        String[] products = {"133.080101", "130.080100", "120.080102", "111.080102", "131.080101", "135.080102", "134.080101", "122.080101", "108.080102", "124.080100"};
        for(String product: products)
        {
            ValidInput valid = new ValidInput("Product", product);
            tomElementRepository.save(valid);
            searchSku.accepts(valid);
            tomElementRepository.save(searchSku);
        }

        Button orderNextBtn = new Button("Order Next Button","orderNextButton");
        tomElementRepository.save(orderNextBtn);
        addProductPage.contains(searchSku);
        addProductPage.contains(orderNextBtn);
        tomElementRepository.save(addProductPage);

        Form paymentInfo = new Form("Payment Information");
        tomElementRepository.save(paymentInfo);
        addProductPage.launches(paymentInfo);
        tomElementRepository.save(addProductPage);

        SelectList selectPayment = new SelectList("Select Payment");
        selectPayment.setTagId("pymtCofSelect");
        selectPayment.setIndex(2);
        tomElementRepository.save(selectPayment);
        paymentInfo.contains(selectPayment);
        tomElementRepository.save(paymentInfo);

        Form savedPaymentMethod = new Form("Saved Payment Method");
        tomElementRepository.save(savedPaymentMethod);
        paymentInfo.launches(savedPaymentMethod);
        tomElementRepository.save(paymentInfo);
        Button btnSuccess = new Button("COF Submit Button","cofSubmitBtn");
        tomElementRepository.save(btnSuccess);
        savedPaymentMethod.contains(btnSuccess);
        tomElementRepository.save(savedPaymentMethod);

        // Print dialog
        Form printDialog = new Form("Print Dialog");
        tomElementRepository.save(printDialog);
        Button printCancelButton = new Button("Print Cancel Button","");
        savedPaymentMethod.launches(printDialog);
        tomElementRepository.save(savedPaymentMethod);

        Form orderCompleted = new Form("Order Completed");
        tomElementRepository.save(orderCompleted);
        Button orderCompltdButton = new Button("Finish and Clear", "completeOrderBtn");
        tomElementRepository.save(orderCompltdButton);
        orderCompleted.contains(orderCompltdButton);
        tomElementRepository.save(orderCompleted);
        printDialog.launches(orderCompleted);
        tomElementRepository.save(printDialog);

        // loop through another customer ID

    }

    private void setupHubGraph(){
        BaseURL baseURL = new BaseURL("Hub Login","https://www.usanabeta.com/hub/");
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
    }

}

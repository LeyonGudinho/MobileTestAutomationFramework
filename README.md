# MobileTestAutomationFramework

## Pre-requesities:
1. Make sure you have Node.js and appium node mudule installed
    
    `npm install -g appium`
2. Setup your test in testNG.xml by providing required desired capabilitis as parameters
    e.g.
    
```
<parameter name="emulator" value="false" />
<parameter name="port" value= "4723"></parameter>
<parameter name="platformName" value="Android" />
<parameter name="udid" value="192.168.0.104:5555" />
<parameter name="deviceName" value="OnePlus 9 pro" />
<parameter name="systemPort" value="10002" />
<parameter name="chromeDriverPort" value="11012" />
```

## Test Input Source
1. You can either provide the TestData in the TestDataFile.xlsx but simplying adding a new sheet and naming it exactly the same name as the respective @test Method and use the data provider which will pass the excel data directly to the test
2. You can mention the testData in the properties file created by the test method name then just use `loadProperties();` (with any params) and `getProperty();`

## How to run the tests
- You can either run the **testNG.xml**
    
    or
- Open the command line in the project directory and execute **mvn test** command

## Running Tests in Parallel
You can mentioned multiple test blocks with different devices configurations in the testNG.xml and it will start running on each device in a parallel mode

## Screenshots and Video Recording
Each screenshot and video recording for @test for each device and each session is stored in Screenshot and Video folder

    videos ->   device name -> data time -> testMethod name
    
## Reporting
Test run results are captured in extend reports along with screnshot or each page and assertion performed (using custom Assertion)
You can find the latest report in the project root directory as file named 'ExtentReport.html'
Moreover, past reports are archived under reports folder

# MobileTestAutomationFramework

Pre-requesities:
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

How to run the tests
- You can either run the **testNG.xml**
    
    or
- Open the command line in the project directory and execute **mvn test** command

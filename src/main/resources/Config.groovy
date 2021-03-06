url = 'http://stackoverflow.com'

// the setting is useful if we need to take a screenshot after every action
take_screenshot = true

// can turn off logging completely or set it to a particular level.
LOGROOTLEVEL = System.getProperty('LOGROOTLEVEL', 'DEBUG')

// timeout for implicit and webdriverwait
WEBDRIVERWAIT_TIMEOUT = 30
IMPLICITWAIT_TIMEOUT = 10
// webdriverwait polling timeout in milliseconds
WEBDRIVERWAIT_POLL = 10

def credentials = new Properties()
try {
	InputStream propFile = new FileInputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"credentials.properties")
	credentials.load(propFile)
} catch(IOException | Exception e){
	System.out.println("Encountered exception " + e + " while loading the credentials properties file!!!\n Please check whether the file" +
			" exists in the path: " + System.getProperty("user.dir"))
	e.printStackTrace()
}

seleniumConfigs {
	local {
		browser = System.getProperty("BROWSER",'firefox')
	}
	remote {
		ip = System.getProperty("SELENIUM_HOST", "localhost")
		port = Integer.valueOf(System.getProperty("SELENIUM_PORT", "4444"))
		browser = System.getProperty("BROWSER",'firefox')
		version = System.getProperty('SELENIUM_VERSION', '42')
		platform = System.getProperty('SELENIUM_PLATFORM', 'ANY')
	}
	mobile {
		ip = System.getProperty("SELENIUM_HOST", "localhost")
		port = Integer.valueOf(System.getProperty("SELENIUM_PORT", "4723"))
        mobile_platform = System.getProperty('MOBILE_PLATFORM', 'android')
        android {
            browser = System.getProperty("BROWSER", 'Browser')
            platform = 'Android'
            deviceName = 'Android Emulator'
            platformVersion = '4.4.2'
        }
        ios {
            deviceType = System.getProperty('DEVICETYPE', 'iphone')
            iphone {
                browser = System.getProperty("BROWSER", 'Safari')
                platform = 'iOS'
                deviceName = 'iPhone Emulator'
                platformVersion = '8.1'
            }
            ipad {
                browser = System.getProperty("BROWSER", 'Safari')
                platform = 'iOS'
                deviceName = 'iPad Emulator'
                platformVersion = '8.1'
            }
        }
	}
	sauceLabs {
		// the various Sauce properties are introduced by the SauceLabs
		// onDemand jenkins plugin. Using these to set the desired properties/capabilities
		// Set default values if the properties are not supplied.
		userName = credentials.getProperty('SAUCE_USERNAME', 'sandeep-singh-79')
		accessKey = credentials.getProperty('SAUCE_ACCESS_KEY')
		os = System.getProperty('SELENIUM_PLATFORM', 'Windows 8')
		browser = System.getProperty("BROWSER",'firefox')
		browserVersion = System.getProperty('SELENIUM_VERSION', '42')
		onDemand {
			server = 'ondemand.saucelabs.com'
			port = '80'
		}
	}
}
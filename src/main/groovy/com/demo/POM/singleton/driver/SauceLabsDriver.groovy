package com.demo.POM.singleton.driver

import com.demo.POM.singleton.exceptions.UnsupportedDriverTypeException
import groovy.transform.PackageScope
import groovy.transform.PackageScopeTarget
import groovy.util.logging.Slf4j
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

@Slf4j
@PackageScope(PackageScopeTarget.CLASS)
class SauceLabsDriver extends DriverType {
    private def userName, accessKey

    public SauceLabsDriver(String userName,
                           String accessKey,
                           String serverAddress,
                           int serverPort,
                           String platform,
                           String browser,
                           String browserVersion) {
        this.userName = userName
        this.accessKey = accessKey
        this.serverAddress = serverAddress
        this.serverPort = serverPort
        this.platform = platform
        this.browser = browser
        version = browserVersion
	}

	@Override
	WebDriver createDriver() {
        log.info("entering createDriver of %s class", this.class.simpleName)

		if(driver == null) {
            log.info("Requesting ${browser} instance")
            createCapabilities()
		} else {
			return driver
		}

        log.info("creating SauceLabsDriver instance with url: http://${userName}:${accessKey}@${serverAddress}:${serverPort}/wd/hub")
        log.info("and capabilities: ${caps.getVersion()}, ${caps.getPlatform()}")
		driver = new RemoteWebDriver(
                new URL("http://${userName}:${accessKey}@${serverAddress}:${serverPort}/wd/hub"), caps)

        driver
    }

    @Override
    protected def createCapabilities() {
        if (browser.equalsIgnoreCase('firefox')) {
            caps = DesiredCapabilities.firefox()
        } else if (browser.equalsIgnoreCase('chrome')) {
            caps = DesiredCapabilities.chrome()
        } else if (browser.contains('internet')) {
            caps = DesiredCapabilities.internetExplorer()
            caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true)
        } else if (browser.equalsIgnoreCase('safari')) {
            caps = DesiredCapabilities.safari()
        } else {
            log.error("Unsupported browser type: ${browser}. Throwing exception")
            throw new UnsupportedDriverTypeException("unsupported browser type: ${browser}")
        }

        caps.version = version
        caps.platform = Platform.fromString(platform)
    }
}

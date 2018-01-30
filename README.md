# rest-automation
# REST API automation - (rest-assured)


# JDK8U152
*****************************
- http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
- JAVA 1.8_152 !!!!!!!


vi .zshrc
# Path to JAVA_HOME
- export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_152.jdk/Contents/Home
- export PATH=$PATH:$JAVA_HOME



# Certificat JVM
*******************
- HowTo:
- https://confluence.atlassian.com/kb/how-to-import-a-public-ssl-certificate-into-a-jvm-867025849.html


- 1.Run program called: portecle.jnlp
- 2.Select the Examine menu and then click Examine SSL/TLS Connection:
- 3.Enter the SSL Host and Port of the target system:
- 4.Wait for it to load, then select the public certificate and click on PEM:
- 5.Export the certificate and save it.
6. Go back to the main screen and select the Open an existing keystore from disk option, select cacerts (for example $JAVA_HOME/lib/security/cacerts) then enter the password (the default is: changeit).
- 7. Select the Import a trusted certificate into the loaded keystore button:
- 8.Select the certificate that was saved in step 6 and confirm that you trust it, giving it an appropriate alias (e.g.: confluence).
You may hit this error: If so, hit OK, and then accept the certificate as trusted.
- 9.Save the Key Store to disk --> Maybe is protected, then you should Save as and replace from Finder.
- 10. Restart your application.
- 11. Test that you can connect to the host.



# Set up IPs in hosts file (POSTMAN and INTELLIJ IDE uses localhost or hostname)
*******************
- PING hostname (xxx.xxx.xxx.xxx): 56 data bytes
- Terminal --->
- sudo nano /etc/hosts           
- Password:*******

- #127.0.0.1      localhost
- #PROD ENV
- #xxx.xxx.xxx.xxx  localhost

- ctrl+O  Enter (Save)
- ctrl+X  (Exit)

- sudo killall -HUP mDNSResponder (clear DNS cache)


# MAVEN
**************************
- mvn clean test -pl RESTtest_functional -am -DtestSuite=testsuite.xml
- mvn clean test -pl RESTtest_functional -am -DtestSuite=singletest.xml > ~/Documents/`date +\%Y\%m\%d\%H\%M\%S`-REST_API_Tests_running.log 2>&1


# REPORTING
*************************
IntelliJ IDEA --> Run --> Edit Configuration --> testsuit.xml --> Listeners --> Use default reporters (this should generate test-output folder with index.html report file)

# DEMO
***********************
https://www.screencast.com/t/AMrgyD1SoiVQ

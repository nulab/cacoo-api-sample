README : Cacoo API sample.

This application is a sample to understand how to implement your web application with Cacoo API.
This sample is implemented as traditional Java Servlet and JSP application. But the Cacoo API is based on web standard technology so you can develop your applications by any program language.
You have to understand OAuth (1.0) technology for authenticating Cacoo API. If you need, please see the URL in Reference section of this README.

Please see the wire frame of this sample application.
https://cacoo.com/diagrams/sI91kTNRgAYqf4lV

#####
# Development environment of this sample.

- Java 6 or newer
- Apache Tomcat
- Eclipse 3.6 and WTP Plug-in

#####
# If you don't have Cacoo account yet, please sign-up to create Cacoo account.

#####
# Register Your Application at "https://cacoo.com/profile/apps" and get key strings.

#####
# Replace with your "Consumer Key" and "Consumer Secret" into src/com/cacoo/apisample/cacoo/CacooUtils.java

	private static final String CONSUMER_KEY = "-- Consumer Key --";
	private static final String CONSUMER_SECRET = "-- Consumer Secret --";

#####
# Add login users to your Apache Tomcat.

You can specify users to write $TOMCAT_HOME/conf/tomcat-users.xml, like below (adding user "testuser / pass" as "myapp" role).
===
<?xml version="1.0" encoding="UTF-8"?>
<tomcat-users>
  <role rolename="myapp"/>
  <user username="testuser" password="pass" roles="myapp"/>
</tomcat-users>
===
This users are different from Cacoo accounts. This users are just used to authenticate to sample application.

#####
# Build this sample and deploy into Apache Tomcat, then start it.

If you can not build sample, please fix build path for libraries, (JDK, Web Application Runtime).

#####
# Note

This sample is created to explain using Cacoo API simply. 
So some popular web application features are omitted such as reading / writing Database.
Please see "FIXME" comment in sources.

#####
# Reference

- Cacoo API
  https://cacoo.com/api

- OAuth / Wikipedia
  http://en.wikipedia.org/wiki/OAuth
  
- OAuth 1.0 Guide - Technology
  http://hueniverse.com/oauth/guide/terminology/

- oauth-signpost (OAuth library used for this sample)
  http://code.google.com/p/oauth-signpost/

:: Please enjoy Cacoo API :)
:: 12th July, 2012

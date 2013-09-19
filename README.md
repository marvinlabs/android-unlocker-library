Android Unlocker Library
========================

Provides a full system to help you develop Android applications that have a free version and some commercial unlocking application. There is also the flexibility for implementing multiple features to be unlocked by various unlocker applications. 

Getting started
---------------

This whole tutorial assumes we want to create a free application published under the package name `com.myapp` and an unlocker application published under the package name `com.myapp.unlocker`.

### Create the required projects

1. Import the "library" folder in your workspace as a library project. This is where authorization gets verified.
1. Create a library project for your application, that will be shared between your free application and the unlocker application. That project should depend on the library project you created in step 1.
1. Create an application project for the unlocker. That project should depend on the library project you created in step 2.
1. Create an application project for the free app. That project should depend on the library project you created in step 2.

### Implement the app's common library project

This is the project that will contain the code needed to unlock the features in the free application and also the code that is needed to check if the unlock application is installed or not.

1. Create a class `Configuration` in the package `com.myapp.common`. You can take the sample class provided in the sample-common project. Make sure you replace the `PACKAGE_NAME` constant with your free application's package name. In our case `com.myapp`.
1. Create an `UnlockerProvider` class in the package `com.myapp.common.provider` that will handle the authorisations. You can take the sample class provided in the sample-common project. This class should extend the `AuthorizationContentProvider` class. This is where you can define how features are made available or not.

That should be it for the code in common for the unlocker and the free application.

### Implement the unlocker application

That one is easy. In fact the unlocker application is nothing more than some icons and a few lines in the `AndroidManifest.xml` file. In that file, we request a permission to access the unlocker's content provider and we will also declare it so that it is available to our free application.

1. Add the icons in the drawable folders as usual
1. Name your application properly (usually automatically created in the strings.xml file)
1. Declare a new permission in the manifest file. You can use the sample declaration from the sample-unlocker project. Just replace the `fr.marvinlabs.samples.unlocker.AUTHORIZATION_PROVIDER` string with your own. In our case, we will name it `com.myapp.AUTHORIZATION_PROVIDER`.
1. Add the content provider declaration within the `application` tag of the manifest file. You can use the sample declaration from the sample-unlocker project. Just replace the `fr.marvinlabs.samples.unlocker.common.provider.SampleUnlockerProvider` string with your own. In our case, we will name it `com.myapp.common.provider.UnlockerProvider` and also replace the `fr.marvinlabs.samples.unlocker.AUTHORIZATION_PROVIDER` string with your own. In our case, we will name it `com.myapp.AUTHORIZATION_PROVIDER`.

In the end, your manifest file should look like that:

	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
		package="com.myapp.unlocker"
		android:versionCode="1"
		android:versionName="1.0" >

		<uses-sdk
			android:minSdkVersion="14"
			android:targetSdkVersion="18" />

		<permission
			android:name="com.myapp.AUTHORIZATION_PROVIDER"
			android:protectionLevel="signature" />

		<application
			android:allowBackup="true"
			android:icon="@drawable/ic_launcher"
			android:label="@string/app_name" >
			<provider
				android:name="com.myapp.common.provider.UnlockerProvider"
				android:authorities="fr.marvinlabs.unlocker"
				android:permission="com.myapp.AUTHORIZATION_PROVIDER" />
		</application>
	</manifest>

### Implement the free application

This will be where you will want to check if the user has installed the unlocker application. In order for that to work, you will need to:

1. Request the permission to use the unlock content provider declared in the unlocker. This is done by adding a uses-permission tag in the `AndroidManifest.xml` file. You can use the sample declaration from the sample-locked project. Just replace the permission name with the one you declared in the unlocker project. In our case, we could add the line `<uses-permission android:name="com.myapp.AUTHORIZATION_PROVIDER" />` within the manifest tag. That permission will not show up when the user installs the application because it is not a system-level permission, so no worries.

That's it, now you simply need to check whether the user has installed the unlocker application by using such code:

	boolean isAuthorized = UnlockerProvider.getPackageLevelAuthorization(getContentResolver());

History
-------

v1.0 (2013-09-19)

- Added intents to open the app markets

License
-------

Copyright 2013 Vincent Mimoun-Prat

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

More info
---------

- Get some more info and other Android tutorials on our blog: [blog.marvinlabs.com][1]
- Get to know what MarvinLabs does on our website: [www.marvinlabs.com][2]
- Follow us on social networks:

* [Follow us on Twitter](http://twitter.com/marvinlabs)
* [Follow us on Google+](https://plus.google.com/u/0/117677945360605555441)
* [Follow us on Facebook](http://www.facebook.com/studio.marvinlabs)

  [1]: http://blog.marvinlabs.com
  [2]: http://www.marvinlabs.com
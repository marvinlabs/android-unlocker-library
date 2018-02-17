Android Unlocker Library [![Release](https://jitpack.io/v/marvinlabs/android-unlocker-library.svg)](https://jitpack.io/#marvinlabs/android-unlocker-library)
========================

Provides a full system to help you develop Android applications that have a free version and some commercial unlocking application. There is also the flexibility for implementing multiple features to be unlocked by various unlocker applications. 

### Gradle (via [JitPack.io](https://jitpack.io/))

1. add jitpack to your project's `build.gradle`:

```groovy
repositories {
	maven { url "https://jitpack.io" }
}
```

2. add the compile statement to your module's `build.gradle`:

```groovy
dependencies {
        // unlocker app
	implementation 'com.github.marvinlabs.android-unlocker-library:library:1.0.1'
	// main app
	implementation 'com.github.marvinlabs.android-unlocker-library:library-core:1.0.1'
}
```

### Usage

**Assumption**

* you have an app with the package `com.my.app`
* you want to create an unlocker app with the package `com.my.app.unlocker`

*Adjust those package names accordingly to fit your requirements*

#### The unlocker app

1. Create an unlocker app. No need to add anything to this project, create a complete empty project (no activities or similar).
2. Add the correct dependency to this project: `implementation 'com.github.marvinlabs.android-unlocker-library:library:1.0.1'`
3. A simply default activity is added to this project by default - define a custom string for `unlocker_info_app` with your apps name for the unlocker apps info string
4. Adjust the manifest like following (read the comments for explanations)

	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
		android:versionCode="100"
		android:versionName="1.00"
		package="com.my.app.unlocker">

		<!-- define the permission -->
		<permission
			android:name="com.my.app.AUTHORIZATION_PROVIDER"
			android:protectionLevel="signature" />

		<application
			android:label="@string/app_name"
			android:allowBackup="true"
			android:icon="@mipmap/icon"
			android:roundIcon="@mipmap/icon_round">

			<!-- ************* -->
			<!-- optional data -->
			<!-- ************* -->

			<!-- string data - define a feature that you want to check or just leave this meta-data away if you just want to check against the unlocker app at all -->
			<meta-data
				android:name="unlocker_app_feature_name"
				android:value="pro" />
				
			<!-- boolean data - set it to true, if you want the unlocker to print it's debug messages, leave it away if not-->
			<meta-data
				android:name="unlocker_debug"
				android:value="true" />

			<!-- ************* -->
			<!-- required data -->
			<!-- ************* -->

			<!-- the package name of the app that needs to be unlocked -->
			<meta-data
				android:name="unlocker_app_package_name"
				android:value="com.my.app" />

			<!-- the provider with the correct authority, permission and exported flag -->
			<provider
				android:name="fr.marvinlabs.unlocker.core.provider.UnlockerProvider"
				android:authorities="com.my.app"
				android:exported="true"
				android:permission="com.my.app.AUTHORIZATION_PROVIDER" />

		</application>

	</manifest>

3. you're done, compile the app and you can already use the unlocker app. *IMPORTANT: you must sign the unlocker app and the app with the same signature!*

#### The main app

1. In your app's manifest add following:

		<manifest 
			<permission
				android:name="com.my.app.AUTHORIZATION_PROVIDER"
				android:protectionLevel="signature" />
				
		</manifest>
		
2. Add the correct dependency: `implementation 'com.github.marvinlabs.android-unlocker-library:library-core:1.0.1'`
3. Check the unlocker state like following:

    3.1 Check if the unlocker app is available
  
		// If the authority is equal to the apps package name, simply pass the context
		boolean unlockerAppAvailable1 = UnlockerProvider.getPackageLevelAuthorization(context);
		// otherwise pass the package name to check as well
		boolean unlockerAppAvailable2 = UnlockerProvider.getPackageLevelAuthorization(context, "com.my.app");
		
    3.2 Check if the unlocker app with a special feature is available
  
		// the above written example manifest defines a "unlocker_app_feature_name" with the value "pro", so if we want to check this feature this works like following
		String featureToCheck = "pro"; 
		// If the authority is equal to the apps package name, simply pass the context + feature
		boolean unlockerAppFeatureAvailable1 = UnlockerProvider.getFeatureLevelAuthorization(context, featureToCheck);
		// otherwise pass the package name as well to check as well
		boolean unlockerAppFeatureAvailable2 = UnlockerProvider.getPackageLevelAuthorization(context, "com.my.app", featureToCheck);

History
-------

v1.0.1 (2018-02-17)

- full gradle support added
- jitpack.io release added
- simplified usage - setup via app manifest meta-data

v1.0.0 (2013-09-19)

- Library project added
- Packaged as a JAR file

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

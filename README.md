# Noviembre15
Practice "Android Studio" with support from GitHub.


## Introduction
The goal of this project is to prepare a Andoid Content Provider. 
Additionally it was used the library CommonSDK


![Content Provider](https://cloud.githubusercontent.com/assets/6483001/10971968/5fb4a8ec-83d7-11e5-8d81-08581f62d67f.JPG)



TODO: Desarrollar incluyendo URLs de fuentes consultadas etc.
- Moc pantallas
- Desarrollo de un content provider
-** 
Linea Editada con Orion

Utilización de la libreria:


# GitHub Android App  [![Google Play](http://developer.android.com/images/brand/en_generic_rgb_wo_45.png)](https://play.google.com/store/apps/details?id=com.github.mobile) [![Build Status](https://travis-ci.org/github/android.png)](https://travis-ci.org/github/android)

This repository contains the source code for the GitHub Android app.

<a href="https://play.google.com/store/apps/details?id=com.github.mobile" alt="logo" title="Download from Google Play">
  <img src="http://img.skitch.com/20120709-nkdc1yugu2qmdg1ss81m1gr9ty.jpg">
</a>

Please see the [issues](https://github.com/github/android/issues) section to
report any bugs or feature requests and to see the list of known issues.

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

## Building

The build requires [Maven](http://maven.apache.org/download.html)
v3.1.1+ and the [Android SDK](http://developer.android.com/sdk/index.html)
to be installed in your development environment. In addition you'll need to set
the `ANDROID_HOME` environment variable to the location of your SDK:

```bash
export ANDROID_HOME=/opt/tools/android-sdk
```

After satisfying those requirements, the build is pretty simple:

* Run `mvn clean package` from the `app` directory to build the APK only
* Run `mvn clean install` from the root directory to build the app and also run
  the integration tests, this requires a connected Android device or running
  emulator

You might find that your device doesn't let you install your build if you
already have the version from the Android Market installed.  This is standard
Android security as it it won't let you directly replace an app that's been
signed with a different key.  Manually uninstall GitHub from your device and
you will then be able to install your own built version.

See [here](https://github.com/github/android/wiki/Building-From-Eclipse) for
instructions on building from [Eclipse](http://eclipse.org).

## Acknowledgements

This project uses the [GitHub Java API](https://github.com/eclipse/egit-github/tree/master/org.eclipse.egit.github.core)
built on top of [API v3](http://developer.github.com/).

It also uses many other open source libraries such as:

* [Udacity - SunShine App](https://github.com/udacity/Sunshine-Version-2)
* [CommonSDK](https://github.com/CommonUtils/android)



These are just a few of the major dependencies, the entire list of dependencies
is listed in the [app's POM file](https://github.com/github/android/blob/master/app/pom.xml).

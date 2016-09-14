# User-GPS-19.9.16

i couldnt compile the app at first since AS kept giving me a FINE LOCATION PERMISSION needed error. Turns out 
"ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, and WRITE_EXTERNAL_STORAGE are all part of the Android 6.0 runtime permission 
system. In addition to having them in the manifest as you do, you also have to request them from the user at runtime 
(using requestPermissions()) and see if you have them (using checkSelfPermission()).

One workaround in the short term is to drop your targetSdkVersion below 23. " 

taken from -->
http://stackoverflow.com/questions/32083913/android-gps-requires-access-fine-location-error-even-though-my-manifest-file

# Post Test

## How to run the app

For running the app, open Android Studio, File -> Open -> search for the project folder and open it. 
The project is updated with the last running version of Android (29) and you can try it from the 16.
Once inside the app, you can try all the features implemented.

## Architecture

The architecture used is MVVM. There structure of the project has a Models folder, where the data classes are. Also in this folder there are the classes for the database implementation. Then the View folder, where there are all the Fragments and the Main Activity. There you can find the ViewModel folder with the viewmodels used along with the views. There is a Remote folder where you can find the API calls. 

#### Architectural Components of Google

In this project the architectural components of Google are used, for example Room for the local persistence, LiveData, Navigation Component and Databinding.

## Third-party libraries used

- Dagger2 for the dependency injection.
- Retrofit for the API calls.
- Mockito and JUnit for unit testing.


# My-Restaurant-advisor

Application Restaurant Advisor (Java / Android Studio)

Environnement utilisé

Cette application a été développée sous Android Studio.
Nous avons utilisé Retrofit2 et Okhttp afin de pouvoir effectuer des requetes directements sur notre API.
Les librairies exactes à utiliser sont les suivantes

    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'

    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'

    implementation 'com.squareup.okhttp3:logging-interceptor:4.2.1'

    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'

Lancer le projet

Pour lancer le projet il faudra ouvrir le dossier RestaurantAdvisor en tant que projet sur Android Studio.
Vous devez ensuite lancer l'api via la commande php artisan serve --host 192.168.1.*** --port 80, replacez les *** par la fin de votre addresse ip local, de même dans le fichier Retrofit2Client.java.
Afin de correctement aligner chaque informations, vous devez configurer un appareil avec une résolution de 1080x2160 et 440dpi.


Scenario de démo

Baladez vous dans l'application sans vous connecter ni vous enregistrez et appuiez sur tout ce que vous pouvez.
Connectez vous et tout ce qui vous aura été bloqué vous sera débloqué.

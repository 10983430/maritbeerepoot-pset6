# Marvel character database

Deze applicatie is gemaakt als eindproject voor het vak "Native App Studio". De app maakt het mogelijk om door een database van Marvel charactes heen te zoeken en je favoriete characters aan favorieten toe te voegen. De characters hebben allemaal een informatiepagina, waar beschrijvingen staan van de characters en in welke comics, stories en series zij voorkomen. Daarnaast kunnen de favorieten van andere gebruikers ook bekeken worden in een userdatabase.
De gebruikt moet ingelogd zijn om zelf favorieten toe te voegen, maar kan de favorieten van elke gebruiker en de characterdatabase bekijken zonder ingelogd te zijn.

# Eisen
### Firebase
De app gebruikt firebase om data van de gebruiker op te slaan. Hierdoor is het mogelijk om in te loggen. Data kan ook worden bekeken zonder in te loggen.
### API
Voor deze applicatie is de [API van Marvel](https://developer.marvel.com/) gebruikt. Deze kan soms een beetje sloom zijn.
### Data persistance & same state na 'killing' app
De applicatie crashed niet wanneer er geen netwerk/internet beschikbaar is. De app geeft een melding wanneer deze geen connectie kan maken met het internet. De user blijft ingelogd wanneer de connectie wegvalt. De user blijft ook ingelogd wanneer de app gekilled wordt, waardoor dezelfde state behouden wordt.

# Screenshots
![ScreenshotPart1](/doc/ScreensPart1.png)
![ScreenshotPart2](/doc/ScreensPart2.png)
![ScreenshotPart2](/doc/ScreensPart3.png)
Van links naar rechts:  
Reeks 1: Startup scherm, character database, zoekfunctie in actie, informatie character (scrolbaar om verdere info te bekijken)  
Reeks 2: Login, registreer, user database, profiel bij klik op user database  
Reeks 3: User informatie (van ingelogde gebruiker), rest van character informatie (bij het scrollen)  

De navigatie is rechts bovenin te vinden (links character database, midden userdatabase, rechts userinformatie)

# Better code hub
[![BCH compliance](https://bettercodehub.com/edge/badge/10983430/maritbeerepoot-pset6?branch=master)](https://bettercodehub.com/)

# Peer review
Uit de peer review kwamen de volgende punten
* Headers moeten nog worden toegevoegd
* Door de java bestanden namen en activity namen is het niet altijd duidelijk welke bij elkaar horen
* Hier en daar te veel enters
* Dubbele onclicklistener aanwezig in de inlog activity
* Er missen soms spaties tussen de '()' en '{' in het maken van functies
* Vergeet onnodige comments met dode code niet weg te halen
* Dubbele code op verschillende plekken
* Onbereikbare code onder else statement Register pagina
* Functie van 1 regel in UserDatabase
* Sommige dingen kunnen nog worden omgezet in functies in de CharacterDatabase activity  
Uiteraard heb ik alle punten aangepast.






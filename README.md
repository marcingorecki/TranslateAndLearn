TranslateAndLearn
=================
http://geek.mgorecki.net/index.php/i-made-this/android-development/translate-and-learn/

### What is Translate and Learn?
Android application that helps you remember the words you checked in the dictionary.

### How does it work?
App tracks the words you’ve looked up and uses artificial intelligence algorithm* to plan your individual of repetitions.

### Is the dictionary any good?
It’s the best! It is using wiktionary data and currently holds over 450.000 words with examples, multiple meanings and forms.

### Why should I be your beta tester?
For glory or because you want to get some beers from me – you decide.

### Ok, I’m convinced. How to install it?
It’s first beta release, so the installation has some rough edges:
1. Download two files
  http://geek.mgorecki.net/apk/TranslateAndLearn.apk – main application
	http://geek.mgorecki.net/apk/dictionary.db – database file
2. Put the database into root folder (sorry!) of your external storage, most likely it will be called /sdcard
3. Install the application using apk file.

### Troubleshooting
Q: Everytime I search for something I get “Not Found”. A: database file is in wrong location
Q: When I click “Learn” the app says there are no words ready. A: As stated before app uses AI*, so it won’t ask you to repeat words you translated during last 15 minutes nor the ones you said you know already.
Q: App won’t install. A: Throw away the iphone and buy a proper phone
Q: I typed word in English and the translation in English. A: Yes, this is how it works! The button “Translate” will be changed to something more meaningful.

### Known issues
The delete button on history panel doesn’t work
Rotating screen cleans all the data and may lead to “Force Close” (NullPointerException) error
Some words of french origin are missing
Some comments in definitions are partial or incomplete. The rest is fine though.
Footer font is too small on newer devices
The links are not clickable
* well, maybe one day. Now the algorithm is smart, but not intelligent.

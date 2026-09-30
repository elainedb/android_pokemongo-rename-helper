# Pokémon GO Rename Helper

A small native Android utility that makes renaming Pokémon by their IV values faster. Kotlin, 2019.

> **Archived.** No longer on the Play Store.

## How it works

The app posts a persistent notification with buttons for the values 0 to 15. Tap the attack, defence, and stamina values you read in the game, in order, and the last three taps are placed on the clipboard. Switch back to Pokémon GO and paste them as the new name. Expand the notification to see all sixteen values; dismiss it when you are done.

Everything happens from the notification, so there is no need to leave the game.

## Building

Open in Android Studio or run:

```bash
./gradlew assembleDebug
```

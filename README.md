# Pixleboxed

**GUI by Juan and Backend by Neo**

## Frontend Documentation

> [!NOTE]
> Made by Juantjie

### How does the Frontend Work

Currently still heavily under construction however the general idea will be

- User gets shown all current game entries
- User can choose a game or add a game to the list

| Choose Game | Add Game |
|---|---|
|Takes user to a review page|ask the user for the game name|
|Saves the user Review|Grabs Api data for game and asks user to choose correct one|
|---|Saves user game to json|

## Backend Documentation

> [!NOTE]
> Made by Neo

### How does the backend work

The backend currently makes use of the [RAWG](https://rawg.io/) api to allow users to add games at will and rate them. The goal is to have the api return a name, a description and an image for the game.

The backend contains a few methods, only one is public and will soon be replaced by the [GUI](#frontend-documentation).

- requestCorrectGame
    - this method grabs the api request, uses a helper to sift through, and then displays the possible games to the user
    - the user is then asked to select from the options with the index
    - it is ran by using
      ```java
      grabGame api = new grabGame(search, "path/to/games.json");
      api.requestCorrectGame();
      ```

## Purpose of this Project

This project was thought of while making [Juan-Parser](https://github.com/neo-vh/juan-parser) as a way to use the parser

- meant for more learning
- a way to have fun after making the custom json parser

***

> [!IMPORTANT]
> this was made during exams with basically no java experience and a GUI library neither of us know

**PS. Juan n klein chomma**
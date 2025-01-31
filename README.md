# About

This project contains several different implementations of the card game Blackjack.

The intent of this project is to explore approaches to designing software with a focus on...

- Separation of core data and logic from user interaction
- Use of appropriate code structures (enums, records, classes, interfaces) to model various aspects of the game.

To keep the implementations relatively simple, a simplified set of Blackjack rules is used:
- Each round of the game is played with a standard deck of 52 cards.
- The game is played between a dealer and one player.
- Betting occurs only at the beginning of the game (no insurance, splits, doubling down, etc.).
- The player starts with $100 and can bet any amount between $1 and their current balance.
- Once the player runs out of money, the game ends.
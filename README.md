# Darwin World

This project is an evolving simulation where animals navigate a world of steppes and jungles, foraging for food, reproducing, and evolving over time.

## Table of Contents
- [Project Goal](#project-goal)
- [World Overview](#world-overview)
- [Animal Mechanics](#animal-mechanics)
- [Reproduction and Evolution](#reproduction-and-evolution)
- [Simulation Steps](#simulation-steps)
- [Configuration Variants](#configuration-variants)
- [Application Requirements](#application-requirements)

## Project Goal

Darwin World is a game-like simulation where we do not control characters directly. Instead, we create an evolving ecosystem and observe how animals adapt and survive.

## World Overview

The world consists of a rectangular grid divided into tiles. There are two main terrain types:
- **Steppes** – Sparse vegetation.
- **Jungles** – Higher plant growth.

Plants grow randomly, with higher concentration in jungles.

## Animal Mechanics

Each animal has:
- **Coordinates (x, y)** – Position on the grid.
- **Energy Level** – Depletes over time and increases when eating plants.
- **Direction** – One of eight possible movement directions.
- **Genome** – A sequence of integers (0-7) controlling movement and behavior.

### Movement and Orientation

Animals change direction based on their genome, move forward, and repeat this cycle. The genome defines turning behavior before moving.

## Reproduction and Evolution

Animals reproduce when they have enough energy:
- **Energy transfer** – Parents pass energy to offspring.
- **Genetic mix** – Offspring inherits genes from parents proportionally to energy levels.
- **Mutations** – Some genes may randomly change.

## Simulation Steps
Each day, the simulation follows these steps:
1. Remove dead animals.
2. Move and rotate animals.
3. Consume food.
4. Reproduce animals.
5. Grow new plants.

## Configuration Variants

Different configurations affect gameplay:
- **Map type** – Shapes and terrain behavior.
- **Plant growth** – Different distribution strategies.
- **Animal behavior** – Genetic inheritance variations.
- **Mutation rules** – Randomness in evolution.

## Application Requirements

1. **Graphical User Interface (GUI)** using JavaFX.
2. **Simulation Customization** – Users can select or modify parameters.
3. **Multiple Simulations** – Run in separate windows.
4. **Animated Display** – Visual representation of animals and plants.
5. **Pause and Resume** – Toggle simulation at any time.
6. **Statistics Tracking** – Displaying key data, including:
   - Total number of animals and plants.
   - Number of free tiles.
   - Most common genotypes.
   - Average offspring per animal.
7. **Animal Tracking** – Select a specific animal to monitor throughout the simulation.

## Full decription
[link](https://github.com/Soamid/obiektowe-lab/blob/master/proj/Readme.md)

## License
This project is licensed under the MIT License.


# Waves

A minecraft plugin for fight monsters waves.




## Author

- [@Ori0nexe](https://www.github.com/Ori0nexe)


## Installation

Download ./plugin/<file>.jar and put it in the "plugin" directory of your server and reload it.
    
## Features

Multiplayer :
 - multiple monster who are strongers with levels

Solo
 - A story-mode based gameplay

## commands :

### Join commands :
```bash
/wmulti # join a multi arena
/wsolo # /!\ not implemented yet
```

### Managements commands :

```bash
/wv <game mode> <parameter> [arena]
```
#### Multi
you need to start by create an arena with
```bash
/wv multi createarena <arena name>
```
then you need to set all the settings of the arena. A complete list of the settings below, you need to be at the precise location where you want to add the point :
```bash
- setloc1 # first corner of the arena
- setloc2 # second corner of the arena
- setlobby # the lobby of the game for wating peoples
- setspawn # the spawn of the arena
- setmobspawn # a spawn of mob, you can create a lot of these spots in your arena
```
An example of a complete command :
```bash
/wv multi setlobby test_arena # set the lobby of the arena named "test_arena"
```
There is a listing command for see all your created arenas :
```bash
- list
```
Output :
```
Liste des arènes:
 multi
  - test
```
#### Solo
[not implemented yet]
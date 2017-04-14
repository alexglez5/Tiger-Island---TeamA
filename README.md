# Tiger-Island---TeamA (correct branch)
## In Order to Run to Accept Server Communication
### Obtain the .java files and project directory

From GitShell

1. `git checkout LastBranch`

2. `git pull`

From Downloaded Zip

unzip 

### Build Project

1. Open Project from folder named `Tiger-Island---TeamA`

2. `Build` project in intellij

3. In `run` dropdown menu select `Edit Configurations...` 

4. Add to the `program arguements` an input similar to `localhost 4444 heygang A A` 

    1.`IP` of the server
    
    2.`port` of the server
    
    3.`Password` of the server
    
    4.`Username` of your team
    
    5.`Password` of your team

5. leftclick `tournamentClientMain.java`

6. `control + shift + F10`

### Command Line 

1. Build in intellij

2. cd in command line to <folder path>/target/class

3. Run java Tigerisland.tournamentClientMain <ip> <port> <tournamentPassword> <username> <password>

### Features Implemented

`Totoro`, `Tiger Playgrounds`, `Villagers`, `AI place tiles`, `AI all build options`, `Nuking`, `AI Nuke Enemy Settlements`, `Points`, `Client Socket`, `Terrain`, `Tile`, `Server Message Handler`, `Merge Settlments`, `Enter Parameters to change IP, pass`

### Known Bugs
    - Rare Timeout issue (exception being thrown)


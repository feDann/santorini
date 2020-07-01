# Prova Finale Ingegneria del Software 2020 - Gruppo PSP11

# Game Setup

## Server
To start the [server](https://github.com/feDann/ing-sw-2020-fadini-ferrazzo-figini/blob/master/Derivelables/final/jar/ServerApp.jar) use the following command:
```
java -jar ServerApp.jar
```

at this point "The Santorini server is up and running..." should be displayed on the terminal.
## Client
### CLI
A player that wishes to play with a [CLI](https://github.com/feDann/ing-sw-2020-fadini-ferrazzo-figini/blob/master/Derivelables/final/jar/ClientApp.jar) interface should run the following command in the terminal:
```
java -jar ClientApp.jar -nogui
```
### GUI
a player that wished to play with a [GUI](https://github.com/feDann/ing-sw-2020-fadini-ferrazzo-figini/blob/master/Derivelables/final/jar/ClientApp.jar) interface should be able to simply double click on the jar to start playing,
otherwise run the following command in the terminal:
```
java -jar ClientApp.jar
```
# How to Play
## To play locally
- start the server
- start two or more clients (CLI or GUI)
- during server ip selection, insert 127.0.0.1 (or just leave blank)
- you are all set up to play a local game!
## To play with the remote server
- start two or more clients (CLI or GUI)
- during server ip selection, insert 13.58.138.117
- you are set up to play an online game!


# Implemented Features
| Feature | State |
|:-----------------------|:------------------------------------:|
| Simplified Rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| Complete Rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| Multiple Games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| Advanced Gods | [![RED](https://placehold.it/15/f03c15/f03c15)](#)|
| Persistency | [![RED](https://placehold.it/15/f03c15/f03c15)](#)|
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#)|

# Documentation
## UML
The following UML diagrams give an overview of the project, with a different levels of abstraction:
- [Project Uml](https://github.com/feDann/ing-sw-2020-fadini-ferrazzo-figini/blob/master/Derivelables/final/uml/ProjectUml.svg)
- [Summary UML](https://github.com/feDann/ing-sw-2020-fadini-ferrazzo-figini/blob/master/Derivelables/final/uml/Summary%20Uml.svg)
- [Package Specific UMLs](https://github.com/feDann/ing-sw-2020-fadini-ferrazzo-figini/tree/master/Derivelables/final/uml/Package%20Specific%20UMLs)
## Javadoc
Project documentation available at this [link]()
## Report
Some statistics of final coverage [report]() :
- Model Package line coverage: 92%


# Group Members
- ###       Francesco Fadini ([@frafad](https://github.com/frafad))
- ###       Daniele Francesco Antonio Ferrazzo ([@feDann](https://github.com/feDann))
- ###       Andrea Figini ([@anath3m98](https://github.com/anath3m98))

### Note
- the game runs on port 50000
- the remote server may not be active 24/7
<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

# Noviembre15
Practice "Android Studio" with support from GitHub.


## Introduction
The goal of this project is to prepare a Andoid Content Provider. 
The content provider is implemented and used by the application "facts."

![Content Provider](https://cloud.githubusercontent.com/assets/6483001/10971968/5fb4a8ec-83d7-11e5-8d81-08581f62d67f.JPG)

## App Facts
The facts application works like a diary, in which the relevant facts are collected, and optionally the fact geographical location and its economic value.

## DB

|  | Field  | Type | Comment |
|:-:|:--------       | -----:   |-----:   |
| 1 | _id      |key ||
| 2 |date |date   | the date of fact |
| 3 |name |character |short description|
| 4 | category   |character | indeed categorization |
| 5 | category_id|int | Numerical id of the categories |
| 6 | fact   |character | detailed description of the fact |
| 7 | value   |numeric |  quantifying the economic fact |
| 8 | coord_lat   |numeric | geographical location |
| 8 | coord_long   |numeric | geographical location |

## Contract

![Contract](https://cloud.githubusercontent.com/assets/6483001/10997181/42f8904c-848b-11e5-9c93-0fbaa4033471.JPG)


## MOC



## Acknowledgements

This project is inspired by:

* [Udacity - SunShine App](https://github.com/udacity/Sunshine-Version-2)
* [NewCircle's - Android Content Provider Tutorial](https://newcircle.com/s/post/1375/android_content_provider_tutorial)



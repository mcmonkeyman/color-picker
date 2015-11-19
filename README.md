# Color Picker

This project provides a REST api that  allows a user to check out and save random colors.

# Run

```
sbt run
```

# Use

* Get a client id:
```
curl http://localhost:8080/uuid
```
```
{
  "sessionId": "9be0beb%3A150c5df6199%3A-7fff"
}
```

* Check out a color:
```
curl http://localhost:8080/color/4d04b88%3A150c42d94c2%3A-7ffe
```
```
{
  "red": {
    "colorValue": "215"
  },
  "green": {
    "colorValue": "152"
  },
  "blue": {
    "colorValue": "40"
  }
}
```

* Save the checked out color:
```
curl -XPOST http://localhost:8080/color/4d04b88%3A150c42d94c2%3A-7ffe
```
```
{
  "red": {
    "colorValue": "215"
  },
  "green": {
    "colorValue": "152"
  },
  "blue": {
    "colorValue": "40"
  }
}
```

* View last saved color for user:
```
curl http://localhost:8080/color/latest/4d04b88%3A150c42d94c2%3A-7ffe
```
```
{
  "red": {
    "colorValue": "215"
  },
  "green": {
    "colorValue": "152"
  },
  "blue": {
    "colorValue": "40"
  }
}
```

# About

This project was based on a spray template at https://github.com/spray/spray-template.

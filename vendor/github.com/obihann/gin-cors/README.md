# gin-cors

[![GoDoc](https://godoc.org/github.com/obihann/gin-cors?status.svg)](https://godoc.org/github.com/obihann/gin-cors)
[![Build Status](https://travis-ci.org/obihann/gin-cors.svg?branch=master)](https://travis-ci.org/obihann/gin-cors)

CORS middleware for [Gin]. Originally written by [tommy351](https://github.com/tommy351/gin-cors), however I felt that OPTIONS
requests should return 204 (No Content) instead of 200 (OK). The [specs](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html)
do state that 200 is a valid response however I believe if no body is returned we should pass 204 instead.

## Installation

``` bash
$ go get github.com/obihann/gin-cors
```

## Usage

``` go
import (
    "github.com/gin-gonic/gin"
    "github.com/obihann/gin-cors"
)

func main(){
    g := gin.New()
    g.Use(cors.Middleware(cors.Options{}))
}
```

[Gin]: http://gin-gonic.github.io/gin/

## Thanks
* [tommy351](https://github.com/tommy351/gin-cors)

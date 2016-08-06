package main

import (
	"fmt"
	"net/http"
	"os"
	"time"

	"github.com/dgrijalva/jwt-go"
	"github.com/garyburd/redigo/redis"
	"github.com/gin-gonic/gin"
	"github.com/obihann/gin-cors"
	"github.com/satori/go.uuid"
)

var (
	pool *redis.Pool
)

func main() {
	type ()

	var (
		redisServer = os.Getenv("REDISCLOUD_URL")
		port        = os.Getenv("PORT")
		authKey     = os.Getenv("FTDAUTHKEY")
		sub         = "8205d3b2-3e73-432b-b7eb-b73f73818d83"
	)

	if len(redisServer) <= 1 {
		redisServer = ":6379"
	}

	if len(port) <= 1 {
		port = ":3000"
	}

	if len(authKey) <= 1 {
		authKey = "supersecret"
	}

	pool = &redis.Pool{
		MaxIdle: 100,
		Dial: func() (redis.Conn, error) {
			c, err := redis.Dial("tcp", redisServer)

			if err != nil {
				return nil, err
			}

			return c, nil
		},
	}
	router := gin.New()

	router.Use(cors.Middleware(cors.Options{
		AllowHeaders: []string{"Origin", "Accept", "Content-Type", "Authorization", "X-HTTP-Method-Override"},
		AllowMethods: []string{"GET", "POST", "PUT", "DELETE", "HEAD"},
	}))

	base := router.Group("/")
	{
		base.GET("", func(c *gin.Context) {
			uuid := uuid.NewV4()

			tokenString, err := buildToken(uuid, sub)

			if err != nil {
				c.AbortWithError(http.StatusInternalServerError, err)
			}

			err = storeToken(tokenString, uuid, sub)

			if err != nil {
				c.AbortWithError(http.StatusInternalServerError, err)
			}

			c.JSON(200, gin.H{
				"token": tokenString,
			})
		})
	}

	router.Run(port)
}

func storeToken(tokenString string, uuid uuid.UUID, sub string) error {
	conn := pool.Get()
	defer conn.Close()

	conn.Send("SET", fmt.Sprintf("ftd/%s/%s", sub, uuid), tokenString)
	conn.Send("EXPIRE", fmt.Sprintf("ftd/%s/%s", sub, uuid), 86400)

	conn.Flush()
	_, err := conn.Receive()

	if err != nil {
		return err
	}

	return nil
}

func buildToken(uuid uuid.UUID, sub string) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"iss": "http://localhost:3500/",
		"aud": "http://localhost:8000/",
		"exp": time.Now().UTC().Unix() + 86400,
		"sub": sub,
		"jti": uuid,
	})

	key := []byte("supersecret")
	tokenString, err := token.SignedString(key)

	if err != nil {
		return "", err
	}

	return tokenString, nil
}

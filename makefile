dev:
	FTDAUTHKEY=supersecret PORT=:3500 go run server.go
tokens:
	./scripts/generate_keys.sh
docker:
	docker build -t ftd/auth .
clean:
	docker rmi ftd/authauth

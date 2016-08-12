dev:
	FTDAUTHKEY=supersecret DB=ftd-test MONGOLAB_URI=mongodb://localhost REDIS_URL=redis://localhost:6379 PORT=:3500 go run server.go
tokens:
	./scripts/generate_keys.sh
docker:
	docker run -it --rm --name ftd-auth -p 3500:3500 --env-file .env ftd-auth


FROM golang

ADD . /go/src/github.com/fear-the-dice/auth

RUN go install github.com/fear-the-dice/auth
ENTRYPOINT /go/bin/auth

EXPOSE 3500

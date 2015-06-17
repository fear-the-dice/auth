#!/bin/sh

mkdir -p keys
mkdir -p /usr/local/keys

openssl genrsa -out keys/FTDAuth.rsa 1024 -outform DER
openssl pkcs8 -topk8 -nocrypt -in keys/FTDAuth.rsa -out keys/FTDAuth_pk8.rsa
openssl rsa -in keys/FTDAuth.rsa -out keys/FTDAuth.pub -pubout

ln -s $(PWD)/keys/FTDAuth_pk8.rsa /usr/local/keys/FTDAuth_pk8.rsa
ln -s $(PWD)/keys/FTDAuth_pk8.pub /usr/local/keys/FTDAuth.pub

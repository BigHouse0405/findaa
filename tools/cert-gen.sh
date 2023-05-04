#!/bin/bash

workdir=~/work/qrekru
certsdir=$workdir/tools
. $workdir/tools/log.sh

jwtCert=jwt
jwtPub=jwt-pub
jwtSecret=jwt-secret
tlsCA=qrekru
tlsClient=app.qrekru
keystorePassword=$1

main() {
  mkdir $workdir/src/main/resources/certs/
  inf "certificates" "Attempting to rotate certificates in app... [0/2]"
  rotateJwtSecret
  rotateTlsCerts
  inf "certificates" "All certificates rotated."
}

rotateJwtSecret() {
  inf "JWT" "\tRotating JWT certificates"
  inf "JWT" "\tGenerating JWT certificates..."
  openssl genrsa -out jwt.pem 2048
  openssl rsa -in $certsdir/$jwtCert.pem -pubout -out $certsdir/$jwtPub.pem
  openssl pkcs8 -in $certsdir/$jwtCert.pem -topk8 -nocrypt -inform PEM -outform PEM -out $certsdir/$jwtSecret.pem
  if [ $? -eq 0 ]; then
    inf "JWT" "\tGenerated JWT certificates"
  else
    inf "JWT" "\tCould not generate JWT certificates. Exiting"
    exit
  fi

  inf "JWT" "\tGenerated JWT certificates"
  inf "JWT" "\tMoving certificates to classpath..."
  mv $certsdir/jwt.pem $workdir/src/main/resources/certs/
  mv $certsdir/jwt-pub.pem $workdir/src/main/resources/certs/
  mv $certsdir/jwt-secret.pem $workdir/src/main/resources/certs/

  inf "JWT" "\tDone with JWT rotation [1/2]"
}

rotateTlsCerts() {
  inf "TLS" "\tRotating TLS certificates"
  inf "TLS" "\tGenerating CA..."
  openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -subj '/O=Better Nerf./CN=qrekru' -keyout $certsdir/$tlsCA.key -out $certsdir/$tlsCA.crt
  if [ $? -eq 0 ]; then
    inf "TLS" "\tGenerated CA certificates"
  else
    inf "JWT" "\tCould not generate CA certificates. Exiting"
    exit
  fi

  inf "TLS" "\tGenerating service keypairs..."
  openssl req -out $certsdir/$tlsClient.csr -newkey rsa:2048 -nodes -keyout $certsdir/$tlsClient.key -subj "/CN=localhost/O=app organization"
  openssl x509 -req -sha256 -days 365 -CA $certsdir/$tlsCA.crt -CAkey $certsdir/$tlsCA.key -set_serial 0 -in $certsdir/$tlsClient.csr -out $certsdir/$tlsClient.crt
  if [ $? -eq 0 ]; then
    inf "TLS" "\tGenerated service keypair"
  else
    inf "JWT" "\tCould not generate service keypair. Exiting"
    exit
  fi

  inf "TLS" "\tGenerating service keystore..."
  openssl pkcs12 -export -password pass:$keystorePassword -out $certsdir/keystore.p12 -inkey $certsdir/$tlsClient.key -in $certsdir/$tlsClient.crt


  inf "TLS" "\tMoving certificates to classpath..."
  cp $certsdir/$tlsClient.crt $workdir/src/main/resources/certs
  mv $certsdir/$tlsClient.crt $workdir/tools/stomp/keys
  mv $certsdir/$tlsClient.key $workdir/src/main/resources/certs

  cp $certsdir/$tlsCA.crt $workdir/src/main/resources/certs
  mv $certsdir/$tlsCA.crt $workdir/tools/stomp/keys
  cp $certsdir/$tlsCA.key $workdir/src/main/resources/certs
  mv $certsdir/$tlsCA.key $workdir/tools/stomp/keys

  mv $certsdir/keystore.p12 $workdir/src/main/resources
  cp $workdir/src/main/resources/application-template.yml $workdir/src/main/resources/application.yml
  cp $workdir/src/main/resources/application-container-template.yml $workdir/src/main/resources/application-container.yml
  sed -i '' "s/KEYSTORE_SED/$keystorePassword/g" $workdir/src/main/resources/application.yml
  sed -i '' "s/KEYSTORE_SED/$keystorePassword/g" $workdir/src/main/resources/application-container.yml
  inf "TLS" "\tDone with TLS rotation [2/2]"
}

main

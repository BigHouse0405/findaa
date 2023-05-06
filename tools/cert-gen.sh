#!/bin/bash

workdir=~/work/bnauth
certsdir=$workdir/tools
source $workdir/tools/log.sh

jwtCert=jwt
jwtPub=jwt-pub
jwtSecret=jwt-secret
tlsCA=bnauth
tlsClient=app.bnauth
keystorePassword=$1

main() {
  mkdir $workdir/src/main/resources/certs/
  inf "certificates" "Attempting to rotate certificates in app... [0/2]"
  rotateJwtSecret
  rotateTlsCerts
  inf "certificates" "All certificates rotated."
}

rotateJwtSecret() {
  inf "certificates" "Rotating JWT certificates"
  inf "certificates" "Generating JWT certificates..."
  openssl genrsa -out $certsdir/jwt.pem 2048
  openssl rsa -in $certsdir/$jwtCert.pem -pubout -out $certsdir/$jwtPub.pem
  openssl pkcs8 -in $certsdir/$jwtCert.pem -topk8 -nocrypt -inform PEM -outform PEM -out $certsdir/$jwtSecret.pem
  if [ $? -eq 0 ]; then
    inf "certificates" "Generated JWT certificates"
  else
    inf "certificates" "Could not generate JWT certificates. Exiting"
    exit
  fi

  inf "certificates" "Generated JWT certificates"
  inf "certificates" "Moving certificates to classpath..."
  mv $certsdir/jwt.pem $workdir/src/main/resources/certs/
  mv $certsdir/jwt-pub.pem $workdir/src/main/resources/certs/
  mv $certsdir/jwt-secret.pem $workdir/src/main/resources/certs/

  inf "certificates" "Done with JWT rotation [1/2]"
}

rotateTlsCerts() {
  inf "certificates" "Rotating TLS certificates"
  inf "certificates" "Generating CA..."
  openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -subj '/O=Better Nerf./CN=bnauth' -keyout $certsdir/$tlsCA.key -out $certsdir/$tlsCA.crt
  if [ $? -eq 0 ]; then
    inf "certificates" "Generated CA certificates"
  else
    inf "certificates" "Could not generate CA certificates. Exiting"
    exit
  fi

  inf "certificates" "Generating service keypairs..."
  openssl req -out $certsdir/$tlsClient.csr -newkey rsa:2048 -nodes -keyout $certsdir/$tlsClient.key -subj "/CN=app.bnauth/O=app organization"
  openssl x509 -req -sha256 -days 365 -CA $certsdir/$tlsCA.crt -CAkey $certsdir/$tlsCA.key -set_serial 0 -in $certsdir/$tlsClient.csr -out $certsdir/$tlsClient.crt
  if [ $? -eq 0 ]; then
    inf "certificates" "Generated service keypair"
  else
    inf "certificates" "Could not generate service keypair. Exiting"
    exit
  fi

  inf "certificates" "Generating service keystore..."
  openssl pkcs12 -export -password pass:$keystorePassword -out $certsdir/keystore.p12 -inkey $certsdir/$tlsClient.key -in $certsdir/$tlsClient.crt

  inf "certificates" "Moving certificates to classpath..."
  cp $certsdir/$tlsClient.crt $workdir/src/main/resources/certs
  mv $certsdir/$tlsClient.crt $workdir/ws-client/keys
  cp $certsdir/$tlsClient.key $workdir/src/main/resources/certs
  mv $certsdir/$tlsClient.key $workdir/ws-client/keys

  cp $certsdir/$tlsCA.crt $workdir/src/main/resources/certs
  mv $certsdir/$tlsCA.crt $workdir/ws-client/keys
  cp $certsdir/$tlsCA.key $workdir/src/main/resources/certs
  mv $certsdir/$tlsCA.key $workdir/ws-client/keys

  mv $certsdir/keystore.p12 $workdir/src/main/resources
  cp $workdir/src/main/resources/application-template.yml $workdir/src/main/resources/application.yml
  cp $workdir/src/main/resources/application-container-template.yml $workdir/src/main/resources/application-container.yml
  sed -i '' "s/KEYSTORE_SED/$keystorePassword/g" $workdir/src/main/resources/application.yml
  sed -i '' "s/KEYSTORE_SED/$keystorePassword/g" $workdir/src/main/resources/application-container.yml
  inf "certificates" "Done with TLS rotation [2/2]"
}

main

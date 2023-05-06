# Secure oauth2 server with wss error messaging


## Aim

Spring boot 3.0.4 based oauth2 authorization server with simple functionalities on top.
<br/> Based on preplanned steps to practice and improve spring boot skills.

## Using app

### Structure
- [ws-client](ws-client) - WebSocket react client app - simple functionality to connect to ws and send incorrect login data.
- [src](src) - Spring boot app with oauth2 authorization server and user management. Reports incorrect data to ws-client via websocket.
- [tools](tools) - Scripts to run app locally and in docker, configure key infrastructure and database.

### Pre steps
1. Save content in `~/work/bnauth/`. That means this file should have path `~/work/bnauth/README.md`.
2. In case of local run, npm 9.6+, gradle 8.0+ and java 17+ are required
3. In case of docker run, Docker engine is required.
4. Scripts were tested on zsh, bash and 
### Localhost osx tests

Note: To test on other unix systems, change [local.sh](local.sh) runApps() `osascript` commands to ones matching your system.
<br/> Eventually run script to install apps and then run them manually using steps 4 and 5 from below.
1. Run [local.sh](local.sh) script. 
<br/>For example, `sh local.sh -p keystorePasword`.
   <br/>It will start postgres container, and spring boot app. It will also start
   react app in browser. If it doesn't, open [http://localhost:3000](http://localhost:3000) manually.

or
1. In project root execute `./gradlew clean build` 
2. In project root execute `./gradlew pgRun`
3. Execute `npm i` in [ws-client](ws-client) folder
4. Start spring app with your IDE or `./gradlew bootRun`
5. Start react app with `npm start` in [ws-client](ws-client) folder


### Docker tests
1. Run [docker.sh](docker.sh) script in root folder. <br/>For example, `sh docker.sh -p keystorePasword`.
<br/>It will start gradle installation, image build and container creation. No local runs needed except for working docker env.

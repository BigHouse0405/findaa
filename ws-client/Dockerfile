FROM node:18.4-alpine as BUILD
WORKDIR /app
COPY package.json ./
COPY ./ ./

RUN npm install -g npm@9.6.6
RUN npm install
RUN yes yes | npx dotenv-vault local build --autoapprove

# RUN sed -i "s/localhost/bnauth/g" .env
EXPOSE 3000
CMD ["npm", "start", "--", "HTTPS=true", "SSL_CRT_FILE=./keys/bnauth.crt", "REACT_APP_BNAUTH_URL=https://localhost"]

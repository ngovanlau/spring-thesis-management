FROM node:20.9.0

WORKDIR /thesis-web-app/
COPY src/ /thesis-web-app/src
COPY public/ /thesis-web-app/public
COPY package.json /thesis-web-app/

RUN npm install

EXPOSE 3000

CMD ["npm", "start"]
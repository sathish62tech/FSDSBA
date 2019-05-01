FROM node:10.13-alpine
# ENV NODE_ENV production
WORKDIR /usr/src/app
COPY ["package.json", "package-lock.json*", "npm-shrinkwrap.json*", "./"]
RUN npm install 
COPY . /usr/src/app
# RUN $(npm bin)/ng build
# WORKDIR /usr/src/app
EXPOSE 4200
CMD npm start
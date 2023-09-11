FROM node:18-alpine

# create work directory in app folder
WORKDIR /app

# install required packages for node image
RUN apk update && apk upgrade
RUN apk add git

# copy over package.json files
COPY ./package*.json /app/

# install all depencies
RUN npm install && npm cache clean --force

# copy over all files to the work directory
ADD . /app

# build the project
RUN npm run build

# expose the host and port 3000 to the server
ENV HOST 0.0.0.0
EXPOSE 3000

# run the build project with node
ENTRYPOINT ["node", ".output/server/index.mjs"]

# COPY . .
#
# ENV PATH ./node_modules/.bin/:$PATH

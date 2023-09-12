FROM node:18-alpine
#RUN mkdir /frontend

# 동작을 수행할 파일 위치 지정 (해당 위치에 파일이 복사, 도커 컨테이너 접근 시 해당 폴더에 바로 접근)
WORKDIR /app
#WORKDIR /frontend

# install required packages for node image
RUN apk update && apk upgrade
RUN apk add git

# 종속성 파일 먼저 설치 (소스 코드 변경 시, 기존 종속성 파일 설치 과정은 캐시된 데이터로 처리)
COPY ./package*.json /app/

# install all depencies
RUN npm install && npm cache clean --force
#RUN npm install -g npm && \
#    npm install -g @vue/cli && \
#    npm install -g @vue/cli-init

# copy over all files to the work directory
ADD . /app

# build the project
#RUN npm run build

# expose the host and port 3000 to the server
ENV HOST=0.0.0.0
EXPOSE 9001

# run the build project with node
#ENTRYPOINT ["node", ".output/server/index.mjs","--server.port=3000"]
ENTRYPOINT ["npm", "run", "dev"]

# COPY . .
#
# ENV PATH ./node_modules/.bin/:$PATH

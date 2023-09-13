FROM openjdk:11
#VOLUME /tmp
#상대경로
ARG JAR_FILE=build/libs/*-SNAPSHOT.jar
#WORKDIR $APP_HOME
COPY ./${JAR_FILE} app.jar
#ENV HOST=0.0.0.0
#ENTRYPOINT : 이미지가 컨테이너화 되며 실행될 명령어 지정
ENTRYPOINT ["java","-jar","/app.jar"]
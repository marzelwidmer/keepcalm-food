FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'

ENV JAVA_OPTS=""
ENV SPRING_PROFILE="default"
ENV MONGO_USER=""
ENV MONGO_PASSWORD=""
ENV MONGO_DATABASE=""

ENTRYPOINT exec java $JAVA_OPTS \
 -Djava.security.egd=file:/dev/./urandom \
 -Dspring.profiles.active=$SPRING_PROFILE \
 -Dmongo.user=$MONGO_USER \
 -Dmongo.password=$MONGO_PASSWORD \
 -Dmongo.database=$MONGO_DATABASE \
 -jar app.jar

FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -q -f /usr/src/app/pom.xml clean package -Dmaven.test.skip=true
RUN mkdir -p /usr/src/app/target/dependency && (cd /usr/src/app/target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
ARG DEPENDENCY=/usr/src/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 9092
ENTRYPOINT ["java","-cp","app:app/lib/*","com.classmanagement.authorizationserver.AuthorizationServerApplication"]
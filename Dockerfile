FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /workspace/mptv-integrated-tests

COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src

CMD ["mvn", "clean", "test"]

FROM jelastic/maven:3.9.5-openjdk-21 as builder

WORKDIR /app

COPY pom.xml .
COPY /src ./src

RUN mvn clean install -Dmaven.test.skip=true

FROM bellsoft/liberica-openjdk-alpine:21 as target

WORKDIR /app

COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar /app/task_management_service.jar

CMD ["java", "-jar", "task_management_service.jar"]

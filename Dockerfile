# ---------------------------------------------------------
# Stage 1: Build Java sources
# ---------------------------------------------------------
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app
COPY src ./src
RUN mkdir out

RUN find src -name "*.java" > sources.txt && \
    javac -d out @sources.txt

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/out ./out

# Run the CLI application
ENTRYPOINT ["java", "-cp", "out", "com.carousell.marketplace.cli.Application"]

# Stage 1: Build the Spring Boot application
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src
COPY ansible ./ansible

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the Spring Boot application and install Ansible Navigator
FROM openjdk:17-jdk-alpine

# Install necessary packages
RUN apk update && \
    apk add --no-cache \
	python3=~3.9 \
	python3-dev \
	bash \
	gcc \
	musl-dev \
	libffi-dev \
	openssl-dev \
	make \
	pkgconf \
	oniguruma-dev

RUN python3 -m ensurepip && \
    pip3 install --no-cache --upgrade pip setuptools wheel

# Install ansible and ansible-navigator

RUN pip3 install ansible ansible-navigator docker

# Set the working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]


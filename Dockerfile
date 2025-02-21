FROM openjdk:17

ARG FILE_JAR=target/*.jar

COPY ${FILE_JAR} app.service.jar

ENTRYPOINT ["java", "-jar", "app.service.jar"]

EXPOSE 8888
# docker build -t api-img-sample-tayjava .
# docker run -it -p 8888:8888 --name=api-container api-img-sample-tayjava

# Stop the existing container
#docker stop api-container

# Remove the existing container
#docker rm api-container

# Run the new container
#docker run -it -p 8888:8888 --name=api-container api-img-sample-tayjava
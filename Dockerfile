FROM openjdk
ADD target/liveloc-worker.jar liveloc-worker.jar
ENTRYPOINT ["java", "-jar", "/liveloc-worker.jar"]
EXPOSE 9000
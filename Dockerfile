FROM java:8
WORKDIR /
COPY liveloc-worker.jar liveloc-worker.jar
EXPOSE 9000
CMD ["java", "-jar", "liveloc-worker.jar"]

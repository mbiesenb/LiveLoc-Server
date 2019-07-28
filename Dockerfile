FROM java:8
WORKDIR /
ADD liveloc.jar liveloc.jar
EXPOSE 9000
CMD ["java", "-jar", "liveloc.jar"]

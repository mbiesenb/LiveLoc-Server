FROM java:8
WORKDIR /
ADD liveloc.jar liveloc.jar
EXPOSE 3000
CMD ["java", "-jar", "liveloc.jar"]

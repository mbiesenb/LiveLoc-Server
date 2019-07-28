FROM java:8
WORKDIR /home/martin/jenkins/liveloc/liveloc-worker/1.0
ADD liveloc-worker-1.0-jar-with-dependencies.jar liveloc.jar
EXPOSE 9000
CMD ["java", "-jar", "liveloc.jar"]

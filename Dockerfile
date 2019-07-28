FROM java:8
WORKDIR /home/martin/jenkins/
ADD /home/martin/jenkins/liveloc/liveloc-worker/1.0liveloc-worker-1.0-jar-with-dependencies.jar liveloc.jar
EXPOSE 9000
CMD ["java", "-jar", "liveloc.jar"]

#FROM openjdk:8-jdk-alpine

FROM store/oracle/serverjre:8

RUN mkdir -p /u01/app

COPY ./target/ssp.jar /u01/app

WORKDIR /u01/app

#ENTRYPOINT ["java","-jar","ssp.jar"]

ENTRYPOINT exec java -Xms512m -Xmx512m -jar ssp.jar 

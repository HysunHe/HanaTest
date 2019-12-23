#FROM openjdk:8-jdk-alpine

FROM store/oracle/serverjre:8

RUN mkdir -p /u01/app

COPY ./target/bot-gateway-springboot.jar /u01/app

WORKDIR /u01/app

#ENTRYPOINT ["java","-jar","bot-gateway-springboot.jar"]

ENTRYPOINT exec java -Xms1024m -Xmx2048m -jar bot-gateway-springboot.jar 

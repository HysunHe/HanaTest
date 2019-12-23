#FROM openjdk:8-jdk-alpine

FROM store/oracle/serverjre:8

RUN mkdir -p /u01/app/Wallet

COPY ./target/ssp.jar /u01/app

#COPY ./Wallet_SKBBPOCDB.zip /u01/app

COPY ./ojdbc8-full/* /u01/app/

COPY ./Wallet/* /u01/app/Wallet/

WORKDIR /u01/app

#ENTRYPOINT ["java","-jar","ssp.jar"]

ENTRYPOINT exec java -Xms512m -Xmx512m -Xbootclasspath/a:./ojdbc8.jar:./ucp.jar:./oraclepki.jar:./osdt_core.jar:./osdt_cert.jar:./ons.jar:. -jar ssp.jar 

-- CREATE USER c##botadmin IDENTIFIED BY BotWelcome123$$;
-- GRANT CONNECT TO c##botadmin;
-- GRANT CONNECT, RESOURCE TO c##botadmin;
-- GRANT UNLIMITED TABLESPACE TO c##botadmin;

CREATE TABLE SSP_LOCAL_GLN_USER 
(
  USER_ID VARCHAR2(256) NOT NULL
, USER_NAME VARCHAR2(256)
, ORG_CODE VARCHAR2(256) NOT NULL 
, LOCAL_GLN_UUID VARCHAR2(256)
, BALANCE FLOAT NOT NULL
);
-- insert into SSP_LOCAL_GLN_USER values ('OAA00018', 'Eric NA', 'GSKOAA', 'GSKOAAGU100000165', '999999999999');

CREATE TABLE SSP_REQ_ORG 
(
  ORG_CODE VARCHAR2(256) NOT NULL 
, AUTH_SECRET VARCHAR2(2048) 
);
-- insert into SSP_REQ_ORG values ('GSKOAA', '3342504754796D56567A746E797A7953324837476F456F634B61423538463344');

CREATE TABLE SSP_TRANSACTIONS 
(
  USER_ID VARCHAR2(256)
, REQ_ORG_CODE VARCHAR2(50) 
, REQ_ORG_TX_DATE VARCHAR2(20) 
, REQ_ORG_TX_TIME VARCHAR2(20) 
, GLN_TX_NO VARCHAR2(50) 
, PAY_CODE VARCHAR2(50) 
, BAR_CODE VARCHAR2(256) 
, QR_CODE VARCHAR2(256) 
, VALID_SECOND INT 
, ORIG_BALANCE FLOAT 
, NEW_BALANCE FLOAT 
, TX_AMT FLOAT 
, TX_CUR VARCHAR2(20) 
, TX_EX_RATE FLOAT 
, STATUS VARCHAR2(20) 
, APPROVE_DATETIME VARCHAR2(20) 
, PAYMENT_NO	VARCHAR2(256)
, WON	VARCHAR2(256)
, RECEIPT_QR	VARCHAR2(512)
, SETTLEMENT_DATE	VARCHAR2(20)
, API_KEY	VARCHAR2(2048)
);

CREATE TABLE SSP_WV_PARAMS 
(
  ID VARCHAR2(256) NOT NULL 
, USERNAME VARCHAR2(256) 
, ACCOUNT VARCHAR2(256) 
, BANKBRANCH VARCHAR2(256) 
, AMOUNT VARCHAR2(256) 
, TARGETACTION VARCHAR2(256) 
, CALLBACK_URL VARCHAR2(1024) NOT NULL 
);


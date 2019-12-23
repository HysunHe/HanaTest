-- CREATE USER c##botadmin IDENTIFIED BY BotWelcome123$$;
-- GRANT CONNECT TO c##botadmin;
-- GRANT CONNECT, RESOURCE TO c##botadmin;
-- GRANT UNLIMITED TABLESPACE TO c##botadmin;

--------------------------------------------------------
--  DDL for Table BOT_BLACKLIST
--------------------------------------------------------

  CREATE TABLE BOT_BLACKLIST
   (	"ID" VARCHAR2(36 BYTE), 
	"USER_ID" VARCHAR2(256 BYTE), 
	"USER_AGENT" VARCHAR2(512 BYTE), 
	"CREATED_AT" TIMESTAMP (6), 
	"UPDATED_AT" TIMESTAMP (6)
   );
--------------------------------------------------------
--  DDL for Table BOT_CATEGORIES
--------------------------------------------------------

  CREATE TABLE BOT_CATEGORIES
   (	"ID" VARCHAR2(36 BYTE), 
	"NAME" VARCHAR2(256 BYTE), 
	"CREATED_AT" TIMESTAMP (6), 
	"UPDATED_AT" TIMESTAMP (6)
   );
--------------------------------------------------------
--  DDL for Table BOT_CONFIG
--------------------------------------------------------

  CREATE TABLE BOT_CONFIG
   (	"ID" VARCHAR2(36 BYTE), 
	"DISPLAY" VARCHAR2(512 BYTE), 
	"VALUE" VARCHAR2(1024 BYTE), 
	"CREATED_AT" TIMESTAMP (6), 
	"UPDATED_AT" TIMESTAMP (6), 
	"KIND" VARCHAR2(256 BYTE), 
	"KEY" VARCHAR2(512 BYTE)
   );
--------------------------------------------------------
--  DDL for Table BOT_FILES
--------------------------------------------------------

  CREATE TABLE BOT_FILES
   (	"ID" VARCHAR2(36 BYTE), 
	"SERIAL_NO" VARCHAR2(512 BYTE), 
	"CHECKSUM" VARCHAR2(512 BYTE), 
	"FILENAME" VARCHAR2(512 BYTE), 
	"FILE_ID" VARCHAR2(512 BYTE), 
	"LINK_ID" VARCHAR2(512 BYTE), 
	"PUBLIC_URI" VARCHAR2(1024 BYTE), 
	"BATCH_NUMBER" VARCHAR2(512 BYTE), 
	"COMMODITY_ID" VARCHAR2(512 BYTE), 
	"CREATED_AT" TIMESTAMP (6), 
	"UPDATED_AT" TIMESTAMP (6)
   );
--------------------------------------------------------
--  DDL for Table BOT_QUESTIONS
--------------------------------------------------------

  CREATE TABLE BOT_QUESTIONS
   (	"ID" VARCHAR2(36 BYTE), 
	"USER_ID" VARCHAR2(256 BYTE), 
	"QUESTION" VARCHAR2(4000 BYTE), 
	"CREATED_AT" TIMESTAMP (6), 
	"UPDATED_AT" TIMESTAMP (6)
   );
--------------------------------------------------------
--  DDL for Table BOT_TOPICS
--------------------------------------------------------

  CREATE TABLE BOT_TOPICS
   (	"ID" VARCHAR2(36 BYTE), 
	"QUESTION" VARCHAR2(4000 BYTE), 
	"ANSWER" VARCHAR2(4000 BYTE), 
	"HEAT" NUMBER(*,0), 
	"CREATED_AT" TIMESTAMP (6), 
	"UPDATED_AT" TIMESTAMP (6), 
	"CATEGORY" VARCHAR2(36 BYTE)
   );
--------------------------------------------------------
--  DDL for Index BOT_BLACKLIST_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_BLACKLIST_PK ON BOT_BLACKLIST ("ID");
--------------------------------------------------------
--  DDL for Index BOT_CATEGORIES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_CATEGORIES_PK ON BOT_CATEGORIES ("ID");
--------------------------------------------------------
--  DDL for Index BOT_CATEGORIES_NAMEUNQ
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_CATEGORIES_NAMEUNQ ON BOT_CATEGORIES ("NAME");
--------------------------------------------------------
--  DDL for Index BOT_PROP_CONFIG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_PROP_CONFIG_PK ON BOT_CONFIG ("ID");
--------------------------------------------------------
--  DDL for Index BOT_FILES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_FILES_PK ON BOT_FILES ("ID");
--------------------------------------------------------
--  DDL for Index BOT_QUESTIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_QUESTIONS_PK ON BOT_QUESTIONS ("ID");
--------------------------------------------------------
--  DDL for Index BOT_TOPICS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX BOT_TOPICS_PK ON BOT_TOPICS ("ID");
--------------------------------------------------------
--  Constraints for Table BOT_BLACKLIST
--------------------------------------------------------

  ALTER TABLE BOT_BLACKLIST MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE BOT_BLACKLIST ADD CONSTRAINT "BOT_BLACKLIST_PK" PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table BOT_CATEGORIES
--------------------------------------------------------

  ALTER TABLE BOT_CATEGORIES MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE BOT_CATEGORIES MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE BOT_CATEGORIES ADD CONSTRAINT "BOT_CATEGORIES_PK" PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table BOT_CONFIG
--------------------------------------------------------

  ALTER TABLE BOT_CONFIG MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE BOT_CONFIG MODIFY ("DISPLAY" NOT NULL ENABLE);
  ALTER TABLE BOT_CONFIG MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE BOT_CONFIG ADD CONSTRAINT "BOT_PROP_CONFIG_PK" PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table BOT_FILES
--------------------------------------------------------

  ALTER TABLE BOT_FILES MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES ADD CONSTRAINT "BOT_FILES_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE BOT_FILES MODIFY ("SERIAL_NO" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("CHECKSUM" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("FILENAME" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("FILE_ID" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("LINK_ID" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("PUBLIC_URI" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("BATCH_NUMBER" NOT NULL ENABLE);
  ALTER TABLE BOT_FILES MODIFY ("COMMODITY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BOT_QUESTIONS
--------------------------------------------------------

  ALTER TABLE BOT_QUESTIONS MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE BOT_QUESTIONS ADD CONSTRAINT "BOT_QUESTIONS_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE BOT_QUESTIONS MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE BOT_QUESTIONS MODIFY ("QUESTION" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BOT_TOPICS
--------------------------------------------------------

  ALTER TABLE BOT_TOPICS MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE BOT_TOPICS ADD CONSTRAINT "BOT_TOPICS_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE BOT_TOPICS MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE BOT_TOPICS MODIFY ("QUESTION" NOT NULL ENABLE);
  ALTER TABLE BOT_TOPICS MODIFY ("ANSWER" NOT NULL ENABLE);
  ALTER TABLE BOT_TOPICS MODIFY ("HEAT" NOT NULL ENABLE);

--------------------------------------------------------
--  Init Data
--------------------------------------------------------
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('E0B69EC1F7AC47C5884E40DCDA18F264','恶意用户连续提问多少次禁言','99',sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('21F341AC066B405D96DD0367F1F989BB','恶意用户每次禁言多少分钟','120',sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('4F874BCA5DC647F6B4CC5D2D8CEDB7B9','转人工客服关键词列表(半角斜线/分隔)','转人工/接人工/转客服/接客服/人工客服/人工服务',sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('25C8C433B3D0467B93D25DE067B866C8','用户提问多少次转人工客服','1000',sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('365F69C5E49C46D9BECA8B890C69DB18','意图识别失败多少次转人工客服','1000', sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('C9FD9EADF100417CB0AF20A15D55BD9E','显示多少个热门问题','8', sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('D8FD9EADF100417CB0AF20A15D55BD9F','欢迎语','我是智能客服小马，有什么我能够帮您的吗？', sysdate, sysdate,'SYSTEM_SETTINGS',null);
Insert into BOT_CONFIG (ID,DISPLAY,VALUE,CREATED_AT,UPDATED_AT,KIND,KEY) values ('EEFD9EADF100417CB0AF20A15D55BA1F','提示语','您可以输入关键词进行提问，如“报名”等信息，也可以从下面的热门问题中进行选择。', sysdate, sysdate,'SYSTEM_SETTINGS',null);
commit;

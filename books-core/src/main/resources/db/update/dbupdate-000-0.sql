SET IGNORECASE TRUE;
create memory table T_AUTHENTICATION_TOKEN ( AUT_ID_C varchar(36) not null, AUT_IDUSER_C varchar(36) not null, AUT_LONGLASTED_B bit not null, AUT_CREATIONDATE_D datetime not null, AUT_LASTCONNECTIONDATE_D datetime, primary key (AUT_ID_C) );
create memory table T_BASE_FUNCTION ( BAF_ID_C varchar(20) not null, primary key (BAF_ID_C) );
create memory table T_CONFIG ( CFG_ID_C varchar(50) not null, CFG_VALUE_C varchar(250) not null, primary key (CFG_ID_C) );
create memory table T_LOCALE ( LOC_ID_C varchar(10) not null, primary key (LOC_ID_C) );
create memory table T_USER ( USE_ID_C varchar(36) not null, USE_IDLOCALE_C varchar(10) not null, USE_IDROLE_C varchar(36) not null, USE_USERNAME_C varchar(50) not null, USE_PASSWORD_C varchar(60) not null, USE_EMAIL_C varchar(100) not null, USE_THEME_C varchar(100) not null, USE_FIRSTCONNECTION_B bit not null, USE_CREATEDATE_D datetime not null, USE_DELETEDATE_D datetime, primary key (USE_ID_C) );
create memory table T_ROLE ( ROL_ID_C varchar(36) not null, ROL_NAME_C varchar(36) not null, ROL_CREATEDATE_D datetime not null, ROL_DELETEDATE_D datetime, primary key (ROL_ID_C) );
create memory table T_ROLE_BASE_FUNCTION ( RBF_ID_C varchar(36) not null, RBF_IDROLE_C varchar(36) not null, RBF_IDBASEFUNCTION_C varchar(20) not null, RBF_CREATEDATE_D datetime not null, RBF_DELETEDATE_D datetime, primary key (RBF_ID_C) );
create cached table T_BOOK ( BOK_ID_C varchar(36) not null, BOK_TITLE_C varchar(255) not null, BOK_SUBTITLE_C varchar(255), BOK_AUTHOR_C varchar(255) not null, BOK_DESCRIPTION_C varchar(4000), BOK_ISBN10_C varchar(10), BOK_ISBN13_C varchar(13), BOK_PAGECOUNT_N integer, BOK_LANGUAGE_C varchar(2), BOK_PUBLISHDATE_D datetime, primary key (BOK_ID_C) );
create cached table T_USER_BOOK ( UBK_ID_C varchar(36) not null, UBK_IDBOOK_C varchar(36) not null, UBK_IDUSER_C varchar(36) not null, UBK_CREATEDATE_D datetime not null, UBK_DELETEDATE_D datetime, UBK_READDATE_D datetime, primary key (UBK_ID_C) );
create cached table T_TAG ( TAG_ID_C varchar(36) not null, TAG_IDUSER_C varchar(36) not null, TAG_NAME_C varchar(36) not null, TAG_COLOR_C varchar(7) default '#3a87ad' not null, TAG_CREATEDATE_D datetime, TAG_DELETEDATE_D datetime, primary key (TAG_ID_C) );
create cached table T_USER_BOOK_TAG ( BOT_ID_C varchar(36) not null, BOT_IDUSERBOOK_C varchar(36) not null, BOT_IDTAG_C varchar(36) not null, primary key (BOT_ID_C) );
alter table T_AUTHENTICATION_TOKEN add constraint FK_AUT_IDUSER_C foreign key (AUT_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_USER add constraint FK_USE_IDLOCALE_C foreign key (USE_IDLOCALE_C) references T_LOCALE (LOC_ID_C) on delete restrict on update restrict;
alter table T_USER add constraint FK_USE_IDROLE_C foreign key (USE_IDROLE_C) references T_ROLE (ROL_ID_C) on delete restrict on update restrict;
alter table T_ROLE_BASE_FUNCTION add constraint FK_RBF_IDROLE_C foreign key (RBF_IDROLE_C) references T_ROLE (ROL_ID_C) on delete restrict on update restrict;
alter table T_ROLE_BASE_FUNCTION add constraint FK_RBF_IDBASEFUNCTION_C foreign key (RBF_IDBASEFUNCTION_C) references T_BASE_FUNCTION (BAF_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK add constraint FK_UBK_IDBOOK_C foreign key (UBK_IDBOOK_C) references T_BOOK (BOK_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK add constraint FK_UBK_IDUSER_C foreign key (UBK_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_TAG add constraint FK_TAG_IDUSER_C foreign key (TAG_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK_TAG add constraint FK_BOT_IDUSERBOOK_C foreign key (BOT_IDUSERBOOK_C) references T_USER_BOOK (UBK_ID_C) on delete restrict on update restrict;
alter table T_USER_BOOK_TAG add constraint FK_BOT_IDTAG_C foreign key (BOT_IDTAG_C) references T_TAG (TAG_ID_C) on delete restrict on update restrict;
insert into T_CONFIG(CFG_ID_C, CFG_VALUE_C) values('DB_VERSION', '0');
insert into T_CONFIG(CFG_ID_C, CFG_VALUE_C) values('API_KEY_GOOGLE', '');
insert into T_BASE_FUNCTION(BAF_ID_C) values('ADMIN');
insert into T_LOCALE(LOC_ID_C) values('en');
insert into T_LOCALE(LOC_ID_C) values('fr');
insert into T_ROLE(ROL_ID_C, ROL_NAME_C, ROL_CREATEDATE_D) values('admin', 'Admin', NOW());
insert into T_ROLE(ROL_ID_C, ROL_NAME_C, ROL_CREATEDATE_D) values('user', 'User', NOW());
insert into T_ROLE_BASE_FUNCTION(RBF_ID_C, RBF_IDROLE_C, RBF_IDBASEFUNCTION_C, RBF_CREATEDATE_D) values('admin_ADMIN', 'admin', 'ADMIN', NOW());
insert into T_USER(USE_ID_C, USE_IDLOCALE_C, USE_IDROLE_C, USE_USERNAME_C, USE_PASSWORD_C, USE_EMAIL_C, USE_THEME_C, USE_FIRSTCONNECTION_B, USE_CREATEDATE_D) values('admin', 'en', 'admin', 'admin', '$2a$05$6Ny3TjrW3aVAL1or2SlcR.fhuDgPKp5jp.P9fBXwVNePgeLqb4i3C', 'admin@localhost', 'default.less', true, NOW());

-- create cached table T_COMMON_LIBRARY_BOOK (CLB_ID_C varchar(36) not null primary key,CLB_TITLE_C varchar(255) not null,CLB_AUTHORS_C varchar(1024) not null,CLB_GENRES_C varchar(1024) not null,CLB_RATING_N double not null,CLB_THUMBNAIL_URL_C varchar(1024));

CREATE TABLE T_COMMON_LIBRARY_BOOK (CLB_ID_C VARCHAR(36) NOT NULL PRIMARY KEY, CLB_TITLE_C VARCHAR(255) NOT NULL,CLB_RATING_N DOUBLE NOT NULL,CLB_TOTAL_RATINGS INT NOT NULL,CLB_AVERAGE_RATING DOUBLE NOT NULL,CLB_THUMBNAIL_URL_C VARCHAR(1024));

CREATE TABLE T_AUTHORS (AUTHOR_ID INT PRIMARY KEY,AUTHOR_NAME VARCHAR(255) NOT NULL);

CREATE TABLE T_GENRES (GENRE_ID INT PRIMARY KEY,GENRE_NAME VARCHAR(255) NOT NULL);

CREATE TABLE T_BOOK_AUTHORS (BOOK_ID VARCHAR(36) NOT NULL,AUTHOR_ID INT NOT NULL,PRIMARY KEY (BOOK_ID, AUTHOR_ID),FOREIGN KEY (BOOK_ID) REFERENCES T_COMMON_LIBRARY_BOOK (CLB_ID_C),FOREIGN KEY (AUTHOR_ID) REFERENCES T_AUTHORS (AUTHOR_ID));

CREATE TABLE T_BOOK_GENRES (BOOK_ID VARCHAR(36) NOT NULL,GENRE_ID INT NOT NULL,PRIMARY KEY (BOOK_ID, GENRE_ID),FOREIGN KEY (BOOK_ID) REFERENCES T_COMMON_LIBRARY_BOOK (CLB_ID_C),FOREIGN KEY (GENRE_ID) REFERENCES T_GENRES (GENRE_ID));

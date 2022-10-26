CREATE TABLE ECATEGORY(
    CID INT PRIMARY KEY,
    CATEGORY VARCHAR(20)
);

CREATE TABLE EPRODUCT(
    EID INT PRIMARY KEY,
    ENAME VARCHAR(200) NOT NULL,
    EPRICE INT DEFAULT 0,
    EREVIEW INT DEFAULT 0,
    ECATEGORY INT DEFAULT 0,
    EIMG VARCHAR(500) NOT NULL
);

CREATE TABLE USERDATA(
	SID INT PRIMARY KEY,
	NAME VARCHAR(200) NOT NULL,
	MID VARCHAR(200) UNIQUE NOT NULL,
	MPW VARCHAR(200) NOT NULL
);

--안씀
CREATE TABLE BUYLIST(
	BID INT PRIMARY KEY,
	BNAME INT,
	BITEM INT
);
--

CREATE TABLE BUYLIST(
    EID INT,
    SID INT
);

INSERT INTO USERDATA VALUES((SELECT NVL(MAX(SID),0)+1 FROM USERDATA),'관리자','ADMIN','1111');

SELECT * FROM USERDATA;

INSERT INTO BUYLIST VALUES((SELECT NVL(MAX(BID),0)+1 FROM BUYLIST),?,?);
UPDATE BUYLIST SET BITEM = ? WHERE BNAME = ?; 

SELECT * FROM USER_TABLES;
SELECT * FROM USERDATA;
SELECT * FROM EPRODUCT;
SELECT * FROM ECATEGORY;

DROP TABLE USERDATA;
DROP TABLE BUYLIST;

INSERT INTO ECATEGORY VALUES((SELECT NVL(MAX(CID),1)+100 FROM ECATEGORY),'과일');
INSERT INTO ECATEGORY VALUES((SELECT NVL(MAX(CID),1)+100 FROM ECATEGORY),'채소');
INSERT INTO ECATEGORY VALUES((SELECT NVL(MAX(CID),1)+100 FROM ECATEGORY),'정육');
INSERT INTO ECATEGORY VALUES((SELECT NVL(MAX(CID),1)+100 FROM ECATEGORY),'수산물');
INSERT INTO ECATEGORY VALUES((SELECT NVL(MAX(CID),1)+100 FROM ECATEGORY),'과자');

SELECT ENAME,CATEGORY FROM EPRODUCT,ECATEGORY WHERE EPRODUCT.ECATEGORY=ECATEGORY.CID;

SELECT BUYLIST.*,USERDATA.NAME,EPRODUCT.* FROM BUYLIST,USERDATA,EPRODUCT WHERE BUYLIST.BNAME=? AND BUYLIST.BNAME=USERDATA.SID  AND BUYLIST.BITEM=EPRODUCT.EID;


DROP TABLE PRODUCT;
DROP TABLE TEST1;
DROP TABLE EMART;

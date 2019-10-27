CREATE TABLE FOREXCODE(
	code  VARCHAR(200),
);


CREATE TABLE APIDETAILS(
	API_TOKEN VARCHAR(200),
	EMAILID VARCHAR(200)
	
);


CREATE TABLE DATADETAILS(
	DATADATE VARCHAR(50),
	DATA VARCHAR(500)	
);

insert into FOREXCODE values ('AUDUSD');
insert into FOREXCODE values ('AUDNZD');
insert into FOREXCODE values ('AUDHKD');
insert into FOREXCODE values ('AUDKRW');
insert into FOREXCODE values ('AUDJPY');

insert into APIDETAILS values ('5dae818f0088a4.65981706','kanchwalazainu@gmail.com,zainu_k15@yahoo.in');
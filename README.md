# forexDataMailApp
Automated delivery of FX Data

FX data is sourced from:
https://eodhistoricaldata.com/knowledgebase/list-supported-currencies/
and delivered as an email file attachment at 8am, 12pm and 4pm each day.
The FX rates of interest are AUDUSD, AUDNZD, AUDHKD, AUDKRW and AUDJPY
The email file attachment will have:
a filename of obsval_YYYYMMDD_HHMM.csv e.g. obsval_20191015_0800.csv
a header row of FOREX, VALUE
a row for each FX pair e.g. AUDUSD, .65

Assumptions:-
1. Fetched real-time data from the API.
2. Fetched data from the parameter closed andsent it to the user in the API
2. The mail is sent to kanchwalazainu@gmail.com and zainu_k15@yahoo.in. To modify the email address, Edit the insert query in the File V2__dbMigrationScript.sql from src/main/resources/db/migration folder.

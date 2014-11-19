#include "filesservice.h"
#include "client.h"
#include <QFile>
#include <QTextStream>

FilesService::FilesService()
{
    serverFileName = "server_address.oscfg";
    keywordsRecordsFileName = "keywords_records.oscfg";
    readKeywordsRecords();
}

QList<KeywordsRecord> FilesService::getSavedKeywordsRecords() {
    return records;
}

QString FilesService::getServerAddressFromFile() {
    QString serverAddress = "http://localhost:8080/api";
    QFile file(serverFileName);
    if(!file.open(QIODevice::ReadOnly)) {
        file.open(QIODevice::WriteOnly);
        QTextStream stream( &file );
        stream << serverAddress;
        file.close();
        return serverAddress;
    }

    QTextStream in(&file);
    serverAddress = in.readLine();
    file.close();

    return serverAddress;
}

bool FilesService::addKeywordsRecordToFile(KeywordsRecord record) {
    int i = 0;
    while(i < records.size() && records[i].getKeywords() != record.getKeywords())
        i++;
    if(i == records.size()){
        records.append(record);
        return saveKeywordsRecords();
    }
    return true;
}

bool FilesService::updateKeywordRecorsDate(KeywordsRecord keywordsRecord) {
   int i = 0;
   while(i < records.size() && records[i].getKeywords() != keywordsRecord.getKeywords())
       i++;
   if(i < records.size()) {
       records[i].setCurrentDate();
       return saveKeywordsRecords();
   }
   return false;
}

/*************Private Methods****************/

void FilesService::readKeywordsRecords() {
    QFile file(keywordsRecordsFileName);
    if(!file.open(QIODevice::ReadOnly)) {
        file.open(QIODevice::WriteOnly);
        file.close();
        return;
    }

    QTextStream in(&file);
    while(!in.atEnd()) {
        QString line = in.readLine();
        if(line != "") {
            QStringList fields = line.split("|");
            records.append(KeywordsRecord(fields[0], QDateTime::fromString(fields[1],"yyyy-MM-dd")));
        }
    }
    file.close();
}

bool FilesService::saveKeywordsRecords() {
    QFile file(keywordsRecordsFileName);
    if(file.open(QIODevice::WriteOnly)) {
        QTextStream stream( &file );
        for(int i=0; i<records.size(); i++) {
            stream << records[i].getKeywords() << "|" << records[i].getDate().toString("yyyy-MM-dd") << endl;
        }
        file.close();
        return true;
    }
    return false;
}

bool FilesService::saveServerAddress(QString serverAddress) {
    QFile file(serverFileName);
    if(file.open(QIODevice::WriteOnly)) {
        QTextStream stream( &file );
        stream << serverAddress;
        file.close();
        return true;
    }
    return false;
}

bool FilesService::clearKeywordsRecords() {
    records.clear();
    return saveKeywordsRecords();
}

/****************Test************************/
void FilesService::test() {
    qDebug()<<"\nTest getServerAddressFromFile()";
    qDebug()<<getServerAddressFromFile();

    //Show records
    qDebug()<<"Records";
    for(int i=0; i<records.size(); i++)
        qDebug()<<records[i].getKeywords()<<" - "<<records[i].getDate().toString("yyyy-MM-dd");

    qDebug()<<"Test addKeywordsRecordsToFile()";
    qDebug()<<addKeywordsRecordToFile(KeywordsRecord("Test test",QDateTime::currentDateTime()));

//    qDebug()<<"Test clearKeywordsRecords()";
//    qDebug()<<clearKeywordsRecords();

}

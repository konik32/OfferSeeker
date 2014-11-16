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
    records.append(record);
    return saveKeywordsRecords();
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
        QStringList fields = line.split("|");
        records.append(KeywordsRecord(fields[0], QDateTime::fromString(fields[1],"yyyy-MM-dd")));
    }
    file.close();
}

bool FilesService::saveKeywordsRecords() {
    //TODO
    return false;
}

/****************Test************************/
void FilesService::test() {
    qDebug()<<"\nTest getServerAddressFromFile()";
    qDebug()<<getServerAddressFromFile();


}

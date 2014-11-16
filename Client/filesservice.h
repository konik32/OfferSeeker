#ifndef FILESSERVICE_H
#define FILESSERVICE_H
#include <QDateTime>
#include "keywordsrecord.h"

class FilesService
{
private:
    QString serverFileName;
    QString keywordsRecordsFileName;
    QList<KeywordsRecord> records;

    void readKeywordsRecords();
    bool saveKeywordsRecords();
public:
    FilesService();
    QString getServerAddressFromFile();
    QList<KeywordsRecord> getSavedKeywordsRecords();
    bool addKeywordsRecordToFile(KeywordsRecord record);
    bool saveServerAddress(QString serverAddress);
    bool clearKeywordsRecords();

    //Test
    void test();
};

#endif // FILESSERVICE_H

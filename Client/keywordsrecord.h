#ifndef KEYWORDSRECORD_H
#define KEYWORDSRECORD_H
#include <QDateTime>

class KeywordsRecord
{
private:
    QString keywords;
    QDateTime date;
    int countNew;
public:
    KeywordsRecord(QString keywords, QDateTime date);
    QString getKeywords();
    QDateTime getDate();
    int getCountNew();
    void setCountNew(int countNew);
};

#endif // KEYWORDSRECORD_H

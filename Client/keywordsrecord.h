#ifndef KEYWORDSRECORD_H
#define KEYWORDSRECORD_H
#include <QDateTime>

class KeywordsRecord
{
private:
    QString keywords;
    QDateTime date;
public:
    KeywordsRecord(QString keywords, QDateTime date);
    QString getKeywords();
    QDateTime getDate();
};

#endif // KEYWORDSRECORD_H

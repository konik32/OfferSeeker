#include "keywordsrecord.h"

KeywordsRecord::KeywordsRecord(QString keywords, QDateTime date) {
    this->keywords = keywords;
    this->date = date;
}

QString KeywordsRecord::getKeywords() { return keywords; }
QDateTime KeywordsRecord::getDate() { return date; }

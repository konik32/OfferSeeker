#include "keywordsrecord.h"

KeywordsRecord::KeywordsRecord() {}
KeywordsRecord::KeywordsRecord(QString keywords, QDateTime date) {
    this->keywords = keywords;
    this->date = date;
    this->countNew = 0;
}

QString KeywordsRecord::getKeywords() { return keywords; }
QDateTime KeywordsRecord::getDate() { return date; }
int KeywordsRecord::getCountNew() { return countNew; }

void KeywordsRecord::setCountNew(int countNew) {
    this->countNew = countNew;
}

void KeywordsRecord::setCurrentDate() {
    this->date = QDateTime::currentDateTime();
}

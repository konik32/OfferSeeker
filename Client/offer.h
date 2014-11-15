#ifndef OFFER_H
#define OFFER_H
#include<QUuid>

class Offer
{
private:
    QUuid id;
    QString description;
    QString url;

public:
    Offer(QUuid, QString, QString);
    QUuid getId();
    QString getDescription();
    QString getUrl();
};

#endif // OFFER_H

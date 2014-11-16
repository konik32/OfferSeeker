#ifndef OFFER_H
#define OFFER_H
#include<QUuid>
#include<QDateTime>

class Offer
{
private:
    QUuid id;
    QString description;
    QString url;
    QDateTime timestamp;


public:
    Offer(QUuid, QString, QString, QDateTime);
    QUuid getId();
    QString getDescription();
    QString getUrl();
    QDateTime getTimestamp();
};

#endif // OFFER_H

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
    Offer();
    Offer(QUuid, QString, QString, QDateTime);
    QUuid getId();
    QString getDescription() const;
    QString getUrl();
    QDateTime getTimestamp() const;

};


#endif // OFFER_H

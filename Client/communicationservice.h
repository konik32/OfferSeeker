#ifndef COMUNICATIONSERVICE_H
#define COMUNICATIONSERVICE_H
#include <QUuid>
#include <QUrl>
#include <QDateTime>
#include "offer.h"
#include "statistic.h"

class CommunicationService
{
private:
    QString serverAddress;
    QString getResponseFromUrl(QUrl url);
    int getResponseCodeFromUrl(QUrl url);

public:
    CommunicationService();
    CommunicationService(QString serverAddress);
    void setServerAddress(QString serverAddress);

    //Statistics
    long getStatisticCount();
    long getStatisticCount(bool isOffer);
    bool setOfferValidation(Offer offer, bool valid);
    bool setOfferValidation(QUuid offerId, bool valid);
    QList<Statistic> getListOfStatistics();
    QList<Statistic> getListOfStatistics(QDateTime startDate, QDateTime endDate);
    QList<Statistic> getListOfStatisticsSince(QDateTime startDate);
    QList<Statistic> getListOfStatisticsOldestBy(QDateTime endDate);

    //Offers
    QList<Offer> getOffers(QString keywords);

};

#endif // COMUNICATIONSERVICE_H

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
    QList<Statistic> getStatisticsListFromJSON(QString jsonString);
    QList<Offer> getOffersListFromJSON(QString jsonString);

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
    QList<Statistic> getListOfStatistics(bool isOffer);
    QList<Statistic> getListOfStatistics(QDateTime startDate, QDateTime endDate);
    QList<Statistic> getListOfStatistics(QDateTime startDate, QDateTime endDate, bool isOffer);
    QList<Statistic> getListOfStatisticsSince(QDateTime startDate);
    QList<Statistic> getListOfStatisticsSince(QDateTime startDate, bool isOffer);
    QList<Statistic> getListOfStatisticsOlderThan(QDateTime endDate);
    QList<Statistic> getListOfStatisticsOlderThan(QDateTime endDate, bool isOffer);

    //Offers
    QList<Offer> getOffers(QString keywords);
    QList<Offer> getOffers(QString keywords, QDateTime timestamp);

    //Test
    void test();

};

#endif // COMUNICATIONSERVICE_H

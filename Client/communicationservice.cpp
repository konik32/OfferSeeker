#include "communicationservice.h"
#include "client.h"
#include "statistic.h"
#include "offer.h"
#include <QJsonDocument>
#include <QJsonObject>
#include <QJsonArray>

CommunicationService::CommunicationService() {
    serverAddress = "http://localhost:8080/api";
}

CommunicationService::CommunicationService(QString serverAddress) {
    this->serverAddress = serverAddress;
}

void CommunicationService::setServerAddress(QString serverAddress) {
    this->serverAddress = serverAddress;
}

long CommunicationService::getStatisticCount() {
    return getResponseFromUrl(QUrl(serverAddress+"/statistics/count")).toLong();
}

long CommunicationService::getStatisticCount(bool isOffer) {
    return getResponseFromUrl(QUrl(serverAddress+"/statistics/count?isOffer="+QString::number(isOffer))).toLong();
}

bool CommunicationService::setOfferValidation(Offer offer, bool valid) {
    return setOfferValidation(offer.getId(), valid);
}

bool CommunicationService::setOfferValidation(QUuid offerId, bool valid) {
    int statusCode = getResponseCodeFromUrl(QUrl(serverAddress+"/offers/"+offerId.toString().mid(1,36).toUpper()+"?isOffer="+QString::number(valid)));
    if(statusCode == 200) return true;
    else return false;
}

QList<Statistic> CommunicationService::getListOfStatistics() {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list"));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatistics(bool isOffer) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?isOffer="+QString::number(isOffer)));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatistics(QDateTime startDate, QDateTime endDate) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?startDate="+startDate.toString("yyyy-MM-dd")+"&endDate="+endDate.toString("yyyy-MM-dd")));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatistics(QDateTime startDate, QDateTime endDate, bool isOffer) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?startDate="+startDate.toString("yyyy-MM-dd")+"&endDate="+endDate.toString("yyyy-MM-dd")+"&isOffer="+QString::number(isOffer)));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatisticsSince(QDateTime startDate) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?startDate="+startDate.toString("yyyy-MM-dd")));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatisticsSince(QDateTime startDate, bool isOffer) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?startDate="+startDate.toString("yyyy-MM-dd")+"&isOffer="+QString::number(isOffer)));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatisticsOlderThan(QDateTime endDate) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?endDate="+endDate.toString("yyyy-MM-dd")));
    return getStatisticsListFromJSON(jsonString);
}

QList<Statistic> CommunicationService::getListOfStatisticsOlderThan(QDateTime endDate, bool isOffer) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/statistics/list?endDate="+endDate.toString("yyyy-MM-dd")+"&isOffer="+QString::number(isOffer)));
    return getStatisticsListFromJSON(jsonString);
}

QList<Offer> CommunicationService::getOffers(QString keywords) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\""));
    return getOffersListFromJSON(jsonString);
}

QList<Offer> CommunicationService::getOffers(QString keywords, QDateTime timestamp) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\"&timestamp="+timestamp.toString("yyyy-MM-dd")));
    return getOffersListFromJSON(jsonString);
}

/*********************Private methods****************************/

QString CommunicationService::getResponseFromUrl(QUrl url){
    QEventLoop eventLoop;
    QNetworkAccessManager mgr;
    QObject::connect(&mgr, SIGNAL(finished(QNetworkReply*)), &eventLoop, SLOT(quit()));
    QNetworkRequest req(url);
    QNetworkReply *reply = mgr.get(req);
    eventLoop.exec(); // blocks stack until "finished()" has been called
    return reply->readAll();
}

int CommunicationService::getResponseCodeFromUrl(QUrl url) {
    QEventLoop eventLoop;
    QNetworkAccessManager mgr;
    QObject::connect(&mgr, SIGNAL(finished(QNetworkReply*)), &eventLoop, SLOT(quit()));
    QNetworkRequest req(url);
    QNetworkReply *reply = mgr.get(req);
    eventLoop.exec(); // blocks stack until "finished()" has been called

    QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
    if ( !statusCode.isValid() ) return -1;
    return statusCode.toInt();
}

QList<Statistic> CommunicationService::getStatisticsListFromJSON(QString jsonString) {
    QList<Statistic> statisticsList;
    QJsonDocument jsonDocument = QJsonDocument::fromJson(jsonString.toUtf8());
    QJsonArray jsonArray = jsonDocument.array();

    for(int i= 0; i< jsonArray.size(); i++) {
        QJsonObject jsonObject = jsonArray[i].toObject();
        QUuid id = QUuid(jsonObject["id"].toString());
        bool valid = jsonObject["offer"].toBool();
        QDateTime date = QDateTime::fromString(jsonObject["validationDate"].toString(), "yyyy-MM-dd");
        statisticsList.append(Statistic(id, valid, date));
    }

    return statisticsList;
}

QList<Offer> CommunicationService::getOffersListFromJSON(QString jsonString) {
    QList<Offer> offersList;

    QJsonDocument jsonDocument = QJsonDocument::fromJson(jsonString.toUtf8());
    QJsonObject jsonMain = jsonDocument.object();
    QJsonArray jsonArray = jsonMain["content"].toArray();

    for(int i= 0; i< jsonArray.size(); i++) {
        QJsonObject jsonObject = jsonArray[i].toObject();
        QUuid id = QUuid(jsonObject["id"].toString());
        QString description = jsonObject["description"].toString();
        QString url = jsonObject["url"].toString();
        QDateTime timestamp = QDateTime::fromString(jsonObject["timestamp"].toString(), "yyyy-MM-dd");
        offersList.append(Offer(id, description, url, timestamp));
    }

    return offersList;
}

/*******************Test*********************************/
void CommunicationService::test() {

    //Test getOffers(keywords)
    QList<Offer> list = getOffers("a");
    qDebug()<<"Test getOffers(keywords)";
    qDebug()<<list.size();
    for(int i=0; i<list.size(); i++) {
        qDebug()<<list[i].getId().toString()+" - "+list[i].getDescription()+" - " + list[i].getUrl() + " - " + list[i].getTimestamp().toString("yyyy-MM-dd");
    }

    //Test getOffers(keywords, timestamp)
    list = getOffers("a", QDateTime::fromString("2014-11-16","yyyy-MM-dd"));
    qDebug()<<"Test getOffers(keywords, timestamp)";
    qDebug()<<list.size();
    for(int i=0; i<list.size(); i++) {
        qDebug()<<list[i].getId().toString()+" - "+list[i].getDescription()+" - " + list[i].getUrl() + " - " + list[i].getTimestamp().toString("yyyy-MM-dd");
    }
}

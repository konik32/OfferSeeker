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
struct cmpByStringLength {
    bool operator()(const Offer & a, const Offer & b) const {
        return a.getTimestamp() < b.getTimestamp();
    }
};
QList<Offer> CommunicationService::getOffers(QString keywords) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\""));
    QList<Offer> list = getOffersListFromJSON(jsonString);
    for(int i=1; i<offersPagesCount; i++){
        jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\"&page="+QString::number(i)));
        list.append(getOffersListFromJSON(jsonString));
    }

        qSort(list.begin(), list.end(), cmpByStringLength());
    return list;
}



QList<Offer> CommunicationService::getOffers(QString keywords, QDateTime timestamp) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\"&timestamp="+timestamp.toString("yyyy-MM-dd")));    
    QList<Offer> list = getOffersListFromJSON(jsonString);
    for(int i=1; i<offersPagesCount; i++){
        jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\"&timestamp="+timestamp.toString("yyyy-MM-dd")+"&page="+QString::number(i)));
        list.append(getOffersListFromJSON(jsonString));
    }
    qSort(list.begin(), list.end(), cmpByStringLength());
    return list;
}

bool CommunicationService::addDomain(QString url) {
    int statusCode = getResponseCodeFromUrl(QUrl(serverAddress+"/domains?url="+url));
    if(statusCode == 200) return true;
    else return false;
}

int CommunicationService::getOffersTotalElementsCount(QString keywords) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\""));
    return getOffersTotalElementsFromJSON(jsonString);
}

int CommunicationService::getOffersTotalElementsCount(QString keywords, QDateTime timestamp) {
    QString jsonString = getResponseFromUrl(QUrl(serverAddress+"/offers?keywords=\""+keywords+"\"&timestamp="+timestamp.toString("yyyy-MM-dd")));
    return getOffersTotalElementsFromJSON(jsonString);
}

QList<KeywordsRecord> CommunicationService::updateCounterNewInKeywordsRecords(QList<KeywordsRecord> records) {
    for(int i=0; i<records.size(); i++) {
        records[i].setCountNew(getOffersTotalElementsCount(records[i].getKeywords(),records[i].getDate()));
    }
    return records;
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
    QMultiMap<QDateTime, Offer> offerMap;
    QList<Offer> offersList;

    QJsonDocument jsonDocument = QJsonDocument::fromJson(jsonString.toUtf8());
    QJsonObject jsonMain = jsonDocument.object();
    QJsonArray jsonArray = jsonMain["content"].toArray();
    offersPagesCount = jsonMain["totalPages"].toInt();

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

int CommunicationService::getOffersTotalElementsFromJSON(QString jsonString) {
    QJsonDocument jsonDocument = QJsonDocument::fromJson(jsonString.toUtf8());
    QJsonObject jsonMain = jsonDocument.object();
    return jsonMain["totalElements"].toInt();
}

/*******************Test*********************************/
void CommunicationService::test() {
    //Test setOfferValidation(QUuid offerId, bool valid)
    qDebug()<<"\nTest setOfferValidation(QUuid offerId, bool valid)";
    qDebug()<<setOfferValidation(QUuid::createUuid(), true);
    qDebug()<<setOfferValidation(QUuid::createUuid(), false);

    //Test getStatisticCount(...)
    qDebug()<<"\nTest getStatisticCount(...) x3";
    qDebug()<<getStatisticCount();
    qDebug()<<getStatisticCount(true);
    qDebug()<<getStatisticCount(false);

    //Test getListOfStatistics(...)
    qDebug()<<"\nTest getListOfStatistics()";
    QList<Statistic> listS = getListOfStatistics();
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatistics(isOffer)";
    listS = getListOfStatistics(true);
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatistics(startDate, endDate)";
    listS = getListOfStatistics(QDateTime::fromString("2014-11-15","yyyy-MM-dd"), QDateTime::fromString("2014-11-16","yyyy-MM-dd"));
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatistics(startDate, endDate, isOffer)";
    listS = getListOfStatistics(QDateTime::fromString("2014-11-15","yyyy-MM-dd"), QDateTime::fromString("2014-11-16","yyyy-MM-dd"), true);
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatisticsSince(startDate)";
    listS = getListOfStatisticsSince(QDateTime::fromString("2014-11-15","yyyy-MM-dd"));
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatisticsSince(startDate, isOffer)";
    listS = getListOfStatisticsSince(QDateTime::fromString("2014-11-15","yyyy-MM-dd"), true);
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatisticsOlderThan(endDate)";
    listS = getListOfStatisticsOlderThan(QDateTime::fromString("2014-11-15","yyyy-MM-dd"));
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    qDebug()<<"\nTest getListOfStatisticsOlderThan(endDate, isOffer)";
    listS = getListOfStatisticsOlderThan(QDateTime::fromString("2014-11-15","yyyy-MM-dd"), true);
    qDebug()<<listS.size();
    for(int i=0; i<listS.size(); i++) {
        qDebug()<<listS[i].getId().toString()+" - "+ listS[i].getValidationDate().toString("yyyy-MM-dd")+" - "<<listS[i].isValid();
    }

    //Test getOffers(keywords)
    QList<Offer> list = getOffers("i");
    qDebug()<<"\nTest getOffers(keywords)";
    qDebug()<<list.size();
    for(int i=0; i<list.size(); i++) {
        qDebug()<<list[i].getId().toString()+" - "+list[i].getDescription().left(20)+" - " + list[i].getUrl() + " - " + list[i].getTimestamp().toString("yyyy-MM-dd");
    }

    //Test getOffers(keywords, timestamp)
    list = getOffers("i", QDateTime::fromString("2014-11-16","yyyy-MM-dd"));
    qDebug()<<"\nTest getOffers(keywords, timestamp)";
    qDebug()<<list.size();
    for(int i=0; i<list.size(); i++) {
        qDebug()<<list[i].getId().toString()+" - "+list[i].getDescription().left(20)+" - " + list[i].getUrl() + " - " + list[i].getTimestamp().toString("yyyy-MM-dd");
    }

    //Test addDomain(url)
    qDebug()<<"\nTest addDomain(url)";
    qDebug()<<addDomain("http://www.allegro.pl");

    //Test getOffersTotalElementsCount(...)
    qDebug()<<"\nTest getOffersTotalElementsCount(...)";
    qDebug()<<getOffersTotalElementsCount("i");
    qDebug()<<getOffersTotalElementsCount("i", QDateTime::fromString("2014-11-16", "yyyy-MM-dd"));

    //Test updateCounterNewInKeywordsRecords(...)
    qDebug()<<"\nTest updateCounterNewInKeywordsRecords(...)";
    QList<KeywordsRecord> testList;
    testList.append(KeywordsRecord("i", QDateTime::currentDateTime()));
    testList.append(KeywordsRecord("a", QDateTime::currentDateTime()));
    for(int i=0; i<testList.size(); i++)
       qDebug()<<testList[i].getCountNew();
    testList = updateCounterNewInKeywordsRecords(testList);
    for(int i=0; i<testList.size(); i++)
       qDebug()<<testList[i].getCountNew();
}

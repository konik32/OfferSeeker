#include "communicationservice.h"
#include "client.h"

CommunicationService::CommunicationService() {
    serverAddress = "http://localhost:8080/api";
}

CommunicationService::CommunicationService(QString serverAddress) {
    this->serverAddress = serverAddress;
}

void CommunicationService::setServerAddress(QString serverAddress) {
    this->serverAddress = serverAddress;
}

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
    int statusCode = getResponseCodeFromUrl(QUrl(serverAddress+"/offers/"+offerId.toString()+"?isOffer="+QString::number(valid)));
    if(statusCode == 200) return true;
    else return false;
}

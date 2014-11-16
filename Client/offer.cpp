#include "offer.h"

Offer::Offer(QUuid id, QString description, QString url, QDateTime timestamp)
{
    this->id = id;
    this->description = description;
    this->url = url;
    this->timestamp = timestamp;
}

QUuid Offer::getId() { return id; }
QString Offer::getDescription() { return description; }
QString Offer::getUrl() { return url; }
QDateTime Offer::getTimestamp() { return timestamp; }

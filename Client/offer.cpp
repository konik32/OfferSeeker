#include "offer.h"

Offer::Offer(QUuid id, QString description, QString url)
{
    this->id = id;
    this->description = description;
    this->url = url;
}

QUuid Offer::getId() { return id; }
QString Offer::getDescription() { return description; }
QString Offer::getUrl() { return url; }

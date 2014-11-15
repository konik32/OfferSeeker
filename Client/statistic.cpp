#include "statistic.h"

Statistic::Statistic(QUuid id, bool valid, QDateTime validationDate){
    this->id = id;
    this->valid = valid;
    this->validationDate = validationDate;
}

QUuid Statistic::getId(){ return id; }
bool Statistic::isValid(){ return valid; }
QDateTime Statistic::getValidationDate(){ return validationDate; }

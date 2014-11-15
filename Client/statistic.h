#ifndef STATISTIC_H
#define STATISTIC_H
#include <QUuid>
#include <QDateTime>

class Statistic
{
private:
    QUuid id;
    bool valid;
    QDateTime validationDate;
public:
    Statistic(QUuid, bool, QDateTime);
    QUuid getId();
    bool isValid();
    QDateTime getValidationDate();
};

#endif // STATISTIC_H

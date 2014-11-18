#ifndef STATISTICSERVICE_H
#define STATISTICSERVICE_H
#include <QVector>
#include "statistic.h"

class StatisticGraphData
{
private:
    QVector<double> times;
    QVector<double> values;
    double minDate, maxDate;
    double maxValue;
    void test();
public:
    StatisticGraphData(QList<Statistic>);
    StatisticGraphData(QList<Statistic>,QDateTime,QDateTime);
    QVector<double> getTimes();
    QVector<double> getValues();
    double getMinDate();
    double getMaxDate();
    double getMaxValue();
};

#endif // STATISTICSERVICE_H

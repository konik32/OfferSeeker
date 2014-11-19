#include "statisticgraphdata.h"
#include <QMap>
#include <QDebug>

StatisticGraphData::StatisticGraphData(QList<Statistic> statistics)
{
    QDateTime maxTempDate;
    QDateTime minTempDate;
    maxValue = 0;
    QMap<QDateTime, int> statisticsMap;

    for(int i=0; i<statistics.size(); i++) {
        if(i == 0) {
            maxTempDate = statistics[0].getValidationDate();
            minTempDate = statistics[0].getValidationDate();
            statisticsMap[statistics[0].getValidationDate()]++;
            maxValue = 1;
        }
        else{
            if(statistics[i].getValidationDate() < minTempDate)
                minTempDate = statistics[i].getValidationDate();
            if(statistics[i].getValidationDate() > maxTempDate)
                maxTempDate = statistics[i].getValidationDate();
            statisticsMap[statistics[i].getValidationDate()]++;
            if(statisticsMap[statistics[i].getValidationDate()] > maxValue)
                maxValue = statisticsMap[statistics[i].getValidationDate()];
        }
    }

    maxDate = maxTempDate.toTime_t();
    minDate = minTempDate.toTime_t();

    int tableSize = (maxDate-minDate) / 86400 + 1; //days counter

    for(int i=0; i<tableSize; i++) {
        times.append(minDate+(i*86400));
        values.append(0);
    }

    foreach(QDateTime key, statisticsMap.keys() ) {
        int index = (key.toTime_t()-minDate) / 86400;
        values[index] = statisticsMap[key];
    }
}

StatisticGraphData::StatisticGraphData(QList<Statistic> statistics, QDateTime startDate, QDateTime endDate)
{
    QDateTime maxTempDate = startDate;
    QDateTime minTempDate = endDate;
    maxValue = 0;
    QMap<QDateTime, int> statisticsMap;

    for(int i=0; i<statistics.size(); i++) {
        if(statistics[i].getValidationDate() < minTempDate)
            minTempDate = statistics[i].getValidationDate();
        if(statistics[i].getValidationDate() > maxTempDate)
            maxTempDate = statistics[i].getValidationDate();
        statisticsMap[statistics[i].getValidationDate()]++;
        if(statisticsMap[statistics[i].getValidationDate()] > maxValue)
            maxValue = statisticsMap[statistics[i].getValidationDate()];
    }

    maxDate = maxTempDate.toTime_t();
    minDate = minTempDate.toTime_t();

    if(endDate.toTime_t() + 86400 > QDateTime::currentDateTime().toTime_t()) maxDate = QDateTime::currentDateTime().toTime_t();

    int tableSize = (maxDate-minDate) / 86400 + 1; //days counter

    for(int i=0; i<tableSize; i++) {
        times.append(minDate+(i*86400));
        values.append(0);
    }

    foreach(QDateTime key, statisticsMap.keys() ) {
        int index = (key.toTime_t()-minDate) / 86400;
        values[index] = statisticsMap[key];
    }
}

QVector<double> StatisticGraphData::getTimes() { return times; }
QVector<double> StatisticGraphData::getValues() { return values; }
double StatisticGraphData::getMinDate() { return minDate; }
double StatisticGraphData::getMaxDate() { return maxDate; }
double StatisticGraphData::getMaxValue() { return maxValue; }


/*********************Test*********************************/
void StatisticGraphData::test() {
    for(int i=0; i<times.size(); i++) {
        qDebug()<<times[i]<<" - "<<values[i];
    }
    qDebug()<<maxDate<<" - "<<minDate<<" - "<<maxValue;
}

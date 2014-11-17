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

    foreach(QDateTime key, statisticsMap.keys() ) {
        times.append(key.toTime_t());
        values.append(statisticsMap[key]);
    }

    maxDate = maxTempDate.toTime_t();
    minDate = minTempDate.toTime_t();
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

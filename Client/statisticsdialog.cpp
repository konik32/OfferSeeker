#include "statisticsdialog.h"
#include "ui_statisticsdialog.h"
#include "statistic.h"
#include "statisticgraphdata.h"

StatisticsDialog::StatisticsDialog(QWidget *parent, CommunicationService* communicationService) :
    QDialog(parent),
    ui(new Ui::StatisticsDialog)
{
    ui->setupUi(this);
    ui->customPlot->setLocale(QLocale(QLocale::Polish, QLocale::Poland));
    ui->customPlot->setBackground(QBrush(QColor(235,235,235,255)));
    this->communicationService = communicationService;
    setStartPack();
    updatePlot();
    connect(ui->isoffer_rbtn_true,SIGNAL(clicked()),this,SLOT(updatePlot()));
    connect(ui->isoffer_rbtn_false,SIGNAL(clicked()),this,SLOT(updatePlot()));
    connect(ui->startdateEdit,SIGNAL(userDateChanged(QDate)),this,SLOT(updatePlot()));
    connect(ui->enddateEdit,SIGNAL(userDateChanged(QDate)),this,SLOT(updatePlot()));
}

StatisticsDialog::~StatisticsDialog()
{
    delete ui;
}

void StatisticsDialog::setStartPack(){
    QDate date = QDate::currentDate();
    ui->startdateEdit->setDate(date);
    ui->enddateEdit->setDate(date);
    ui->isoffer_rbtn_false->setEnabled(false);
    ui->isoffer_rbtn_true->setEnabled(false);
    ui->startdateEdit->setEnabled(false);
    ui->enddateEdit->setEnabled(false);
}

void StatisticsDialog::on_startDate_cbox_clicked(){
    if(ui->startDate_cbox->isChecked()){
        ui->startdateEdit->setEnabled(true);
    }
    else{
        ui->startdateEdit->setEnabled(false);
    }
    updatePlot();
}


void StatisticsDialog::on_endDate_cbox_clicked(){
    if(ui->endDate_cbox->isChecked()){
        ui->enddateEdit->setEnabled(true);
    }
    else{
        ui->enddateEdit->setEnabled(false);
    }
    updatePlot();
}

void StatisticsDialog::on_isoffer_cbox_clicked(){
    if(ui->isoffer_cbox->isChecked()){
        ui->isoffer_rbtn_false->setEnabled(true);
        ui->isoffer_rbtn_true->setEnabled(true);
    }
    else{
        ui->isoffer_rbtn_true->setEnabled(false);
        ui->isoffer_rbtn_false->setEnabled(false);
    }
    updatePlot();
}

void StatisticsDialog::updatePlot()
{
    //initial
    bool isOfferLimited = ui->isoffer_cbox->isChecked();
    bool onlyTrue = ui->isoffer_rbtn_true->isChecked();
    bool onlyFalse = ui->isoffer_rbtn_false->isChecked();
    bool isStartDateChecked = ui->startDate_cbox->isChecked();
    bool isEndDateChecked = ui->endDate_cbox->isChecked();
    QDateTime startDate = QDateTime::fromString(ui->startdateEdit->text(), "yyyy-MM-dd");
    QDateTime endDate = QDateTime::fromString(ui->enddateEdit->text(), "yyyy-MM-dd");

    //Plotting
    QList<Statistic> statisticsTrue;
    QList<Statistic> statisticsFalse;

    if(!isOfferLimited || (isOfferLimited && onlyTrue)) {
        if(isStartDateChecked && isEndDateChecked)
            statisticsTrue = communicationService->getListOfStatistics(startDate, endDate, true);
        else if(isStartDateChecked)
            statisticsTrue = communicationService->getListOfStatisticsSince(startDate, true);
        else if(isEndDateChecked)
            statisticsTrue = communicationService->getListOfStatisticsOlderThan(endDate, true);
        else
            statisticsTrue = communicationService->getListOfStatistics(true);
    }

    if(!isOfferLimited || (isOfferLimited && onlyFalse)) {
        if(isStartDateChecked && isEndDateChecked)
            statisticsFalse = communicationService->getListOfStatistics(startDate, endDate, false);
        else if(isStartDateChecked)
            statisticsFalse = communicationService->getListOfStatisticsSince(startDate, false);
        else if(isEndDateChecked)
            statisticsFalse = communicationService->getListOfStatisticsOlderThan(endDate, false);
        else
            statisticsFalse = communicationService->getListOfStatistics(false);
    }

    StatisticGraphData* graphDataTrue;
    StatisticGraphData* graphDataFalse;

    if(!isOfferLimited || (isOfferLimited && onlyTrue))
        graphDataTrue = new StatisticGraphData(statisticsTrue, startDate, endDate);
    if(!isOfferLimited || (isOfferLimited && onlyFalse))
        graphDataFalse = new StatisticGraphData(statisticsFalse, startDate, endDate);

    double maxData;
    double minData;
    double maxValue;

    if(!isOfferLimited) {
        if(!isEndDateChecked)
            maxData = (graphDataTrue->getMaxDate() > graphDataFalse->getMaxDate())?graphDataTrue->getMaxDate():graphDataFalse->getMaxDate();
        else
            maxData = endDate.toTime_t();
        if(!isStartDateChecked)
            minData = (graphDataTrue->getMinDate() > graphDataFalse->getMinDate())?graphDataTrue->getMinDate():graphDataFalse->getMinDate();
        else
            minData = startDate.toTime_t();
        maxValue = (graphDataTrue->getMaxValue() > graphDataFalse->getMaxValue())?graphDataTrue->getMaxValue():graphDataFalse->getMaxValue();
    }
    else if(isOfferLimited && onlyTrue) {
        maxData = (!isEndDateChecked)?graphDataTrue->getMaxDate():endDate.toTime_t();
        minData = (!isStartDateChecked)?graphDataTrue->getMinDate():startDate.toTime_t();
        maxValue = graphDataTrue->getMaxValue();
    }
    else if(isOfferLimited && onlyFalse) {
        maxData = (!isEndDateChecked)?graphDataFalse->getMaxDate():endDate.toTime_t();
        minData = (!isStartDateChecked)?graphDataFalse->getMinDate():startDate.toTime_t();
        maxValue = graphDataFalse->getMaxValue();
    }

    //Plot generating
    ui->customPlot->clearGraphs();

    //true plot
    if(!isOfferLimited || (isOfferLimited && onlyTrue)) {
        QVector<double> time = graphDataTrue->getTimes(), value = graphDataTrue->getValues();
        ui->customPlot->addGraph();
        ui->customPlot->graph()->setPen(QPen(Qt::darkGreen));
        ui->customPlot->graph()->setName("Pozytywne");
        ui->customPlot->graph()->setData(time, value);
    }

    //false plot
    if(!isOfferLimited || (isOfferLimited && onlyFalse)) {
        QVector<double> time2 = graphDataFalse->getTimes(), value2 = graphDataFalse->getValues();
        ui->customPlot->addGraph();
        ui->customPlot->graph()->setPen(QPen(Qt::darkRed));
        ui->customPlot->graph()->setName("Negatywne");
        ui->customPlot->graph()->setData(time2, value2);
    }

    if((maxData-minData)/86400 > 5)
        ui->customPlot->xAxis->setAutoTickStep(true);
    else {
        ui->customPlot->xAxis->setAutoTickStep(false);
        ui->customPlot->xAxis->setTickStep(86400); // one day in seconds 86400
        ui->customPlot->xAxis->setSubTickCount(3);
    }
    ui->customPlot->xAxis->setTickLabelType(QCPAxis::ltDateTime);
    ui->customPlot->xAxis->setDateTimeFormat("dd MMMM\nyyyy");
    ui->customPlot->xAxis->setLabel("Data");
    ui->customPlot->yAxis->setLabel("Ilość ocen");
    ui->customPlot->xAxis->setRange(minData-21600, maxData+21600);
    ui->customPlot->yAxis->setRange(0, maxValue+1);
    ui->customPlot->legend->setVisible(true);
    ui->customPlot->replot();

    if(!isEndDateChecked)
        ui->enddateEdit->setDateTime(QDateTime::fromTime_t(maxData));
    if(!isStartDateChecked)
        ui->startdateEdit->setDateTime(QDateTime::fromTime_t(minData));

    if(!isOfferLimited || (isOfferLimited && onlyTrue))
        delete graphDataTrue;
    if(!isOfferLimited || (isOfferLimited && onlyFalse))
        delete graphDataFalse;
}

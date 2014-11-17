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
    setStartPack();
    this->communicationService = communicationService;
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
}


void StatisticsDialog::on_endDate_cbox_clicked(){
    if(ui->endDate_cbox->isChecked()){
        ui->enddateEdit->setEnabled(true);
    }
    else{
        ui->enddateEdit->setEnabled(false);
    }
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
}

void StatisticsDialog::on_stat_btn_clicked()
{
    QList<Statistic> statisticsTrue = communicationService->getListOfStatistics(true);
    QList<Statistic> statisticsFalse = communicationService->getListOfStatistics(false);

    StatisticGraphData* graphDataTrue = new StatisticGraphData(statisticsTrue);
    StatisticGraphData* graphDataFalse = new StatisticGraphData(statisticsFalse);

    double maxData = (graphDataTrue->getMaxDate() > graphDataFalse->getMaxDate())?graphDataTrue->getMaxDate():graphDataFalse->getMaxDate();
    double minData = (graphDataTrue->getMinDate() > graphDataFalse->getMinDate())?graphDataTrue->getMinDate():graphDataFalse->getMinDate();
    double maxValue = (graphDataTrue->getMaxValue() > graphDataFalse->getMaxValue())?graphDataTrue->getMaxValue():graphDataFalse->getMaxValue();

    QVector<double> time = graphDataTrue->getTimes(), value = graphDataTrue->getValues();
    QVector<double> time2 = graphDataFalse->getTimes(), value2 = graphDataFalse->getValues();

//    delete graphDataTrue;
//    delete graphDataFalse;

    //Plot generating
    ui->customPlot->clearGraphs();
    srand(8);

    //true plot
    int gi=0;
    ui->customPlot->addGraph();
    ui->customPlot->graph()->setPen(QPen(Qt::darkGreen));
//    ui->customPlot->graph()->setBrush(QBrush(QColor(0,140,0,150)));
    ui->customPlot->graph()->setName("Pozytywne");
    ui->customPlot->graph()->setData(time, value);

    //false plot
    gi++;
    ui->customPlot->addGraph();
    ui->customPlot->graph()->setPen(QPen(Qt::darkRed));
//    ui->customPlot->graph()->setBrush(QBrush(QColor(215,0,0,150)));
    ui->customPlot->graph()->setName("Negatywne");
    ui->customPlot->graph()->setData(time2, value2);

    ui->customPlot->xAxis->setAutoTickStep(false);
    ui->customPlot->xAxis->setTickStep(86400); // one month in seconds
    ui->customPlot->xAxis->setSubTickCount(3);
    ui->customPlot->xAxis->setTickLabelType(QCPAxis::ltDateTime);
    ui->customPlot->xAxis->setDateTimeFormat("dd MMMM\nyyyy");
    ui->customPlot->xAxis->setLabel("Data");
    ui->customPlot->yAxis->setLabel("Ilość ocen");
    ui->customPlot->xAxis->setRange(minData-21600, maxData+21600);
    ui->customPlot->yAxis->setRange(0, maxValue+1);
    ui->customPlot->legend->setVisible(true);
    ui->customPlot->replot();
}

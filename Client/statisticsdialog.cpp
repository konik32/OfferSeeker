#include "statisticsdialog.h"
#include "ui_statisticsdialog.h"

StatisticsDialog::StatisticsDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::StatisticsDialog)
{
    ui->setupUi(this);
    ui->customPlot->setLocale(QLocale(QLocale::Polish, QLocale::Poland));
    setStartPack();
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
    ui->customPlot->clearGraphs();
    double now = QDateTime::currentDateTime().toTime_t();
    srand(8);
    for (int gi=0; gi<2; ++gi)
    {
        ui->customPlot->addGraph();
        QPen pen;
        pen.setColor(QColor(0, 0, 255, 200));
        ui->customPlot->graph()->setLineStyle(QCPGraph::lsLine);
        ui->customPlot->graph()->setPen(pen);
        ui->customPlot->graph()->setBrush(QBrush(QColor(255/4.0*gi,160,50,150)));
        ui->customPlot->graph()->setName("Test"+QString::number(gi));

        QVector<double> time(250), value(250);
        for (int i=0; i<250; ++i)
        {
            time[i] = now + 24*3600*i;
            if (i == 0)
                value[i] = (i/50.0+1)*(rand()/(double)RAND_MAX-0.5);
            else
                value[i] = fabs(value[i-1])*(1+0.02/4.0*(4-gi)) + (i/50.0+1)*(rand()/(double)RAND_MAX-0.5);
        }
        ui->customPlot->graph()->setData(time, value);
    }
    ui->customPlot->xAxis->setTickLabelType(QCPAxis::ltDateTime);
    ui->customPlot->xAxis->setDateTimeFormat("dd MMMM\nyyyy");
    ui->customPlot->xAxis->setLabel("Data");
    ui->customPlot->yAxis->setLabel("Ilość ocen");
    ui->customPlot->xAxis->setRange(now, now+24*3600*249);
    ui->customPlot->yAxis->setRange(0, 60);
    ui->customPlot->legend->setVisible(true);
    ui->customPlot->replot();
}

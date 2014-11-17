#include "statisticsdialog.h"
#include "ui_statisticsdialog.h"

StatisticsDialog::StatisticsDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::StatisticsDialog)
{
    ui->setupUi(this);
    setCurrentDate();
    ui->startdateEdit->setEnabled(false);
    ui->enddateEdit->setEnabled(false);
}

StatisticsDialog::~StatisticsDialog()
{
    delete ui;
}

void StatisticsDialog::setCurrentDate(){
    QDate date = QDate::currentDate();
    ui->startdateEdit->setDate(date);
    ui->enddateEdit->setDate(date);
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

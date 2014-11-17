#include "statisticsdialog.h"
#include "ui_statisticsdialog.h"

StatisticsDialog::StatisticsDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::StatisticsDialog)
{
    ui->setupUi(this);
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

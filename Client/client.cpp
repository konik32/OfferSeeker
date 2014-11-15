#include "client.h"
#include "ui_client.h"

Client::Client(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Client)
{
    ui->setupUi(this);
    communicationService = new CommunicationService();
}

Client::~Client()
{
    delete ui;
}

void Client::on_pushButton_2_clicked()
{
    ui->lineEdit->setText("");
}

void Client::on_pushButton_clicked(){
    ui->textEdit->setText(QString::number(communicationService->getStatisticCount(true)));

}

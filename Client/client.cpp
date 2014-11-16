#include "client.h"
#include "ui_client.h"

Client::Client(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Client)
{
    ui->setupUi(this);
    communicationService = new CommunicationService("http://192.168.200.100:8080/api");
    model = new QStandardItemModel(ui->listView);
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
    offers = communicationService->getOffers(ui->lineEdit->text());
    //qDebug()<<offers[0].getDescription();

    for(int i=0; i<offers.size(); i++){
        QStandardItem *item = new QStandardItem();
        item->setData(QFont("Segoe UI", 12), Qt::FontRole);
        item->setData(offers[i].getId());
        item->setText(offers[i].getDescription().left(35)+"...");
        model->appendRow(item);
    }

    ui->listView->setModel(model);



    //ui->textEdit->setText(QString::number(communicationService->getStatisticCount(true)));
}

#include "client.h"
#include "ui_client.h"
#include "statisticsdialog.h"

Client::Client(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Client)
{
    ui->setupUi(this);
    filesService = new FilesService();
    communicationService = new CommunicationService(filesService->getServerAddressFromFile());
    ui->listView->setSelectionMode(QAbstractItemView::SingleSelection);
    model = new QStandardItemModel(ui->listView);
    model2 = new QStandardItemModel(20,2,this);
    observOffers();
}


Client::~Client()
{
    delete ui;
    delete filesService;
    delete communicationService;
    offers.clear();
}

void Client::on_pushButton_3_clicked(){
    if(ui->textEdit->toPlainText() != "" && ui->textEdit->toPlainText() != "Podaj czego szukasz")
    {
        if(communicationService->setOfferValidation(offers[current].getId(), false)){
            ui->status->setText("Dziękujemy za informację");
        }
    }
}

void Client::on_pushButton_2_clicked()
{
    if(ui->textEdit->toPlainText() != "" && ui->textEdit->toPlainText() != "Podaj czego szukasz")
    {
        if(communicationService->setOfferValidation(offers[current].getId(), true)){
            ui->status->setText("Dziękujemy za informację");
        }
    }
}

void Client::on_pushButton_clicked(){
    if(ui->lineEdit->text() != ""){
        offers = communicationService->getOffers(ui->lineEdit->text());
        model->clear();
        ui->url->clear();
        ui->status->clear();
        ui->textEdit->clear();

        if(!offers.empty()){
            for(int i=0; i<offers.size(); i++){
                QStandardItem *item = new QStandardItem();
                item->setData(QFont("Segoe UI", 12), Qt::FontRole);
                item->setData(offers[i].getId());
                item->setText(offers[i].getDescription().left(30)+"...");
                model->appendRow(item);
            }
            ui->listView->setModel(model);
        }
        else{
            ui->textEdit->setText("Brak Ofert");
        }
    }
    else{
        model->clear();
        ui->textEdit->setText("Podaj czego szukasz");
    }
}

void Client::on_listView_clicked(){
    QModelIndex index = ui->listView->selectionModel()->currentIndex();
    current= index.row();
    ui->textEdit->setText(offers[current].getDescription());
    ui->url->setText(offers[current].getUrl());
}

void Client::on_observ_clicked(){
    QModelIndex index = ui->observ->selectionModel()->currentIndex();
    current= index.row();
    ui->lineEdit->setText(keyRecords[current].getKeywords());
    on_pushButton_clicked();
}

void Client::on_przejdz_clicked(){
    QString link = ui->url->text();
    QDesktopServices::openUrl(QUrl(link));
}

void Client::on_dodaj_clicked(){
    filesService->addKeywordsRecordToFile(KeywordsRecord(ui->lineEdit->text(), QDateTime::currentDateTime()));
    observOffers();
}

void Client::on_clearBtn_clicked(){
    model2->clear();
}


void Client::observOffers(){
    keyRecords = communicationService->updateCounterNewInKeywordsRecords(filesService->getSavedKeywordsRecords());
    model2->clear();

    int i;
    QString data;

    if(!keyRecords.empty()){
        for(i=0; i<keyRecords.size(); i++){
            data.clear();
            QStandardItem *item = new QStandardItem();
            item->setData(QFont("Segoe UI", 12), Qt::FontRole);
            data.append(QString::number(keyRecords[i].getCountNew()));
            item->setText(data.leftJustified(10, 32)+"   "+keyRecords[i].getKeywords().left(30));
            model2->appendRow(item);
        }
        ui->observ->setModel(model2);
    }
    else{
        QStandardItem *item = new QStandardItem();
        item->setData(QFont("Segoe UI", 12), Qt::FontRole);
        item->setText("Brak nowych ofert");
        model2->appendRow(item);
        ui->observ->setModel(model2);
    }
}

void Client::on_stat_btn_clicked()
{
    StatisticsDialog statisticsDialog;
    statisticsDialog.setModal(true);
    statisticsDialog.exec();
}

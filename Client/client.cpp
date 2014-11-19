#include "client.h"
#include "ui_client.h"

Client::Client(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::Client)
{
    ui->setupUi(this);
    this->setWindowFlags(windowFlags() & ~Qt::WindowContextHelpButtonHint);
    filesService = new FilesService();
    communicationService = new CommunicationService(filesService->getServerAddressFromFile());
    statisticsDialog = new StatisticsDialog(this, communicationService);
    settingsDialog = new SettingsDialog(this, communicationService, filesService);
    connect(ui->lineEdit,SIGNAL(editingFinished()),this,SLOT(on_przejdz_clicked()));

    ui->listView->setSelectionMode(QAbstractItemView::SingleSelection);
    model = new QStandardItemModel(ui->listView);
    model2 = new QStandardItemModel(20,2,this);
    observOffers();
    this->setStyleSheet("QLabel{color: white;} QPushButton {color: #eeeeee; border: 1px solid #8f8f91;border-radius: 2px;background-color:  qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(34, 34, 34, 255), stop:1 rgba(94, 94, 94, 255));min-width: 80px;}QPushButton:pressed {background-color: qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(79, 79, 79, 255), stop:1 rgba(145, 145, 145, 255));color: #dddddd;}QPushButton:flat {border: none;} QPushButton:default {border-color: white;} QPushButton:disabled {color: #444444; background: qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(79, 79, 79, 255), stop:1 rgba(145, 145, 145, 255));}");
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
            ui->pushButton_3->setEnabled(false);
            ui->pushButton_2->setEnabled(false);
            on_pushButton_clicked();
        }
    }
}

void Client::on_pushButton_2_clicked()
{
    if(ui->textEdit->toPlainText() != "" && ui->textEdit->toPlainText() != "Podaj czego szukasz")
    {
        if(communicationService->setOfferValidation(offers[current].getId(), true)){
            ui->status->setText("Dziękujemy za informację");
            ui->pushButton_2->setEnabled(false);
            ui->pushButton_3->setEnabled(false);
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
            for(int i=offers.size()-1; i>=0; i--){
                QStandardItem *item = new QStandardItem();
                item->setData(QFont("Segoe UI", 12), Qt::FontRole);
                item->setData(offers[i].getId());

                QString desc = offers[i].getDescription().left(30)+"...";
                item->setText(desc);
                if (offers[i].getTimestamp() == QDateTime::fromString("2014-11-17", "yyyy-MM-dd" )) {
                    item->setFont(QFont("Segoe UI", 12, QFont::Bold));
                    item->setBackground(QBrush(QColor(50,50,50)));
                }


                model->appendRow(item);
            }
            ui->listView->setModel(model);
        }
        else{
            ui->textEdit->setText("Brak Ofert");
        }

        filesService->updateKeywordRecorsDate(KeywordsRecord(ui->lineEdit->text(),QDateTime::currentDateTime()));
        observOffers();
    }
    else{
        model->clear();
        ui->textEdit->setText("Podaj czego szukasz");
    }
}

void Client::on_listView_clicked(){
    QModelIndex index = ui->listView->selectionModel()->currentIndex();
    current= offers.size()-index.row() - 1;
    QString dateText = "\n\nData znalezienia oferty: "+offers[current].getTimestamp().toString("yyyy-MM-dd");
    ui->textEdit->setText(offers[current].getDescription()+dateText);
    ui->url->setText(offers[current].getUrl());
    ui->status->clear();
    ui->pushButton_2->setEnabled(true);
    ui->pushButton_3->setEnabled(true);
}

void Client::on_observ_clicked(){
    QModelIndex index = ui->observ->selectionModel()->currentIndex();
    current= index.row();
    if(!keyRecords.isEmpty()) {
        ui->lineEdit->setText(keyRecords[current].getKeywords());
        filesService->updateKeywordRecorsDate(keyRecords[current]);
        observOffers();
    }
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
    filesService->clearKeywordsRecords();
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
        item->setText("Brak obserwowanych ofert");
        model2->appendRow(item);
        ui->observ->setModel(model2);
    }
}

void Client::on_stat_btn_clicked()
{
    statisticsDialog->setModal(true);
    statisticsDialog->updatePlot();
    statisticsDialog->exec();
}

void Client::on_settings_btn_clicked()
{
    settingsDialog->setModal(true);
    settingsDialog->exec();
}

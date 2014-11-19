#include "settingsdialog.h"
#include "ui_settingsdialog.h"
#include <QMessageBox>

SettingsDialog::SettingsDialog(QWidget *parent, CommunicationService* communicationService, FilesService* filesService) :
    QDialog(parent),
    ui(new Ui::SettingsDialog)
{
    ui->setupUi(this);
    this->setWindowFlags(windowFlags() & ~Qt::WindowContextHelpButtonHint);
    this->communicationService = communicationService;
    this->filesService = filesService;
    ui->servaddressEdit->setText(filesService->getServerAddressFromFile());
}

SettingsDialog::~SettingsDialog()
{
    delete ui;
}

void SettingsDialog::on_revert_btn_clicked()
{
    ui->servaddressEdit->setText(filesService->getServerAddressFromFile());
}

void SettingsDialog::on_changeAddress_btn_clicked()
{
    QMessageBox mbox;
    mbox.setStyleSheet("QMessageBox {background: rgb(94,94,94);} QLabel {color: white;} QPushButton {color: #eeeeee; border: 1px solid #8f8f91;border-radius: 2px;background-color:  qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(34, 34, 34, 255), stop:1 rgba(94, 94, 94, 255));min-width: 80px;}QPushButton:pressed {background-color: qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(79, 79, 79, 255), stop:1 rgba(145, 145, 145, 255));color: #dddddd;}QPushButton:flat {border: none;} QPushButton:default {border-color: white;} QPushButton:disabled {color: #444444; background: qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(79, 79, 79, 255), stop:1 rgba(145, 145, 145, 255));}");
    if(filesService->saveServerAddress(ui->servaddressEdit->text())) {
        communicationService->setServerAddress(ui->servaddressEdit->text());
        mbox.setText("Pomyślnie zmieniono adres.");
        mbox.setIcon(QMessageBox::Information);
    }
    else {
        mbox.setText("Wystąpił błąd podczas zapisu adresu serwera!");
        mbox.setIcon(QMessageBox::Critical);
    }
    mbox.exec();
}

void SettingsDialog::on_adddomain_btn_clicked()
{
    QMessageBox mbox;
    mbox.setStyleSheet("QMessageBox {background: rgb(94,94,94);} QLabel {color: white;} QPushButton {color: #eeeeee; border: 1px solid #8f8f91;border-radius: 2px;background-color:  qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(34, 34, 34, 255), stop:1 rgba(94, 94, 94, 255));min-width: 80px;}QPushButton:pressed {background-color: qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(79, 79, 79, 255), stop:1 rgba(145, 145, 145, 255));color: #dddddd;}QPushButton:flat {border: none;} QPushButton:default {border-color: white;} QPushButton:disabled {color: #444444; background: qlineargradient(spread:pad, x1:0.477, y1:1, x2:0.5, y2:0, stop:0 rgba(79, 79, 79, 255), stop:1 rgba(145, 145, 145, 255));}");
    if(communicationService->addDomain(ui->newdomainEdit->text())) {
        communicationService->setServerAddress(ui->servaddressEdit->text());
        mbox.setText("Pomyślnie dodano adres.");
        mbox.setIcon(QMessageBox::Information);
    }
    else {
        mbox.setText("Wystąpił błąd podczas zapisu adresu serwera!");
        mbox.setIcon(QMessageBox::Critical);
    }
    mbox.exec();
}

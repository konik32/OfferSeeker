#ifndef CLIENT_H
#define CLIENT_H

#include <QDialog>
#include <QDialog>
#include <QDebug>
#include <QStandardItemModel>
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkRequest>
#include <QUrl>
#include <QUrlQuery>
#include <QApplication>
#include <QUuid>
#include <QDateTime>
#include <QRegExpValidator>
#include <QUrl>
#include <QDesktopServices>
#include "communicationservice.h"
#include "filesservice.h"

namespace Ui {
class Client;
}

class Client : public QDialog
{
    Q_OBJECT

public:
    explicit Client(QWidget *parent = 0);
    ~Client();

private slots:
    void on_pushButton_2_clicked();
    void on_pushButton_3_clicked();
    void on_pushButton_clicked();
    void on_listView_clicked();
    void on_przejdz_clicked();

private:
    Ui::Client *ui;
    CommunicationService *communicationService;
    FilesService *filesService;
    QStandardItemModel *model;
    QList<Offer> offers;
    int current;
};

QString postKeyWords();
void getOffers();

#endif // CLIENT_H

#ifndef CLIENT_H
#define CLIENT_H

#include <QDialog>
#include <QDialog>
#include <QDebug>
#include <QtNetwork/QNetworkAccessManager>
#include <QtNetwork/QNetworkReply>
#include <QtNetwork/QNetworkRequest>
#include <QUrl>
#include <QUrlQuery>
#include <QApplication>
#include <QUuid>
#include <QDateTime>
#include "communicationservice.h"

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
    void on_pushButton_clicked();

private:
    Ui::Client *ui;
    CommunicationService *communicationService;
};

QString postKeyWords();
void getOffers();

#endif // CLIENT_H

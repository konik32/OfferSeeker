#include <QCoreApplication>
#include <QDebug>
#include <QNetworkAccessManager>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QUrl>
#include <QUrlQuery>
 
void sendRequest();
 
int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    sendRequest();
    return a.exec();
}
 
void sendRequest(){
 
QNetworkAccessManager *nam;
nam = new QNetworkAccessManager(this);

QByteArray keywords;
QNetworkRequest request= QNetworkRequest(QUrl(adres serwera));

if(keywords.isEmpty())
{
    nam->get(request);
}
else
{
    nam->post(request,keywords);
}
 
// reply
if(reply->error() == QNetworkReply::NoError)
{
    ui->textEdit_result->setText(QObject::tr(reply->readAll()));
}
else
{
    ui->textEdit_result->setPlainText(reply->errorString());
}

connect(nam,
        SIGNAL(finished(QNetworkReply*)),
        this,
        SLOT(finished(QNetworkReply*)));
}

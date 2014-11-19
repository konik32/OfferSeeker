#ifndef STATISTICSDIALOG_H
#define STATISTICSDIALOG_H

#include <QDialog>
#include <QDate>
#include "communicationservice.h"

namespace Ui {
class StatisticsDialog;
}

class StatisticsDialog : public QDialog
{
    Q_OBJECT

private slots:
    void on_startDate_cbox_clicked();
    void on_endDate_cbox_clicked();
    void on_isoffer_cbox_clicked();

    void on_enddateEdit_dateChanged(const QDate &date);

    void on_startdateEdit_dateChanged(const QDate &date);

public slots:
    void updatePlot();

public:
    explicit StatisticsDialog(QWidget *parent = 0, CommunicationService* communicationService = 0);
    ~StatisticsDialog();

private:
    Ui::StatisticsDialog *ui;
    void setStartPack();
    CommunicationService *communicationService;
};



#endif // STATISTICSDIALOG_H

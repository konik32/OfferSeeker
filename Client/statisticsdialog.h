#ifndef STATISTICSDIALOG_H
#define STATISTICSDIALOG_H

#include <QDialog>
#include <QDate>

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

public:
    explicit StatisticsDialog(QWidget *parent = 0);
    ~StatisticsDialog();

private slots:
    void on_stat_btn_clicked();

private:
    Ui::StatisticsDialog *ui;
    void setStartPack();
};



#endif // STATISTICSDIALOG_H

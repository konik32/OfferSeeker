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

public:
    explicit StatisticsDialog(QWidget *parent = 0);
    ~StatisticsDialog();

private:
    Ui::StatisticsDialog *ui;
    void setCurrentDate();
};



#endif // STATISTICSDIALOG_H

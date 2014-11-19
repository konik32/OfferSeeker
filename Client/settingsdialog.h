#ifndef SETTINGSDIALOG_H
#define SETTINGSDIALOG_H
#include "communicationservice.h"
#include "filesservice.h"

#include <QDialog>

namespace Ui {
class SettingsDialog;
}

class SettingsDialog : public QDialog
{
    Q_OBJECT

public:
    explicit SettingsDialog(QWidget *parent = 0, CommunicationService* communicationService = 0, FilesService* filesService = 0);
    ~SettingsDialog();

private slots:
    void on_revert_btn_clicked();

    void on_changeAddress_btn_clicked();

    void on_adddomain_btn_clicked();

private:
    Ui::SettingsDialog *ui;
    CommunicationService* communicationService;
    FilesService* filesService;
};

#endif // SETTINGSDIALOG_H

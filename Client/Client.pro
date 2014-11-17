#-------------------------------------------------
#
# Project created by QtCreator 2014-11-05T17:26:59
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets printsupport

TARGET = Client
TEMPLATE = app

QT   += network

SOURCES += main.cpp\
        client.cpp \
    Controller.cpp \
    offer.cpp \
    statistic.cpp \
    communicationservice.cpp \
    filesservice.cpp \
    keywordsrecord.cpp \
    statisticsdialog.cpp \
    qcustomplot.cpp

HEADERS  += client.h \
    offer.h \
    statistic.h \
    communicationservice.h \
    filesservice.h \
    keywordsrecord.h \
    statisticsdialog.h \
    qcustomplot.h

FORMS    += client.ui \
    statisticsdialog.ui

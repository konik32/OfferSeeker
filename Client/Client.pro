#-------------------------------------------------
#
# Project created by QtCreator 2014-11-05T17:26:59
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = Client
TEMPLATE = app

QT   += network

SOURCES += main.cpp\
        client.cpp \
    Controller.cpp

HEADERS  += client.h

FORMS    += client.ui

package com.javarush.task.task32.task3209.listeners;

import com.javarush.task.task32.task3209.View;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.*;
import java.awt.Component;

public class TextEditMenuListener implements MenuListener {
    private View view;

    public TextEditMenuListener(View view){
        this.view = view;
    }

    @Override
    public void menuSelected(MenuEvent menuEvent) {
        JMenu menu = (JMenu)menuEvent.getSource(); //Из переданного параметра получает объект, над которым было совершено действие.
        //getEditor() - озвращает компонент, который отображает и потенциально изменяет значение модели.
        //MenuEvent используется для уведомления заинтересованных сторон о том, что меню, являющееся источником события,
        //было опубликовано, выбрано или отменено.
        Component[] punktMenu = menu.getMenuComponents(); //У полученного меню получать список компонентов (пунктов меню).
        for(int i = 0; i < punktMenu.length; i++) {
            // Для каждого пункта меню вызывать метод setEnabled, передав в качестве параметра результат вызова метода isHtmlTabSelected() из представления.
            punktMenu[i].setEnabled(view.isHtmlTabSelected());
        }

    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}

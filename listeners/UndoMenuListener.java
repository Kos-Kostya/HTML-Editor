package com.javarush.task.task32.task3209.listeners;

import com.javarush.task.task32.task3209.View;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.Component;

public class UndoMenuListener implements MenuListener{
    private View view;
    private JMenuItem undoMenuItem; //Пункт меню "Отменить"
    private JMenuItem redoMenuItem; //Пункт меню "Вернуть"

    public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem){
        this.view = view;
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        //Он будет вызываться перед показом меню.
        //Спрашивает у представления можем ли мы отменить действие с помощью метода boolean canUndo().
        undoMenuItem.setEnabled(view.canUndo());
        //Он будет вызываться перед показом меню.
        //Спрашивает у представления можем ли мы Вернуть действие с помощью метода boolean canRedo().
        redoMenuItem.setEnabled(view.canRedo());
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}

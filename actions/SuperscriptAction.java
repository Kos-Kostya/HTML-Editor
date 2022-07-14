package com.javarush.task.task32.task3209.actions;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;

public class SuperscriptAction extends StyledEditorKit.StyledTextAction {
    //Класс SuperscriptAction он отвечает за стиль "Надстрочный знак".

    public SuperscriptAction() {
        super(StyleConstants.Superscript.toString());
        //StyleConstants Атрибуты абзаца формируют определение отображаемого абзаца.
        //Superscript - Имя атрибута верхнего индекса.
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JEditorPane editor = getEditor(actionEvent); //Текстовый компонент для редактирования различного контента.
        //getEditor() - озвращает компонент, который отображает и потенциально изменяет значение модели.
        //ActionEvent - Семантическое событие, указывающее, что произошло определенное компонентом действие.
        if (editor != null) {
            MutableAttributeSet mutableAttributeSet = getStyledEditorKit(editor).getInputAttributes();
            //MutableAttributeSet - Общий интерфейс для изменяемой коллекции уникальных атрибутов.
            //getStyledEditorKit(JEditorPane e) - Получает комплект редактора, связанный с панелью редактора.
            //getInputAttributes - Получает входные атрибуты для панели.
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            //SimpleAttributeSet - Простая реализация MutableAttributeSet с использованием хеш-таблицы.
            StyleConstants.setSuperscript(simpleAttributeSet, !StyleConstants.isSuperscript(mutableAttributeSet));
            //setSuperscript - Устанавливает атрибут надстрочного индекса.
            //isSuperscript - Проверяет, установлен ли атрибут надстрочного индекса.
            setCharacterAttributes(editor, simpleAttributeSet, false);
            //setCharacterAttributes - public(AttributeSet attr, boolean replace)
            //Применяет заданные атрибуты к содержимому символов. Если есть выбор, атрибуты применяются к диапазону выбора.
            //Если выбора нет, атрибуты применяются к набору входных атрибутов, который определяет атрибуты для любого вставленного нового текста.
        }
    }
}

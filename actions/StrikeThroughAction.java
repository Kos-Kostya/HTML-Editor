package com.javarush.task.task32.task3209.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

public class StrikeThroughAction extends StyledEditorKit.StyledTextAction {
    // Класс StrikeThroughAction отвечает за стиль текста "Зачеркнутый".

    public StrikeThroughAction() {
        super(StyleConstants.StrikeThrough.toString());
        //StyleConstants Атрибуты абзаца формируют определение отображаемого абзаца.
        //StrikeThrough - Имя атрибута «Зачеркнутый».
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
                StyleConstants.setStrikeThrough(simpleAttributeSet, !StyleConstants.isStrikeThrough(mutableAttributeSet));
                //setStrikeThrough - Устанавливает значение зачеркнутого свойства.
                //StyleConstants - Проверяет, установлен ли атрибут зачеркивания.
                setCharacterAttributes(editor, simpleAttributeSet, false);
                //setCharacterAttributes - public(AttributeSet attr, boolean replace)
                //Применяет заданные атрибуты к содержимому символов. Если есть выбор, атрибуты применяются к диапазону выбора.
                //Если выбора нет, атрибуты применяются к набору входных атрибутов, который определяет атрибуты для любого вставленного нового текста.
            }
        }
    }


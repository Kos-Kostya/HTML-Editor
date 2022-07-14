package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;
//Для открытия или сохранения файла мы будем использовать JFileChooser из библиотеки swing.
//Объекты этого типа поддерживают фильтры, унаследованные от FileFilter.
//У нас будет свой фильтр:

public class HTMLFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) { //Мы хотим, чтобы окно выбора файла отображало только html/htm файлы или папки.

        //возвращал true, если переданный файл директория или содержит
        // в конце имени ".html" или ".htm" без учета регистра.
        String put = file.getName();
        if (file.isDirectory() || put.toLowerCase().endsWith(".html") || put.toLowerCase().endsWith(".htm")) return true;
        else return false;
    }

    @Override
    public String getDescription() {
        //Чтобы в окне выбора файла в описании доступных типов файлов отображался текст "HTML и HTM файлы"
        return "HTML и HTM файлы";
    }
}

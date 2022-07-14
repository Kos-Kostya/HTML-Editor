package com.javarush.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.File;
import com.javarush.task.task32.task3209.listeners.UndoListener;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.FileReader;

public class Controller {
    private View view;
    private HTMLDocument document; //модель
    private File currentFile;

    public Controller(View view){
        this.view = view;
    }

    public static void main(String[] args){
         View view = new View();
         Controller controller = new Controller(view);

         view.setController(controller);
         view.init();
         controller.init();
    }

    public void init(){
        createNewDocument();
    }
    public void exit(){
        System.exit(0);
    }

    public HTMLDocument getDocument(){
        return document;
    }

    public void resetDocument() {
         UndoListener undoListener = view.getUndoListener();
        if (document != null) {
            //Удалять у текущего документа document слушателя правок которые можно отменить/вернуть
            document.removeUndoableEditListener(undoListener);
        }
        //Создавать новый документ по умолчанию и присваивать его полю document
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        //Добавлять новому документу слушателя правок
        document.addUndoableEditListener(undoListener);
        //Вызывать у представления метод update()
        view.update();
    }
    
    public void setPlainText(String text){
        // будет записывать переданный текст с html тегами в документ document.
        try{
        resetDocument(); //сбрасывать документ через метод resetDocument().
        StringReader reader = new StringReader(text);
        HTMLEditorKit htmle = new HTMLEditorKit();
        //читает данные из ридера в документ document.
        htmle.read(reader, document, 0);
        }catch(Exception e){
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText(){
          //Он получает текст из документа со всеми html тегами.
        //Преобразует его в строку и возвоащает эту строку.
            StringWriter writer = new StringWriter();
            HTMLEditorKit html = new HTMLEditorKit();
        try {
            //Записываем содержимое из документа в заданный поток в формате, подходящем для этого типа обработчика содержимого.
            html.write(writer, document, 0, document.getLength());
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
        return  writer.toString();
    }

    public void getActionCommand(){

    }

    public void createNewDocument(){
        //метод создания нового документа в контроллере.
        //Выбирем html вкладку у представления
        view.selectHtmlTab();
        //Сбрасывать текущий документ
        resetDocument();
        //Устанавливаем новый заголовок окна, например: "HTML редактор".
        //Воспользуемся методом setTitle(), который унаследован в нашем представлении.
        view.setTitle("HTML редактор");
        //Обнулить переменную currentFile
        currentFile = null;
    }

    public void openDocument(){
        try {
            //Переключать представление на html вкладку.
            view.selectHtmlTab();
            //
            JFileChooser file = new JFileChooser();
            file.setFileFilter(new HTMLFileFilter());//Устанавливать ему в качестве фильтра объект HTMLFileFilter.
            //Показывать диалоговое окно "Save File" для выбора файла.
            int i = file.showOpenDialog(view);
            //Если пользователь подтвердит выбор файла:
            if (i == file.APPROVE_OPTION) {
                //Сохранять выбранный файл в поле currentFile.
                currentFile = file.getSelectedFile();
                //Сбросить документ.
                resetDocument();
                //Устанавливать имя файла в качестве заголовка окна представления.
                view.setTitle(currentFile.getName());
                //Создавать FileReader на базе currentFile.
                FileReader fileReader = new FileReader(currentFile);
                //Переписывать данные из документа document в объект FileWriter-а аналогично тому, как мы это делали в методе getPlainText()
                HTMLEditorKit html = new HTMLEditorKit();
                try {
                    //Вычитать данные из FileReader-а в документ document с помощью объекта класса HTMLEditorKit.
                    html.read(fileReader, document, 0);
                } catch (Exception e) {
                    ExceptionHandler.log(e);
                }
                view.resetUndo();
            }
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    public void saveDocument(){
        //метод для сохранения открытого файла

            //Переключать представление на html вкладку.
            view.selectHtmlTab();
            //JFileChooser file = new JFileChooser();
            //file.setFileFilter(new HTMLFileFilter());//Устанавливать ему в качестве фильтра объект HTMLFileFilter.
            //Сохранять выбранный файл в поле currentFile.
            //currentFile = file.getSelectedFile();
            //Устанавливать имя файла в качестве заголовка окна представления.
            //view.setTitle(currentFile.getName());

            if(currentFile != null){
                //Создавать FileWriter на базе currentFile.
                try {
                FileWriter fileWriter = new FileWriter(currentFile);
                //Переписывать данные из документа document в объект FileWriter-а аналогично тому, как мы это делали в методе getPlainText()
                HTMLEditorKit html = new HTMLEditorKit();
                html.write(fileWriter, document, 0, document.getLength());
                }catch(Exception e){
                    ExceptionHandler.log(e);
                }
            } else saveDocumentAs();
    }

    public <FileChooserTest> void saveDocumentAs(){
        // метод для сохранения файла под новым именем
        try {
            //Переключать представление на html вкладку.
            view.selectHtmlTab();
            //
            JFileChooser file = new JFileChooser();
            file.setFileFilter(new HTMLFileFilter());//Устанавливать ему в качестве фильтра объект HTMLFileFilter.
            //Показывать диалоговое окно "Save File" для выбора файла.
            int i = file.showSaveDialog(view);
            //Если пользователь подтвердит выбор файла:
            if (i == file.APPROVE_OPTION) {
                //Сохранять выбранный файл в поле currentFile.
                currentFile = file.getSelectedFile();
                //Устанавливать имя файла в качестве заголовка окна представления.
                view.setTitle(currentFile.getName());
                //Создавать FileWriter на базе currentFile.
                FileWriter fileWriter = new FileWriter(currentFile);
                //Переписывать данные из документа document в объект FileWriter-а аналогично тому, как мы это делали в методе getPlainText()
                HTMLEditorKit html = new HTMLEditorKit();
                try {
                    //Записываем содержимое из документа в заданный поток в формате, подходящем для этого типа обработчика содержимого.
                    html.write(fileWriter, document, 0, document.getLength());
                } catch (Exception e) {
                    ExceptionHandler.log(e);
                }
            }
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

}

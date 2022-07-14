package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.undo.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.*;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLDocument;


//класс представления

public class View extends JFrame implements ActionListener{
       private Controller controller;
       private JTabbedPane tabbedPane = new JTabbedPane(); //панель с двумя вкладками
       private JTextPane htmlTextPane = new JTextPane();  //компонент для визуального редактирования html. Он будет размещен на первой вкладке.
       private JEditorPane plainTextPane = new JEditorPane();  //компонент для редактирования html в виде текста, он будет отображать код html (теги и их содержимое).
       private UndoManager undoManager = new UndoManager(); //управляет списком UndoableEdits, предоставляя возможность отменить или повторить соответствующие изменения.
       private UndoListener undoListener = new UndoListener(undoManager);

       public View(){
              try{
                     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                     //определяет стиль в приложении
              }catch (Exception e){
                     ExceptionHandler.log(e);
              }
       }

       public Controller getController(){
              return controller;
       }
       public void setController(Controller controller){
              this.controller = controller;
       }
       public void init(){
              //Он отвечает за инициализацию представления
           initGui();
              FrameListener frameListener = new FrameListener(this); //слушателя событий нашего окна.

              addWindowListener(frameListener); //добовление слушателя
              setVisible(true); //Показывать наше окно.
       }
       public void exit(){
              controller.exit();
       }

       public void initMenuBar(){
              //отвечает за инициализацию меню. JPanel
              JMenuBar menu = new JMenuBar(); //Это наша панель меню.
              MenuHelper.initFileMenu(this, menu); //Файл
              MenuHelper.initEditMenu(this, menu); //Редактировать
              MenuHelper.initStyleMenu(this, menu); //Стиль
              MenuHelper.initAlignMenu(this, menu); //Выравнивание
              MenuHelper.initColorMenu(this, menu); //Цвет
              MenuHelper.initFontMenu(this, menu); //Шрифт
              MenuHelper.initHelpMenu(this, menu); //Помощь
              getContentPane().add(menu, BorderLayout.NORTH); //Добавляет в вверхнюю часть панели JMenuBar. (NORTH верхняя часть)
       }

       public void initEditor(){
              //отвечает за панелей редактора. JPanel
              htmlTextPane.setContentType("text/html"); //Устанавливать значение "text/html" в качестве типа контента для компонента htmlTextPane.
              JScrollPane scrollPane = new JScrollPane(htmlTextPane); // JTextPane с полосой прокрутки.
              tabbedPane.addTab("HTML", scrollPane);
              JScrollPane scrollPane1 = new JScrollPane(plainTextPane); // JTextPane с полем для текста.
              tabbedPane.addTab("Текст", scrollPane1);
              tabbedPane.setPreferredSize(new Dimension(1200, 700)); //Установка предпочтительного размера панели tabbedPane.
              TabbedPaneChangeListener changeListener = new TabbedPaneChangeListener(this);
              tabbedPane.addChangeListener(changeListener);//слушатель изменений TabbedPaneChangeListener
              getContentPane().add(tabbedPane, BorderLayout.CENTER); //Добавление вкладок HTML и Текст в окно панели. JPanel
       }

       public void initGui(){
              //инициализирует графический интерфейс.
              initMenuBar();
              initEditor();
              pack();
       }

       public boolean canUndo(){
              //Возвращает true, если изменения могут быть отменены.
           return undoManager.canUndo();
       }

       public boolean canRedo(){
              //Возвращает true, если изменения могут быть повторены.
              return undoManager.canRedo();
       }

       public void undo(){
              //отменяет последнее действие.
              try{
                     undoManager.undo();
              }catch (Exception e){
                     ExceptionHandler.log(e);
              }
       }

       public void redo(){
              //возвращает ранее отмененное действие.
              try{
                     undoManager.redo();
              }catch (Exception e){
                     ExceptionHandler.log(e);
              }
       }

       public UndoListener getUndoListener(){
              return undoListener;
       }

       public void resetUndo(){
              //сбрасывает все правки в менеджере undoManager.
              undoManager.discardAllEdits();
       }

       public boolean isHtmlTabSelected(){
              //возвращает true, если выбрана вкладка, отображающая html в панели вкладок
              int i = tabbedPane.getSelectedIndex();
              if(i == 0) return true;
              else return false;
       }

       public void selectHtmlTab(){
              //Он выбирает html вкладку (переключаться на нее).
              tabbedPane.setSelectedIndex(0);
              //Сбрасывает все правки с помощью метода, который реализовали ранее.
              resetUndo();
       }

       public void update(){

              HTMLDocument doc = controller.getDocument();//получаем html документ из Контроллера.
              htmlTextPane.setDocument(doc);//Связывает редактор с текстовым документом.
       }

       public void showAbout(){
              //показывает диалоговое окно с информацией о программе.
              JOptionPane.showMessageDialog(null,
                      "Все права защищены",
                      "Программа Kos-a",
                      JOptionPane.INFORMATION_MESSAGE);
       }

       public void selectedTabChanged(){
              //Этот метод вызывается, когда произошла смена выбранной вкладки.
              //Определяет какая вкладка сейчас открыта.
              int i = tabbedPane.getSelectedIndex();
              
              if(i == 0){
                     //Получаем html текст и записываем его в строку.
                     String text = plainTextPane.getText();
                     //передаем строку в Контроллер для записи в документ.
                     controller.setPlainText(text);
              }
              if(i == 1){
                     //Если выбрана вкладка с индексом 1 (вкладка с html текстом), то необходимо получить текст у контроллера
                     //с помощью метода getPlainText() и установить его в панель plainTextPane.
                     String text = controller.getPlainText();
                     plainTextPane.setText(text);
              }
              resetUndo();
       }

       public void actionPerformed(ActionEvent actionEvent){
              //будет вызваться при выборе пунктов меню, у которых наше представление указано в виде слушателя событий.
              //По этой строке можно понять какой пункт меню создал данное событие.
              String punktMenu = actionEvent.getActionCommand();
              if(punktMenu.equals("Новый")){
                     //передано событие с командой "Новый", метод должен вызывать у контроллера createNewDocument().
                     controller.createNewDocument();
              }
              else if(punktMenu.equals("Открыть")){
                     //передано событие с командой "Открыть", метод должен вызывать у контроллера openDocument().
                     controller.openDocument();
              }
              else if(punktMenu.equals("Сохранить")){
                     //передано событие с командой "Сохранить", метод должен вызывать у контроллера saveDocument().
                     controller.saveDocument();
              }
              else if(punktMenu.equals("Сохранить как...")){
                     //передано событие с командой "Сохранить как...", метод должен вызывать у контроллера saveDocumentAs().
                     controller.saveDocumentAs();
              }
              else if(punktMenu.equals("Выход")){
                     //передано событие с командой "Выход", метод должен вызывать у контроллера exit().
                     controller.exit();
              }
              else if(punktMenu.equals("О программе")){
                     //передано событие с командой "О программе", метод должен вызывать у представления showAbout().
                     showAbout();
              }
       }


}

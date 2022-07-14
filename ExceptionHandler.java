package com.javarush.task.task32.task3209;

import java.lang.Exception;

public class ExceptionHandler {
    //Это наш обработчик исключительных ситуаций, который в дальнейшем можно переопределить.

    public static void log(Exception e){
        System.out.println(e.toString());
    }
}

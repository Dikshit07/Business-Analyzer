package com.project2.projecttwo.service;

import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class jythonservice {
    
   private PythonInterpreter pythonInterpreter;

   public String execute()
   {
    this.pythonInterpreter =  new PythonInterpreter();
    // pythonInterpreter.exec();

    return"";
   }


}

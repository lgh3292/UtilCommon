package com.lgh.util.designpattern.vistor.copy2;

import java.util.ArrayList;
import java.util.List;

public abstract class Human {  
    protected int id;  
    //���������������Ա��������ϰ���ô�ǾͿɹ�����  
    protected List<? extends Human> list = new ArrayList<Human>();  
  
    public List<? extends Human> getList() {  
        return list;  
    }  
  
    public void setList(List<? extends Human> list) {  
        this.list = list;  
    }  
  
    public int getId() {  
        return id;  
    }  
  
    public void setId(int id) {  
        this.id = id;  
    }  
  
    public  void accept(Visitor visitor){  
          
    }  
}  
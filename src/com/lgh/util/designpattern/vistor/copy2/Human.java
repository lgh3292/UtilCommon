package com.lgh.util.designpattern.vistor.copy2;

import java.util.ArrayList;
import java.util.List;

public abstract class Human {  
    protected int id;  
    //该人物所管理的人员，如果是老板那么那就可管理经理  
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
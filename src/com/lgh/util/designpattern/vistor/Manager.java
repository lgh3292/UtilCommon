package com.lgh.util.designpattern.vistor;

public class Manager extends Human {  
    public Manager(int id){  
        this.id = id;  
    }  
    @Override  
    public void accept(Visitor visitor) {  
        visitor.visit(this);  
    }  
    public String toString(){  
        return new StringBuffer("Manager the id:"+id).toString();  
    }  
}  
  
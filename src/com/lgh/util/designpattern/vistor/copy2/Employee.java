package com.lgh.util.designpattern.vistor.copy2;

public class Employee extends Human{  
    public  Employee(int id){  
        this.id = id;  
    }  
    @Override  
    public void accept(Visitor visitor) {  
        visitor.visit(this);  
    }  
      
    public String toString(){  
        return new StringBuffer("Employee the id:"+id).toString();  
    }  
  
}  
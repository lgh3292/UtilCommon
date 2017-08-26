package com.lgh.util.designpattern.vistor.copy2;

public class Boss extends Human {  
    public Boss(int id){  
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
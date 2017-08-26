package com.lgh.util.designpattern.vistor.copy;

import java.util.ArrayList;
import java.util.List;


public class FindVisitor implements Visitor{  
    private int soughtId;  
    private Human found;  
    public void visit(Employee employee) {  
        if(found==null&&employee.getId()==soughtId){  
            found=employee;  
        }  
        
    }  
    public void visit(Human human) {  
        if(found==null&&human.getId()==soughtId){  
            found=human;  
            return;  
        }  
        List<? extends Human> list = human.getList();  
        for(Human e:list){  
            if(found==null)  
            e.accept(this);  
        }  
    }  
    public Human find(Human mc,int id){  
        found = null;  
        soughtId = id;  
        mc.accept(this);  
        return found;  
        
    }  
    public static Human getHuman(Human human, int id) {  
        if (human.getId() != id) {  
            List<Human> ms = (List<Human>) human.getList();  
            for (Human h : ms) {  
                if(getHuman(h,id)!=null){  
                    return getHuman(h,id);  
                }else{  
                    return null;  
                }  
            }  
            return null;  
        } else {  
            return human;  
        }  
    }  
    public static void main(String[] args){
    	Boss boss = new Boss(1);  
        List<Manager> managers = new ArrayList<Manager>();  
        for(int i =2;i<10;i++){  
            Manager manager = new Manager(i);  
            List<Employee> employees = new ArrayList<Employee>();  
            int k = i*100;  
            for(int j = k;j<k+8;j++){  
                employees.add(new Employee(j));  
            }  
            manager.setList(employees);  
            managers.add(manager);  
            managers.add(new Manager(20));
        }  
        boss.setList(managers);  
    	
    	FindVisitor fv =new FindVisitor();  
    	Human human = fv.find(boss, 20);  
    	System.out.println("find:"+ human);  
    }
}  
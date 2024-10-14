package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        DepartmentDao departmentDao = new DaoFactory().createDepartmentDao();
               
        System.out.println("=== TEST 1: Department findById =====");
        Department dep = departmentDao.findById(3);
        System.out.println(dep);
        
        System.out.println("=== TEST 2: Department findAll =====");
        List<Department> list = departmentDao.findAll();
        for (Department d : list){
            System.out.println(d);
        }
        
        System.out.println("=== TEST 3: Department insert =====");
        System.out.println("Digite um nome para o novo departamento: ");
        String newDep = sc.nextLine();
        dep = new Department(null, newDep);
        departmentDao.insert(dep);
        System.out.println("Inserted! New Id " + dep.getId());  
      
        System.out.println("=== TEST 2: Department update =====");
        Department dep2 = departmentDao.findById(8);
        dep2.setName("food");
        departmentDao.update(dep2);
        
        System.out.println("=== TEST 2: Department delete =====");
        System.out.print("Enter id for delete test: ");
        int id = sc.nextInt();
        departmentDao.deletById(id);
    }    
}

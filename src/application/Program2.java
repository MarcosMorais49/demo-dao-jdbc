package application;

import java.util.Scanner;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        DepartmentDao departmentDao = new DaoFactory().createDepartmentDao();
        
        System.out.println("=== TEST 1: Department insert =====");
        System.out.println("Digite um nome para o novo departamento: ");
        String newDep = sc.nextLine();
        Department dep = new Department(null, newDep);
        departmentDao.insert(dep);
        System.out.println("Inserted! New Id " + dep.getId());
        
        System.out.println("=== TEST 2: Department findById =====");
        Department findDep = departmentDao.findById(3);
        System.out.println(findDep);
        
        
    }
    
}

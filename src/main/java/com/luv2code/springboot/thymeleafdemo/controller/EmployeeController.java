package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model){
        //get employees from db
        List<Employee> list=employeeService.findAll();
        model.addAttribute("list",list);
        return "employees/employee-list";
    }

    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        Employee emp=new Employee();
        model.addAttribute("employee",emp);//so thymeleaf can bind the form data

        return "employees/add-employee";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee")Employee employee){
        employeeService.save(employee);
        //use redirect to prevent multiple submissions
        return "redirect:/employees/list";
    }

    @GetMapping("/updateEmployee")
    public String updateEmployee(@RequestParam("employeeId") int id,Model model){
       var emp= employeeService.findById(id);
        System.out.println(emp);
       model.addAttribute("employee",emp);
        return "employees/add-employee";

    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("employeeId") int id){
        employeeService.deleteById(id);
        return "redirect:/employees/list";

    }
}

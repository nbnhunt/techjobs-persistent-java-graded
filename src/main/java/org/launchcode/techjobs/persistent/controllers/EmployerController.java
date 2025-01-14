package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

// re: Chapter 17.3.2: using persistence

// EmployerController contains two handlers with missing information
// .... which are.....?
// Add a private field of EmployerRepository type called employerRepository
// Give this field an @Autowired annotation.

// Add an index method that responds at /employers
// with a list of all employers in the database.
// This method should use the template employers/index.

// processAddEmployerForm doesn’t yet contain the code to save a valid object.
// (it's missing information)
// Use employerRepository and the appropriate method to do so.

@Controller
@RequestMapping("employers")
public class EmployerController {

    @Autowired // dependency injection --> asks Spring Boot for objects named xyz
    private EmployerRepository employerRepository;

    // findAll, save, findById

    @GetMapping("")
    public String index (Model model) {
        model.addAttribute("title", "All Employers");
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        //model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("title", "Add Employer");
        model.addAttribute(new Employer());
        return "employers/add";
    }

    // 13.3.1 Passing Data to a Template + 17.3.1 CRUD Ops
    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer newEmployer,
                                    Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Employer");
            return "employers/add";
        } else {
            employerRepository.save(newEmployer);
            return "redirect:";
        }
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int employerId) {

        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}

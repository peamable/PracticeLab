package com.example.practicelab.controller;

import com.example.practicelab.model.InvestmentRecord;
import com.example.practicelab.model.Projection;
import com.example.practicelab.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/*
Create empty GitHub repo
git init
git add .
git commit
git remote add origin <repo-url>
git push -u origin main
GITHUB repository link  https://github.com/peamable/PracticeLab.git
 */
@Controller
public class RecordController {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("records", recordRepository.findAll());
        return "InvestmentRecords"; //the first html page we want to load
    }

    //I'm using this method to route to the form
    @GetMapping("/addRoute")
    public String addRoute(Model model) {
        model.addAttribute("record", new InvestmentRecord());
        return "addform";
    }

    @PostMapping("/addRecord")
    public String addRecord(@ModelAttribute InvestmentRecord record, RedirectAttributes redirectAttributes) {
        //form in html must match fields in model
        try{
            boolean exists = recordRepository.existsByCustomerNumber(record.getCustomerNumber());
            if (record.getId()==null && exists) {throw new IllegalArgumentException("Record already exists");}
            recordRepository.save(record);
            return "redirect:/"; //reload the main page with updated data
        }
        catch (IllegalArgumentException e){
            //use model if we will use the error in the page that sends the request
//            model.addAttribute("errorMessage", "The record you are " +
//                    "trying to add already exists. Choose a different customer number");
            redirectAttributes.addFlashAttribute("errorMessage", "The record you are " +
                   "trying to add already exists. Choose a different customer number");
            return "redirect:/"; //return form if that's what is asked.

        }

    }

    @GetMapping("/editRoute/{id}")
    public String editRoute(@PathVariable Long id, Model model) {
        InvestmentRecord record = recordRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Record not found"));
        model.addAttribute("record", record);
        return "addform";
    }

    @PostMapping("/deleteRecord/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/openProjection/{id}")
    public String openProjection(@PathVariable Long id, Model model) {
       InvestmentRecord record= recordRepository.findById(id).orElseThrow(() ->new IllegalArgumentException("Record not found"));
       model.addAttribute("customerNumber", record.getCustomerNumber());
       model.addAttribute("customerName", record.getCustomerName());
       int numOfYears = record.getNumberOfYears();
       String savingsType = record.getSavingsType();
       double intRate;
       if (savingsType.equals("Savings-Regular"))
       {
           intRate = 0.1;
       }
       else
       {intRate = 0.15;}
       double startingAmt = record.getCustomerDeposit();
       double interest= startingAmt*intRate;
       double endingAmt = startingAmt + interest;
        int year;

        List<Projection> projections = new ArrayList<>();

        for (int i=1; i <= numOfYears; i++ )
        {
            year = i;
            projections.add(new Projection(year, startingAmt, interest, endingAmt));
            startingAmt = endingAmt;
            interest = startingAmt*intRate;
            endingAmt = startingAmt + interest;

        }
        model.addAttribute("projections", projections);
        return "projectiontable";
    }

    @GetMapping("/submit")
    public String submit() {
        return  "redirect:/";
    }


}

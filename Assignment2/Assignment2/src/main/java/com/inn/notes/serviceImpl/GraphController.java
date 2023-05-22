package com.inn.notes.serviceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphController {

    @GetMapping("/displayPieChart")
    public String pieChart(Model model){
        model.addAttribute("NotesAboutLife", 29);
        model.addAttribute("NoteAboutUniversity", 78);
        model.addAttribute("TodoNextWeek", 5);
        model.addAttribute("FiveYearPlan", 64);
        model.addAttribute("Thoughts", 10);
        model.addAttribute("Others", 35);
        return "pieChart";
    }
}

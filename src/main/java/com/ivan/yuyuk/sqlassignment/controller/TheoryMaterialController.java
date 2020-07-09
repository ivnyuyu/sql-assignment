package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.repository.TheoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TheoryMaterialController {

    private TheoryRepository theoryRepository;

    public TheoryMaterialController(TheoryRepository theoryRepository) {
        this.theoryRepository = theoryRepository;
    }

    @GetMapping("/allMaterial")
    public String getAllMaterial(Model model) {
        model.addAttribute("allMaterial", theoryRepository.findAll());
        return "allTheoryMaterial";
    }

    @GetMapping("/allMaterial/{materialId}")
    public String getMaterialById(@PathVariable("materialId") Long id, Model model, HttpServletResponse response, HttpServletRequest request) {
        model.addAttribute("material", theoryRepository.findById(id).get());
        return "materialPage";
    }
}

package com.ivan.GrassCutterShopWithSpring.controller;


import com.ivan.GrassCutterShopWithSpring.model.*;
import com.ivan.GrassCutterShopWithSpring.repo.GrassCutterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
public class GrassCutterController {

    private final GrassCutterRepository grassCutterRepository;

    @Autowired
    public GrassCutterController(GrassCutterRepository grassCutterRepository) {
        this.grassCutterRepository = grassCutterRepository;
    }

    @GetMapping("/grassCutters")
    public String grassCutterPageShow(Model model){
        List<GrassCutter> grassCutters = grassCutterRepository.findAll();
        List<GrassCutterBrand> brands = Arrays.asList(GrassCutterBrand.values());
        model.addAttribute("grassCutters", grassCutters);
        model.addAttribute("brands", brands);
        model.addAttribute("title","Grass Cutters");
        return "grassCutterPage";
    }

    @GetMapping("/grassCutters/filterByBrand")
    public String grassCutterPageByBrand(@RequestParam GrassCutterBrand brand, Model model){
        List<GrassCutter> grassCutters = grassCutterRepository.findAllByBrand(brand);
        List<GrassCutterBrand> brands = Arrays.asList(GrassCutterBrand.values());
        model.addAttribute("grassCutters", grassCutters);
        model.addAttribute("brands", brands);
        model.addAttribute("title","Grass Cutters");
        return "grassCutterPage";
    }

    @GetMapping("/grassCutters/sortByPrice")
    public String grassCutterPageSortedByPrice(Model model){
        List<GrassCutter> grassCutters = grassCutterRepository.findAllByOrderByPrice();
        List<GrassCutterBrand> brands = Arrays.asList(GrassCutterBrand.values());
        model.addAttribute("grassCutters", grassCutters);
        model.addAttribute("brands", brands);
        model.addAttribute("title","Grass Cutters");
        return "grassCutterPage";
    }

    @GetMapping("/grassCutters/sortByPriceDesc")
    public String grassCutterPageSortedByPriceDesc(Model model){
        List<GrassCutter> grassCutters = grassCutterRepository.findAllByOrderByPriceDesc();
        List<GrassCutterBrand> brands = Arrays.asList(GrassCutterBrand.values());
        model.addAttribute("grassCutters", grassCutters);
        model.addAttribute("brands", brands);
        model.addAttribute("title","Grass Cutters");
        return "grassCutterPage";
    }

    @GetMapping("/grassCutters/add")
    public String addUserPage(Model model){
        List<GrassCutterBrand> brands = Arrays.asList(GrassCutterBrand.values());
        List<GrassCutterType> types = Arrays.asList(GrassCutterType.values());
        model.addAttribute("brands", brands);
        model.addAttribute("types", types);
        return "grass-cutter-add";
    }

    @PostMapping("/grassCutters/add")
    public String addUserPost(@RequestParam String name, @RequestParam Double price,
                              @RequestParam GrassCutterType type, @RequestParam GrassCutterBrand brand){
        GrassCutter grassCutter = new GrassCutter(name,price,type,brand);
        grassCutterRepository.save(grassCutter);
        return "redirect:/grassCutters";
    }

    @GetMapping("/grassCutters/{id}")
    public String grassCutterDetails(@PathVariable long id, Model model){
        if(!grassCutterRepository.existsById(id)){
            return "redirect:/grassCutters";
        }
        Optional<GrassCutter> grassCutter = grassCutterRepository.findById(id);
        List<GrassCutter> res = new ArrayList<>();
        grassCutter.ifPresent(res :: add);
        model.addAttribute("grassCutter", res);
        model.addAttribute("title","Grass Cutter Details");
        return "grass-cutter-details";
    }

    @GetMapping("/grassCutters/{id}/edit")
    public String grassCutterEditPage(@PathVariable long id, Model model){
        if(!grassCutterRepository.existsById(id)){
            return "redirect:/grassCutters/{id}";
        }
        Optional<GrassCutter> grassCutterOptional = grassCutterRepository.findById(id);
        GrassCutter grassCutter = grassCutterOptional.get();
        List<GrassCutterBrand> brands = Arrays.asList(GrassCutterBrand.values());
        List<GrassCutterType> types = Arrays.asList(GrassCutterType.values());
        model.addAttribute("grassCutter", grassCutter);
        model.addAttribute("brands", brands);
        model.addAttribute("types", types);
        model.addAttribute("title","Grass Cutter Edit");
        return "grass-cutter-edit";
    }

    @PostMapping("/grassCutters/{id}/edit")
    public String grassCutterEdit(@PathVariable long id, @RequestParam String name, @RequestParam Double price,
                                  @RequestParam GrassCutterType type, @RequestParam GrassCutterBrand brand){
        GrassCutter newGrassCutter = new GrassCutter(id, name, price, type, brand);
        grassCutterRepository.save(newGrassCutter);
        return "redirect:/grassCutters";
    }

    @PostMapping("/grassCutters/{id}/remove")
    public String blogRemove(@PathVariable(value = "id") long id, Model model) {
        GrassCutter grassCutter = grassCutterRepository.findById(id).orElseThrow();
        grassCutterRepository.delete(grassCutter);
        return "redirect:/grassCutters";
    }
}

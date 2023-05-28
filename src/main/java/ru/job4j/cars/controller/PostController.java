package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.dto.PostCreateDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping({"/posts", "/index", "/"})
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final BodyService bodyService;
    private final BrandService brandService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;

    @GetMapping({"/", "/index"})
    public String getAll(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "index";
    }

    @GetMapping("/filter")
    public String getByFilter(@RequestParam(name = "filter", required = false) String filter, Model model) {
        switch (filter) {
            case "lastDay" -> model.addAttribute("posts", postService.findPostFromLastDay());
            case "withPhoto" -> model.addAttribute("posts", postService.findPostWithPhoto());
            case "isSold" -> model.addAttribute("posts", postService.findPostBySold(false));
            default -> model.addAttribute("posts", postService.findAll());
        }
        return "index";
    }

    @GetMapping("/add")
    public String getCreationPage(Model model) {
        model.addAttribute("post", new Post());
            model.addAttribute("bodies", bodyService.findAll());
            model.addAttribute("brands", brandService.findAll());
            model.addAttribute("engines", engineService.findAll());
            model.addAttribute("transmissions", transmissionService.findAll());
            model.addAttribute("categories", categoryService.findAll());
        return "posts/create";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute PostCreateDto postDto,
                         @RequestParam MultipartFile fileDto, Model model,
                         @SessionAttribute User user) throws IOException {
        postDto.setUser(user);
        var postOptional = postService.add(postDto, new FileDto(fileDto.getOriginalFilename(), fileDto.getBytes()));
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        model.addAttribute("message", "Объявление добавлено успешно");
        return "posts/success";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var postOptional = postService.findById(id);
        if (postOptional.isEmpty()) {
            model.addAttribute("message", "Объявление не найдено");
            return "errors/404";
        }
        model.addAttribute("post", postOptional.get());
        return "posts/one";
    }

    @PostMapping("/sеtSold/{id}")
    public String setStatusSold(Model model, @PathVariable int id) {
        var updateRsl = postService.setSold(id);
        if (!updateRsl) {
            model.addAttribute("message", "Не удалось изменить статус объявлению");
            return "errors/404";
        }
        model.addAttribute("message", "Статус объявления успешно изменен");
        return "posts/success";
    }

    @PostMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id, @SessionAttribute User user) {
        model.addAttribute("user", user);
        var isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Ошибка. Объявление не удалено");
            return "errors/404";
        }
        model.addAttribute("message", "Объявление удалено успешно");
        return "posts/success";
    }
}

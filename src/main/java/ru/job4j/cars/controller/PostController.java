package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final FileService fileService;
    private final CategoryService categoryService;
    private final BodyService bodyService;
    private final BrandService brandService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;

    @GetMapping({"/", "/index"})
    public String getAll(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Post> posts = postService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/category")
    public String getCategory(@RequestParam(name = "id", required = false) Integer categoryId, Model model) {
        if (categoryId != null) {
            var categoryOptional = categoryService.findById(categoryId);
            if (categoryOptional.isPresent()) {
                List<Post> posts = postService.findPostByCategory(categoryOptional.get());
                model.addAttribute("posts", posts);
            }
        }
        return "index";
    }

    @GetMapping("/sold")
    public String getPostsBySold(@RequestParam(name = "sold") boolean sold, Model model) {
        List<Post> posts = postService.findPostBySold(sold);
        model.addAttribute("posts", posts);
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
    public String create(@ModelAttribute Post post, @SessionAttribute User user,
                         @RequestParam("categoryId") int categoryId,
                         @RequestParam("bodyId") int bodyId,
                         @RequestParam("engineId") int engineId,
                         @RequestParam("transmissionId") int transmissionId,
                         @RequestPart("files") List<MultipartFile> files, Model model) {
        try {
            Car car = new Car();
            car.setCategory(categoryService.findById(categoryId).get());
            car.setBody(bodyService.findById(bodyId).get());
            car.setEngine(engineService.findById(engineId).get());
            car.setTransmission(transmissionService.findById(transmissionId).get());
            Owner owner = new Owner();
            owner.setUser(user);
            car.setOwner(owner);
            post.setCar(car);
            post.setUser(user);
            List<File> savedFiles = fileService.convertToFiles(files);
            postService.saveWithFiles(post, savedFiles);
            model.addAttribute("message", "Ваше объявление добавлено успешно!");
            return "posts/success";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
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

    @GetMapping("/state/{id}")
    public String updateState(Model model, @PathVariable int id, @SessionAttribute User user) {
        model.addAttribute("user", user);
        var isUpdatedState = postService.setSold(id);
        if (!isUpdatedState) {
            model.addAttribute("message", "Ошибка. Статус объявления не обновлен");
            return "errors/404";
        }
        model.addAttribute("message", "Статус объявления изменен на Продано");
        return "posts/success";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id, @SessionAttribute User user) {
        model.addAttribute("user", user);
        var isDeleted = postService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Ошибка. Объявление не удалено");
            return "errors/404";
        }
        model.addAttribute("message", "Объявление удалено успешно");
        return "success/success";
    }
}

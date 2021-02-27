package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new MovieForm());

        return "items/createItemForm";
    }


    @PostMapping("/items/new")
    public String create(MovieForm form) {
        //==form객체를 Entity에 맞게 변환==//
        Movie movie = form.toEntity();
        itemService.saveItem(movie);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<MovieForm> movieForms = new ArrayList<>();

        List<Item> items = itemService.findItems();
        for (Item item : items) {
            Movie movie = (Movie) item;
            movieForms.add(movie.toMovieForm());
        }

        model.addAttribute("movieForms", movieForms);

        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Movie findItem = (Movie) itemService.findOne(itemId);

        MovieForm form = findItem.toMovieForm();

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") MovieForm form, @PathVariable Long itemId) {
        Movie movie = form.toEntity();


//        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        itemService.saveItem(movie);

        return "redirect:/items";
    }

}

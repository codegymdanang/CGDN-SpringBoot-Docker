package codegymdanang.demo.controller;

import codegymdanang.demo.entity.Book;
import codegymdanang.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/home")
    public String homePage(Model model) {

        List<Book> books = bookRepository.findAll();
        model.addAttribute("books",books);
        return "home";
    }

}

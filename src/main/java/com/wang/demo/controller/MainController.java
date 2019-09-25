package com.wang.demo.controller;


import com.wang.demo.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class MainController {
    static Map<Integer, Article> map = new HashMap();

    static {
        for (int i = 0; i < 10; i++) {
            map.put(i, getArticle(i));
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPage(Model model) {
        model.addAttribute("article", new Article());
        return "add_page";
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String getArticleList(Model model) {
        List<Article> list = new ArrayList<>();
        System.out.println(map);
        for (Map.Entry<Integer, Article> entry : map.entrySet()) {
            if (!entry.getValue().isDeleteFlag()) {
                continue;
            }
            list.add(entry.getValue());
        }
        model.addAttribute("articles", list);
        return "articles";
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public String getArticle(Model model, @PathVariable("id") int id) {
        Article article = map.get(id);
        model.addAttribute("article", article);
        return "article";
    }

    @RequestMapping(value = "/deleteArticle/{id}", method = RequestMethod.GET)
    public String deleteArticle(Model model, @PathVariable("id") int id) {
        Article article = map.get(id);
        article.setDeleteFlag(false);
        return "delete_success";
    }

    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    public String addArticle(Model model, @ModelAttribute(value = "article") Article article) {
        map.put(article.getId(), article);
        return "add_success";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable("id") int id) {
        Article article = map.get(id);
        model.addAttribute("article", article);
        return "update";

    }

    @RequestMapping(value = "/updateArticle",method = RequestMethod.POST)
    public String updateArticle(Model model,
                                @ModelAttribute(value = "article") Article article){
        Article article1 = map.get(article.getId());
        article1.setTitle(article.getTitle());
        article1.setType(article.getType());
        article1.setContent(article.getContent());
        return "update_success";
    }


    static public Article getArticle(int i) {
        Article article = new Article();
        article.setId(i);
        article.setTitle("题目" + i);
        article.setType("类型" + i);
        article.setContent("正文" + i);
        article.setDeleteFlag(true);
        return article;
    }
}

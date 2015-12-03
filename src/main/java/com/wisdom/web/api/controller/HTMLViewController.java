package com.wisdom.web.api.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HTMLViewController {

        //门户网站
        @RequestMapping("/frontpage/{page}")
        public String getFrontPages(@PathVariable String page) {
                return "redirect:/views/frontviews/" + page + ".html";
        }

        @RequestMapping("/")
        public String getRootHtml() {
                return "redirect:/views/frontviews/index.html";
        }

        @RequestMapping("/error")
        public String getErrorHtml() {
                return "redirect:/views/frontviews/error.html";
        }


}
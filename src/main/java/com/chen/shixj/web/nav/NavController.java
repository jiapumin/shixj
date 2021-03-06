package com.chen.shixj.web.nav;

import com.chen.shixj.entity.Nav;
import com.chen.shixj.service.nav.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * User: jpm
 * Date: 13-8-19
 * Time: 下午6:50
 */

@Controller
@RequestMapping(value = "/admin/nav")
public class NavController {
    private static final int PAGE_SIZE = 10;

    @Autowired
    private NavService navService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        //查询所有一级栏目
        List<Nav> navList = navService.getAllNavForParentNav(0L);
        model.addAttribute("navList", navList);
        return "/admin/nav/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam(value = "isShowNav") Boolean isShowNav,@RequestParam(value = "fileNameList") String fileNameList, @Valid Nav nav, RedirectAttributes redirectAttributes) {
        //处理图片路径
        if (fileNameList != ""){
            String[] str = fileNameList.split("/");
            String navImageName = str[str.length-1];
            String navImagePath = fileNameList.substring(0,fileNameList.length()-navImageName.length());
            nav.setNavImageName(navImageName);
            nav.setNavImagePath(navImagePath);
        }

        nav.setShowNav(isShowNav);
        navService.saveNav(nav);
        redirectAttributes.addAttribute("errorMessage", "添加栏目成功");
        return "redirect:/admin/nav/add/";
    }

    @RequestMapping(value = "/look/{id}", method = RequestMethod.GET)
    public String look(@PathVariable("id") Long id,Model model) {
        model.addAttribute("nav", navService.getNav(id));
        //查询所有一级栏目
        List<Nav> navList = navService.getAllNavForParentNav(0L);
        model.addAttribute("navList", navList);
        return "/admin/nav/look";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //查询所有一级栏目
        List<Nav> navList = navService.getAllNavForParentNav(0L);
        List resultList = new ArrayList();
        for (Nav nav : navList) {
            resultList.add(nav);
            long parentNav = nav.getId();
            List<Nav> subNavList = navService.getAllNavForParentNav(parentNav);
            resultList.addAll(subNavList);
        }
        model.addAttribute("navList", resultList);
        return "admin/nav/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("nav", navService.getNav(id));
        //查询所有一级栏目
        List<Nav> navList = navService.getAllNavForParentNav(0L);
        model.addAttribute("navList", navList);
        return "/admin/nav/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("perloadNav") Nav nav,@RequestParam(value = "fileNameList") String fileNameList,@RequestParam(value = "isShowNav") Boolean isShowNav,
                         RedirectAttributes redirectAttributes) {
        //处理图片路径
        if (fileNameList != ""){
            String[] str = fileNameList.split("/");
            String navImageName = str[str.length-1];
            String navImagePath = fileNameList.substring(0,fileNameList.length()-navImageName.length());
            nav.setNavImageName(navImageName);
            nav.setNavImagePath(navImagePath);
        }
        nav.setShowNav(isShowNav);
        navService.saveNav(nav);
        redirectAttributes.addFlashAttribute("message", "更新栏目成功");
        return "redirect:/admin/nav/list";
    }
    @RequestMapping(value = "/updateShowNav", method = RequestMethod.POST)
    public String updateShowNav(@Valid @ModelAttribute("perloadNav") Nav nav,@RequestParam(value = "isShowNav") Boolean isShowNav,
                         RedirectAttributes redirectAttributes) {
        nav.setShowNav(isShowNav);
        navService.saveNav(nav);
        redirectAttributes.addFlashAttribute("message", "更新栏目成功");
        return "redirect:/admin/nav/list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        //删除此栏目下所有的子栏目
        List<Nav> navs = navService.getAllNavForParentNav(id);
        for (Nav nav : navs){
            navService.deleteNav(nav.getId());
        }

        navService.deleteNav(id);
        redirectAttributes.addFlashAttribute("message", "删除栏目成功");
        return "redirect:/admin/nav/list";
    }

    @ModelAttribute(value = "perloadNav")
    public Nav getNav(@RequestParam(value = "id", required = false) Long id) {
        if (id != null) {
            return navService.getNav(id);
        }
        return null;
    }
}

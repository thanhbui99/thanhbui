package com.java.web.moviefinal2.controllers.admin;

import com.java.web.moviefinal2.converter.MovieConverter;
import com.java.web.moviefinal2.dto.MovieDTO;
import com.java.web.moviefinal2.dto.SearchMovie;
import com.java.web.moviefinal2.dto.UserRegister;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.enums.CategoryEnum;
import com.java.web.moviefinal2.enums.TypeEnum;
import com.java.web.moviefinal2.service.IMovieService;
import com.java.web.moviefinal2.service.IUserService;
import com.java.web.moviefinal2.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;

@Controller()
public class MovieController {


    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private IMovieService iMovieService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/admin/admin-movie")
    public String Movie(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "8") Integer size,
                        @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                        Model model, Principal principal) {
        try {
            Long totalMovie = iMovieService.count();

            StringBuilder sb = new StringBuilder("" + totalMovie + "");
            Integer total = Integer.parseInt(sb.toString());
            int totalPage = (int) Math.ceil(((double) total / (double) size));

            List totalItem = new ArrayList();

            for (int i = 0; i < totalPage; i++) {
                totalItem.add(i);
            }

            System.out.println("total page = " + totalPage);

            List<MovieEntity> entities = iMovieService.getAllMovie(page, size, sort); // lay tat ca phim

            List<MovieDTO> results = new ArrayList<>();

            for (MovieEntity entity :
                    entities) {
                MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
                results.add(mo);
            }
            SearchMovie sm = new SearchMovie();
            model.addAttribute("search",sm);
            User loginedUser = (User) ((Authentication) principal).getPrincipal();
            System.out.println(loginedUser.getUsername());

            //map TypeEnum vao map
            Map<TypeEnum, String> typeMap = new HashMap<>();
            Arrays.asList(TypeEnum.values()).forEach(typeEnum -> typeMap.put(typeEnum,typeEnum.getTypeValue()));

            Map<CategoryEnum, String> CategoryMap = new HashMap<>();
            Arrays.asList(CategoryEnum.values()).forEach(categoryEnum -> CategoryMap.put(categoryEnum,categoryEnum.getCategoryValue()));


            if(principal != null){
                List<UserRegister> list = iUserService.getUser(principal.getName());
                model.addAttribute("list",list);
            }
            String userInfo = WebUtils.toString(loginedUser);
            model.addAttribute("typeMap",typeMap);
            model.addAttribute("CategoryMap",CategoryMap);

            model.addAttribute("userInfo", userInfo);
            model.addAttribute("total", totalMovie);
            model.addAttribute("listAll", results);
            model.addAttribute("totalItem", totalItem);

            return "admin/movieManager";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/404";
        }
    }

    // sua phim
    @GetMapping("/admin/admin-movie-edit/{id}")
    public String editMovie(@PathVariable(name = "id") Long id, Model model,Principal principal) {
        MovieDTO mo = iMovieService.findById(id);
        System.out.println(mo.getName());
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("dto", mo);
        return "admin/editMovie";
    }

    // them phim
    @GetMapping("/admin/admin-movie-add")
    public String addMovie(Model model,Principal principal) {
        MovieDTO mo = new MovieDTO();
        //map TypeEnum vao map
        Map<TypeEnum, String> typeMap = new HashMap<>();
        Arrays.asList(TypeEnum.values()).forEach(typeEnum -> typeMap.put(typeEnum,typeEnum.getTypeValue()));

        Map<CategoryEnum, String> CategoryMap = new HashMap<>();

        Arrays.asList(CategoryEnum.values()).forEach(categoryEnum -> CategoryMap.put(categoryEnum,categoryEnum.getCategoryValue()));
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("CategoryMap",CategoryMap);
        model.addAttribute("typeMap",typeMap);
        model.addAttribute("dto", mo);
        return "admin/addMovie";
    }

    //luu phim
    @PostMapping("/admin/admin-movie-save")
    public String saveMovie(@ModelAttribute("dto") MovieDTO movieDTO, Model model,Principal principal) throws ParseException {
        iMovieService.save(movieDTO);
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        MovieDTO mo = iMovieService.findById(movieDTO.getId());
        model.addAttribute("dto", movieDTO);
        return "admin/updateSuccessful";
    }

    // xoa phim
    @GetMapping("/admin/admin-movie-delete/{id}")
    public String deleteMovie(@PathVariable(name = "id") Long id) {
        iMovieService.deleteById(id);
        return "redirect:/admin/admin-movie";
    }

    // tim kiem
    @PostMapping("/admin/search-movie")
    public String searchMovie(Model model,@ModelAttribute("search") SearchMovie searchMovie,Principal principal){
        List<MovieDTO> results = iMovieService.searchMovie(searchMovie);
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("listAll", results);
        return "admin/listSearch";
    }
}
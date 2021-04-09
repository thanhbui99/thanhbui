package com.java.web.moviefinal2.controllers.web;

import com.java.web.moviefinal2.converter.MovieConverter;
import com.java.web.moviefinal2.converter.UserConverter;
import com.java.web.moviefinal2.dto.*;
import com.java.web.moviefinal2.entity.AppUser;
import com.java.web.moviefinal2.entity.ImageEntity;
import com.java.web.moviefinal2.entity.MovieEntity;
import com.java.web.moviefinal2.enums.CategoryEnum;
import com.java.web.moviefinal2.enums.CountryEnum;
import com.java.web.moviefinal2.enums.CountrySeriesMovieEnum;
import com.java.web.moviefinal2.enums.YearEnum;
import com.java.web.moviefinal2.repository.ImageRepository;
import com.java.web.moviefinal2.repository.UserRepository;
import com.java.web.moviefinal2.repository.custom.impl.AppUserDAO;
import com.java.web.moviefinal2.service.*;
import com.java.web.moviefinal2.utils.EncrytedPasswordUtils;
import com.java.web.moviefinal2.utils.StringUtils;
import com.java.web.moviefinal2.utils.WebUtils;
import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    private String awsS3AudioBucket;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private IMovieService iMovieService;

    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IImageService iImageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/")
    public String home(Model model,Principal principal) {
        List<MovieDTO> movieDTOS8 = iMovieService.findFIlmLe8();
        List<MovieDTO> movieDTOS4 = iMovieService.findFIlmLe4();
        List<MovieDTO> movieDTOSSeries = iMovieService.getAllSeries();
        List<MovieDTO> movieDTOSCartoon = iMovieService.getAllCartoon();
        getNavMap(model);
        SearchMovie searchMovie = new SearchMovie();

        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }

        model.addAttribute("movieDTOSSeries",movieDTOSSeries);
        model.addAttribute("movieDTOSCartoon",movieDTOSCartoon);
        model.addAttribute("obj", searchMovie);
        model.addAttribute("movieDTOS", movieDTOS8);
        model.addAttribute("movieDTOS4", movieDTOS4);
        return "web/homeMovie";
    }

    @GetMapping("/detail-movie/{id}")
    public String detailMovie(@PathVariable Long id, Model model,Principal principal) {
        getNavMap(model);
        getIdMovie(id, model);
        SearchMovie searchMovie = new SearchMovie();
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("obj", searchMovie);
        return "web/detailMovie";
    }

    @GetMapping("/play-movie/{id}")
    public String playMovie(@PathVariable Long id, Model model,Principal principal) {
//        MovieDTO mo = iMovieService.findById(id);
//        List<MovieDTO> listSingle = new ArrayList<>();
//        listSingle.add(mo);
//        System.out.println(mo.getName());
//        model.addAttribute("dto", listSingle);
        getNavMap(model);
        MovieDTO mo = iMovieService.findById(id);
        List<MovieDTO> listSingle = new ArrayList<>();
        listSingle.add(mo);
        System.out.println(mo.getName());
        CommentDTO commentDTO = new CommentDTO();

        List<MovieDTO> movieByCode = iMovieService.getMovieByCode(mo.getCode());


        List<CommentDTO> allCommentByMovieId = iCommentService.getAllCommentByMovieId(id);

        return getImageUser(principal, model, listSingle, commentDTO, movieByCode, allCommentByMovieId);
    }

    @GetMapping("/play-movie/{code}/{episode}")
    public String playMovieByEpisode(@PathVariable("code") String code,Principal principal, @PathVariable("episode") String episode, Model model) {
//        MovieDTO mo = iMovieService.findById(id);
//        List<MovieDTO> listSingle = new ArrayList<>();
//        listSingle.add(mo);
//        System.out.println(mo.getName());
//        model.addAttribute("dto", listSingle);
        getNavMap(model);
        List<MovieDTO> movieByCodeAndEpisode = iMovieService.getMovieByCodeAndEpisode(code, episode);
        MovieDTO mo = movieByCodeAndEpisode.get(0);
        List<MovieDTO> listSingle = new ArrayList<>();
        listSingle.add(mo);
        System.out.println(mo.getName());
        CommentDTO commentDTO = new CommentDTO();

        List<MovieDTO> movieByCode = iMovieService.getMovieByCode(mo.getCode());


        List<CommentDTO> allCommentByMovieId = iCommentService.getAllCommentByMovieId(mo.getId());


        return getImageUser(principal, model, listSingle, commentDTO, movieByCode, allCommentByMovieId);
    }

    private String getImageUser(Principal principal, Model model, List<MovieDTO> listSingle, CommentDTO commentDTO, List<MovieDTO> movieByCode, List<CommentDTO> allCommentByMovieId) {
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("dto", listSingle);
        model.addAttribute("userComment", commentDTO);
        model.addAttribute("listComment", allCommentByMovieId);
        model.addAttribute("movieByCode", movieByCode);

        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        return "web/playMovie";
    }

    private void getIdMovie(@PathVariable Long id, Model model) {
        MovieDTO mo = iMovieService.findById(id);
        List<MovieDTO> listSingle = new ArrayList<>();
        listSingle.add(mo);
        System.out.println(mo.getName());
        CommentDTO commentDTO = new CommentDTO();

        List<CommentDTO> allCommentByMovieId = iCommentService.getAllCommentByMovieId(id);

        model.addAttribute("dto", listSingle);
        model.addAttribute("userComment", commentDTO);
        model.addAttribute("listComment", allCommentByMovieId);
    }

    @GetMapping("/register-user")
    public String test(Model model) {
        UserRegister userRegister = new UserRegister();
        model.addAttribute("userRegister", userRegister);
        return "register";
    }

    @GetMapping("/my-movie")
    public String myMovie(Model model, Principal principal) {
        getNavMap(model);

        List<MovieDTO> movieDTOS = iMovieService.getMyMovie(principal.getName());

        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        model.addAttribute("movieDTOS", movieDTOS);
        return "web/myMovie";
    }

    @GetMapping("/delete-my-movie/{id}")
    public String deleteMovieInMyMovie(@PathVariable Long id){
         iMovieService.deleteMovieInMyMovie(id);
        return "redirect:/my-movie";
    }


    @GetMapping("/phim-le")
    public String oddFilm(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "18") Integer size,
                          @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                          Model model, Principal principal) {


        getNavMap(model);
        Long totalMovie = iMovieService.count();
        StringBuilder sb = new StringBuilder("" + totalMovie + "");
        Integer total = Integer.parseInt(sb.toString());
        int totalPage = (int) Math.ceil(((double) total / (double) size));

        List totalItem = new ArrayList();

        for (int i = 0; i < totalPage; i++) {
            totalItem.add(i);
        }
        List<MovieEntity> entities = iMovieService.getMovieOddFilm(page, size, sort);

        List<MovieDTO> results = new ArrayList<>();

        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        model.addAttribute("listAll", results);
        model.addAttribute("totalItem", totalItem);
        return "web/listSearchWeb";
    }

    @GetMapping("/phim-le/{year}")
    public String oddFilm(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "18") Integer size,
                          @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                          Model model, Principal principal, @PathVariable String year) {


        getNavMap(model);
        Long totalMovie = iMovieService.count();
        StringBuilder sb = new StringBuilder("" + totalMovie + "");
        Integer total = Integer.parseInt(sb.toString());
        int totalPage = (int) Math.ceil(((double) total / (double) size));

        List totalItem = new ArrayList();

        for (int i = 0; i < totalPage; i++) {
            totalItem.add(i);
        }
        List<MovieEntity> entities = iMovieService.getByTypeAndYear(year, page, size, sort);

        List<MovieDTO> results = new ArrayList<>();

        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        model.addAttribute("listAll", results);
        model.addAttribute("totalItem", totalItem);
        return "web/listSearchWeb";
    }

    @GetMapping("/phim-bo/{country}")
    public String seriesFilm(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                             @RequestParam(name = "size", required = false, defaultValue = "18") Integer size,
                             @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                             Model model, Principal principal, @PathVariable String country) {


        String countryValue = CountryEnum.valueOf(country).getCountryValue();


        getNavMap(model);
        Long totalMovie = iMovieService.count();
        StringBuilder sb = new StringBuilder("" + totalMovie + "");
        Integer total = Integer.parseInt(sb.toString());
        int totalPage = (int) Math.ceil(((double) total / (double) size));

        List totalItem = new ArrayList();

        for (int i = 0; i < totalPage; i++) {
            totalItem.add(i);
        }
        List<MovieEntity> entities = iMovieService.getByTypeAndCountrySeriesFilm(countryValue, page, size, sort);

        List<MovieDTO> results = new ArrayList<>();

        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        model.addAttribute("listAll", results);
        model.addAttribute("totalItem", totalItem);
        return "web/listSearchWeb";
    }

    @GetMapping("/quoc-gia/{country}")
    public String movieByCountry(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(name = "size", required = false, defaultValue = "18") Integer size,
                                 @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                                 Model model, Principal principal, @PathVariable String country) {


        String countryValue = CountryEnum.valueOf(country).getCountryValue();


        getNavMap(model);
        Long totalMovie = iMovieService.count();
        StringBuilder sb = new StringBuilder("" + totalMovie + "");
        Integer total = Integer.parseInt(sb.toString());
        int totalPage = (int) Math.ceil(((double) total / (double) size));

        List totalItem = new ArrayList();

        for (int i = 0; i < totalPage; i++) {
            totalItem.add(i);
        }
        List<MovieEntity> entities = iMovieService.getMovieCountry(countryValue, page, size, sort);

        List<MovieDTO> results = new ArrayList<>();

        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        model.addAttribute("listAll", results);
        model.addAttribute("totalItem", totalItem);
        return "web/listSearchWeb";
    }

    @GetMapping("/phim-bo")
    public String seriesMovie(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "18") Integer size,
                              @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                              Model model, Principal principal) {


        getNavMap(model);
        Long totalMovie = iMovieService.count();
        StringBuilder sb = new StringBuilder("" + totalMovie + "");
        Integer total = Integer.parseInt(sb.toString());
        int totalPage = (int) Math.ceil(((double) total / (double) size));

        List totalItem = new ArrayList();

        for (int i = 0; i < totalPage; i++) {
            totalItem.add(i);
        }
        List<MovieEntity> entities = iMovieService.getMovieSeriesMovie(page, size, sort);

        List<MovieDTO> results = new ArrayList<>();

        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        SearchMovie searchMovie = new SearchMovie();
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("obj", searchMovie);
        model.addAttribute("listAll", results);
        model.addAttribute("totalItem", totalItem);
        return "web/listSearchWeb";
    }

    @GetMapping("/the-loai/{category}")
    public String countryMovie(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "18") Integer size,
                               @RequestParam(name = "sort", required = false, defaultValue = "id") String sort,
                               Model model, Principal principal, @PathVariable String category) {


        getNavMap(model);
        Long totalMovie = iMovieService.count();
        StringBuilder sb = new StringBuilder("" + totalMovie + "");
        Integer total = Integer.parseInt(sb.toString());
        int totalPage = (int) Math.ceil(((double) total / (double) size));

        List totalItem = new ArrayList();

        for (int i = 0; i < totalPage; i++) {
            totalItem.add(i);
        }
        List<MovieEntity> entities = iMovieService.getMovieByCategory(category, page, size, sort);

        List<MovieDTO> results = new ArrayList<>();

        for (MovieEntity entity :
                entities) {
            MovieDTO mo = movieConverter.convertToDTO(Optional.ofNullable(entity));
            results.add(mo);
        }
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        SearchMovie searchMovie = new SearchMovie();
        model.addAttribute("obj", searchMovie);
        model.addAttribute("listAll", results);
        model.addAttribute("totalItem", totalItem);
        return "web/listSearchWeb";
    }


    private void getNavMap(Model model) {
        Map<CategoryEnum, String> CategoryMap = new HashMap<>();
        Arrays.asList(CategoryEnum.values()).forEach(categoryEnum ->
                CategoryMap.put(
                        categoryEnum,
                        categoryEnum.getCategoryValue()
                )
        );

        Map<String, String> yearEnum = new HashMap<>();
        Arrays.asList(YearEnum.values()).forEach(yearEnums ->
                yearEnum.put(yearEnums.getYearNumberValue(), yearEnums.getYearValue()
                )
        );

        Map<CountryEnum, String> CountryMap = new HashMap<>();
        Arrays.asList(CountryEnum.values()).forEach(countryEnum ->
                CountryMap.put(countryEnum, countryEnum.getCountryValue()
                )
        );

        Map<CountrySeriesMovieEnum, String> countrySeriesMovieEnum = new HashMap<>();
        Arrays.asList(CountrySeriesMovieEnum.values()).forEach(countrySeriesMovieEnum1 ->
                countrySeriesMovieEnum.put
                        (countrySeriesMovieEnum1,
                                countrySeriesMovieEnum1.getCountrySeriesMovieEnumValue()
                        )
        );
        model.addAttribute("countrySeriesMovieEnum", countrySeriesMovieEnum);
        model.addAttribute("CountryMap", CountryMap);
        model.addAttribute("yearEnum", yearEnum);
        model.addAttribute("CategoryMap", CategoryMap);
    }

    @PostMapping("/comment-user/{movieId}")
    public String comment(@PathVariable Long movieId, Model model
            , @ModelAttribute CommentDTO commentDTO, Principal principal) {
        if (principal != null) {
            iCommentService.saveComment(commentDTO.getContent(), movieId, principal.getName());
            return "redirect:/detail-movie/" + commentDTO.getMovieId();
        } else return "redirect:/login";
    }

    @GetMapping("my-movie-add/{movieId}")
    public String myMovieAdd(@PathVariable Long movieId, Principal principal) {

        if (principal == null){
            return "redirect:/login";
        }

        iMovieService.saveMyMovie(movieId, principal.getName());
        return "redirect:/detail-movie/" + movieId.toString();
    }

    @GetMapping("/search")
    public String searchMovieForViewer(@ModelAttribute SearchMovie searchMovie, Model model,Principal principal) {
        List<MovieDTO> results = iMovieService.searchMovieForViewer(searchMovie);
        SearchMovie searchMovies = new SearchMovie();
        String mess = "Tìm kiếm : " + searchMovie.getSearch();
        if(principal != null){
            List<UserRegister> list = iUserService.getUser(principal.getName());
            model.addAttribute("list",list);
        }
        model.addAttribute("mess", mess);
        model.addAttribute("obj", searchMovies);
        model.addAttribute("listAll", results);
        return "web/listSearchWeb";
    }

    @PostMapping("/edit-form-user")
    public String editUser(@ModelAttribute UserEdit userEdit, Principal principal) {
        AppUser user = userRepository.findByUserName(principal.getName());
        if (passwordEncoder.matches(userEdit.getPassCurrent(), user.getEncrytedPassword())
                && userEdit.getNewPass().equals(userEdit.getConfirmNewPass())) {
            user.setEncrytedPassword(passwordEncoder.encode(userEdit.getNewPass()));
            userRepository.save(user);
        }
        List<AppUser> users = new ArrayList<>();
        MultipartFile fileDatas = userEdit.getFileDatas();
        String name = fileDatas.getOriginalFilename();
        if (!name.equals("")) {
            ImageEntity imageEntity = new ImageEntity();
            if (iImageService.getByKeyImage(name).size() == 0) {
                imageEntity.setKeyImage(name);
                imageEntity.setObjectUrl("https://" + amazonS3ClientService.nameBucket() + ".s3.amazonaws.com/" + name);
                this.amazonS3ClientService.uploadFileToS3Bucket(fileDatas, true);
                user.setImage(imageEntity);
                users.add(user);
                imageEntity.setUser(users);
                iImageService.save(imageEntity);
            }
            ImageEntity imageEntity1 = imageRepository.findByKeyImage(name);
            user.setImage(imageEntity1);
            users.add(user);
            imageEntity1.setUser(users);
            iImageService.save(imageEntity1);
        }
        if(principal.getName().equals("admin")){
            return "redirect:admin/admin-movie";
        }
        return "redirect:/userInfo";
    }

}

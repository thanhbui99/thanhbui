package com.java.web.moviefinal2.utils;

import com.java.web.moviefinal2.enums.CategoryEnum;
import com.java.web.moviefinal2.enums.CountryEnum;
import com.java.web.moviefinal2.enums.CountrySeriesMovieEnum;
import com.java.web.moviefinal2.enums.YearEnum;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NavUtils {
    private static void getNavMap(Model model) {
        Map<String, String> CategoryMap = new HashMap<>();
        Arrays.asList(CategoryEnum.values()).forEach(categoryEnum ->
                CategoryMap.put(
                        categoryEnum.getCategoryAddress().toLowerCase(),
                        categoryEnum.getCategoryValue()
                )
        );

        Map<YearEnum, String> yearEnum = new HashMap<>();
        Arrays.asList(YearEnum.values()).forEach(yearEnums ->
                yearEnum.put(yearEnums, yearEnums.getYearValue()
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
}

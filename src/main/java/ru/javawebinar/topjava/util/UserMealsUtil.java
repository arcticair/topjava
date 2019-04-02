package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 110),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> rt =  getFilteredWithExceededWithLoops(mealList, LocalTime.of(7, 0), LocalTime.of(11,0), 2030);
        List<UserMealWithExceed> rrt =  getFilteredWithExceededWithStreams(mealList, LocalTime.of(7, 0), LocalTime.of(11,0), 2030);
        rt.forEach(System.out::println);
        System.out.println("===");
        rt.forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceededWithStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> collect = mealList.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream().filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)).map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), collect.get(m.getDateTime().toLocalDate())> caloriesPerDay)).collect(Collectors.toList());
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededWithLoops(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        int totalCalories = 0;
        List<UserMealWithExceed> res = new ArrayList<>();
        Boolean bu = false;
        for (UserMeal meal: mealList){
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)){
                totalCalories += meal.getCalories();
            }
        }
        if (totalCalories > caloriesPerDay)
            bu = true;
        for (UserMeal meal: mealList){
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)){
                res.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), bu));
            }
        }
        return res;
    }
}

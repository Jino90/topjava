package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        boolean excess;
        int summaryCallories = 0;
        for (UserMeal userMeal: meals){
            if (userMeal.getDateTime().toEpochSecond(ZoneOffset.UTC) >
                    startTime.atDate(userMeal.getDateTime().toLocalDate()).toEpochSecond(ZoneOffset.UTC) &&
                    userMeal.getDateTime().toEpochSecond(ZoneOffset.UTC) <
                    endTime.atDate(userMeal.getDateTime().toLocalDate()).toEpochSecond(ZoneOffset.UTC)){
                for (UserMeal userMeal1: meals){
                    if (Date.from(userMeal.getDateTime().atZone(ZoneOffset.UTC).toInstant()).equals(
                            Date.from(userMeal1.getDateTime().atZone(ZoneOffset.UTC).toInstant()))){
                        summaryCallories = summaryCallories + userMeal1.getCalories();
                    }
                }
                excess = summaryCallories > caloriesPerDay;
                userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess));
            }
            summaryCallories = 0;
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream().filter(m -> m.getDateTime().toEpochSecond(ZoneOffset.UTC) >
                startTime.atDate(m.getDateTime().toLocalDate()).toEpochSecond(ZoneOffset.UTC) &&
                m.getDateTime().toEpochSecond(ZoneOffset.UTC) <
                        endTime.atDate(m.getDateTime().toLocalDate()).toEpochSecond(ZoneOffset.UTC)).map(
                m -> new UserMealWithExcess(
                m.getDateTime(), m.getDescription(), m.getCalories(), caloriesPerDay <= meals.stream().filter(m1 ->
                Date.from(m1.getDateTime().atZone(ZoneOffset.UTC).toInstant()).equals(
                        Date.from(m.getDateTime().atZone(ZoneOffset.UTC).toInstant()))).mapToInt(
                                UserMeal::getCalories).sum())).collect(Collectors.toList());
    }
}

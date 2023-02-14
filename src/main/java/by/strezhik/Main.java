package by.strezhik;

import by.strezhik.model.*;
import by.strezhik.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static final String FEMALE = "Female";
    public static final String A = "A";
    public static final String HUNGARIAN = "Hungarian";
    public static final String MALE = "Male";
    public static final String OCEANIA = "Oceania";
    public static final String INDONESIAN = "Indonesian";
    public static final String CIVIL_BUILDING = "Civil building";
    public static final String HOSPITAL = "Hospital";
    public static final String GLASS = "Glass";
    public static final String STEEL = "Steel";
    public static final String ALUMINUM = "Aluminum";
    public static final char CHAR_C = 'C';
    public static final char CHAR_S = 'S';
    public static final String JAGUAR = "Jaguar";
    public static final String WHITE = "White";
    public static final String BMW = "BMW";
    public static final String LEXUS = "Lexus";
    public static final String CHRYSLER = "Chrysler";
    public static final String TOYOTA = "Toyota";
    public static final String DODGE = "Dodge";
    public static final String GMC = "GMC";
    public static final String BLACK = "Black";
    public static final String CIVIC = "Civic";
    public static final String CHEROKEE = "Cherokee";
    public static final String YELLOW = "Yellow";
    public static final String RED = "Red";
    public static final String BLUE = "Blue";
    public static final String GREEN = "Green";
    public static final String NUMBER_IN_VIN = "59";
    public static final double CONVERSION_TO_TON = 0.001;
    public static final double TRANSPORTATION_COST_PER_TON = 7.14;
    public static final String ENGINEER = "Engineer";

    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        AtomicInteger index = new AtomicInteger(0);
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .collect(Collectors.groupingBy(x -> index.getAndIncrement() / 7))
                .entrySet().stream()
                .skip(2)
                .limit(1)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .filter(animal -> FEMALE.equals(animal.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith(A))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long female = animals.stream()
                .filter(animal -> FEMALE.equals(animal.getGender()))
                .count();
        System.out.println(female);

    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean b = animals.stream()
                .filter(animal -> animal.getAge() >= 20)
                .filter(animal -> animal.getAge() <= 30)
                .anyMatch(animal -> HUNGARIAN.equals(animal.getOrigin()));
        System.out.println(b);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean b = animals.stream()
                .allMatch(animal -> FEMALE.equals(animal.getGender()) || MALE.equals(animal.getGender()));
        System.out.println(b);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean b = animals.stream()
                .noneMatch(animal -> OCEANIA.equals(animal.getOrigin()));
        System.out.println(b);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparing(Animal::getAge))
                .ifPresent(animal -> System.out.println(animal.getAge()));
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparing(chars -> chars.length))
                .ifPresent(chars -> System.out.println(chars.length));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .mapToInt(Animal::getAge)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        Double average = animals.stream()
                .filter(animal -> INDONESIAN.equals(animal.getOrigin()))
                .collect(Collectors.averagingInt(Animal::getAge));
        System.out.println(average);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        LocalDate underage = LocalDate.now().minusYears(18);
        LocalDate toOld = LocalDate.now().minusYears(27);
        people.stream()
                .filter(person -> MALE.equals(person.getGender()))
                .filter(person -> underage.isAfter(person.getDateOfBirth()))
                .filter(person -> toOld.isBefore(person.getDateOfBirth()))
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Stream.concat(houses.stream()
                                .filter(house -> HOSPITAL.equals(house.getBuildingType()))
                                .flatMap(house -> house.getPersonList().stream()),
                        houses.stream()
                                .filter(house -> CIVIL_BUILDING.equals(house.getBuildingType()))
                                .flatMap(house -> house.getPersonList().stream())
                                .sorted(new PersonComparator()))
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        Stream.of(cars.stream()
                                .filter(Main::toTurkmenistan)
                                .mapToDouble(c -> c.getMass() * CONVERSION_TO_TON * TRANSPORTATION_COST_PER_TON)
                                .sum(),
                        cars.stream()
                                .filter(car -> !toTurkmenistan(car))
                                .filter(Main::toUzbekistan)
                                .mapToDouble(c -> c.getMass() * CONVERSION_TO_TON * TRANSPORTATION_COST_PER_TON)
                                .sum(),
                        cars.stream()
                                .filter(car -> !toTurkmenistan(car))
                                .filter(car -> !toUzbekistan(car))
                                .filter(Main::toKazakhstan)
                                .mapToDouble(c -> c.getMass() * CONVERSION_TO_TON * TRANSPORTATION_COST_PER_TON)
                                .sum(),
                        cars.stream()
                                .filter(car -> !toTurkmenistan(car))
                                .filter(car -> !toUzbekistan(car))
                                .filter(car -> !toKazakhstan(car))
                                .filter(Main::toKyrgyzstan)
                                .mapToDouble(c -> c.getMass() * CONVERSION_TO_TON * TRANSPORTATION_COST_PER_TON)
                                .sum(),
                        cars.stream()
                                .filter(car -> !toTurkmenistan(car))
                                .filter(car -> !toUzbekistan(car))
                                .filter(car -> !toKazakhstan(car))
                                .filter(car -> !toKyrgyzstan(car))
                                .filter(Main::toRussia)
                                .mapToDouble(c -> c.getMass() * CONVERSION_TO_TON * TRANSPORTATION_COST_PER_TON)
                                .sum(),
                        cars.stream()
                                .filter(car -> !toTurkmenistan(car))
                                .filter(car -> !toUzbekistan(car))
                                .filter(car -> !toKazakhstan(car))
                                .filter(car -> !toKyrgyzstan(car))
                                .filter(car -> !toRussia(car))
                                .filter(Main::toMongolia)
                                .mapToDouble(c -> c.getMass() * CONVERSION_TO_TON * TRANSPORTATION_COST_PER_TON)
                                .sum())
                .peek(System.out::println)
                .reduce(Double::sum)
                .ifPresent(System.out::println);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin)
                        .thenComparing(Flower::getPrice)
                        .thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(flower -> CHAR_C >= flower.getCommonName().charAt(0) ||
                        CHAR_S <= flower.getCommonName().charAt(0))
                .filter(Flower::isShadePreferred)
                .filter(flower -> flower.getFlowerVaseMaterial().stream()
                        .anyMatch(material -> GLASS.equals(material) || STEEL.equals(material) || ALUMINUM.equals(material)))
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 365 * 5 * 1.39 * CONVERSION_TO_TON)
                .reduce(Double::sum)
                .ifPresent(System.out::println);
    }

    private static boolean toTurkmenistan(Car car) {
        return JAGUAR.equals(car.getCarMake()) || WHITE.equals(car.getColor());
    }

    private static boolean toUzbekistan(Car car) {
        return BMW.equals(car.getCarMake()) || LEXUS.equals(car.getCarMake())
                || CHRYSLER.equals(car.getCarMake()) || TOYOTA.equals(car.getCarMake()) || car.getMass() <= 1500;
    }

    private static boolean toKazakhstan(Car car) {
        return GMC.equals(car.getCarMake()) || DODGE.equals(car.getCarMake())
                || BLACK.equals(car.getColor()) || car.getMass() == 4000;
    }

    private static boolean toKyrgyzstan(Car car) {
        return CIVIC.equals(car.getCarModel()) || CHEROKEE.equals(car.getCarModel())
                || car.getReleaseYear() <= 1982;
    }

    private static boolean toRussia(Car car) {
        return !YELLOW.equals(car.getColor()) && !RED.equals(car.getColor())
                && !GREEN.equals(car.getColor()) && !BLUE.equals(car.getColor())
                || car.getPrice() >= 40000;
    }

    private static boolean toMongolia(Car car) {
        return car.getVin().contains(NUMBER_IN_VIN);
    }

    private static void task16() throws IOException {
        List<Person> people = Util.getPersons();
        boolean engineer = people.stream()
                .filter(person -> person.getId() % 2 == 0)
                .filter(person -> person.getId() % 8 != 0)
                .filter(person -> Main.IsSumOfDigits7(person.getId()))
                .collect(Collectors.groupingBy(Person::getRecruitmentGroup,
                        Collectors.mapping(Person::getOccupation, Collectors.toList())))
                .entrySet().stream()
                .skip(1)
                .limit(1)
                .flatMap(entry -> entry.getValue().stream())
                .anyMatch(string -> string.contains(ENGINEER));
        System.out.println(engineer);
    }

    private static boolean IsSumOfDigits7(int i) {
        Integer integer = i;
        String[] split = integer.toString().split("");
        return Arrays.stream(split)
                .mapToInt(Integer::parseInt)
                .sum() != 11;
    }

    private static class PersonComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            LocalDate d1 = o1.getDateOfBirth();
            LocalDate d2 = o2.getDateOfBirth();
            LocalDate now = LocalDate.now();
            LocalDate underage = now.minusYears(18);
            LocalDate pensionForMen = now.minusYears(63);
            LocalDate pensionForWomen = now.minusYears(58);
            if (underage.isBefore(d1) || pensionForMen.isAfter(d1)
                    || FEMALE.equals(o1.getGender()) && pensionForWomen.isAfter(d1)) {
                if (underage.isBefore(d2) || pensionForMen.isAfter(d2)
                        || FEMALE.equals(o2.getGender()) && pensionForWomen.isAfter(d2))
                    return 0;
                else return -1;
            } else {
                if (underage.isBefore(d2) || pensionForMen.isAfter(d2)
                        || FEMALE.equals(o2.getGender()) && pensionForWomen.isAfter(d2))
                    return 1;
            }
            return 0;
        }
    }
}

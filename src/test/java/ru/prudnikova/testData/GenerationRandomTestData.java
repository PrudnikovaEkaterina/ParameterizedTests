package ru.prudnikova.testData;

import com.github.javafaker.Faker;

public class GenerationRandomTestData {
    private final static String[] ZHK={"Лучи", "Южные Сады", "Измайловский парк"};

    public static TestData generateRandomTestData(){
        TestData testData=new TestData();
        Faker faker=new Faker();
        testData.setZhk(faker.options().nextElement(ZHK));
        return testData;
    }
}

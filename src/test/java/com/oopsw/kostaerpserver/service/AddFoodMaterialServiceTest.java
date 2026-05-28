package com.oopsw.kostaerpserver.service;

import com.oopsw.kostaerpserver.repository.AddFoodMaterialDAO;
import com.oopsw.kostaerpserver.vo.FoodCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
public class AddFoodMaterialServiceTest {
    @Autowired
    AddFoodMaterialService addFoodMaterialService;

    @Test
    public void getFoodCategoryList() {
        System.out.println(addFoodMaterialService.getFoodCategoryList());
    }
}

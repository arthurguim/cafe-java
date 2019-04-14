package com.arthurguim.cafe.cafe;

import com.arthurguim.cafe.cafe.controller.Validator;
import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CafeApplication.class)
public class IngredientTest {

	@Autowired
	private Validator validator;

	private final String INVALID_INGREDIENT_NAME = "4893yu";

	// Create invalid ingredient
	@Test(expected = IngredientNotFoundException.class)
	public void creatingInvalidIngredients() throws IngredientNotFoundException {

		validator.validateIngredient(this.INVALID_INGREDIENT_NAME);
	}
}

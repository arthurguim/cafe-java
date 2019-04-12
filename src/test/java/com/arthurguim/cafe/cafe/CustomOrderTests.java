package com.arthurguim.cafe.cafe;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.arthurguim.cafe.cafe.controller.Validator;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.model.Order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

// TODO: do right exception
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CafeApplication.class)
public class CustomOrderTests {

	@Autowired
	private Validator validator;

	private final List<String> VALID_INGREDIENTS = Arrays.asList("queijo", "hamburguer de carne", "bacon");
	private final List<String> INVALID_INGREDIENTS = Arrays.asList("picles", "hamburguer", "bacon");

	@Test
	public void creatingCustomOrderValidIngredients() throws Exception {
		List<Ingredient> ingredients = new ArrayList<>();
		for (String ingredientName : VALID_INGREDIENTS) {
			Ingredient ingredient = validator.validateIngredient(ingredientName);
			ingredient.setQuantity(1);
			ingredients.add(ingredient);
		}
		
		Order order = new Order(ingredients);

		Double totalExpected = .0;
		for (Ingredient ingredient : ingredients) {
			totalExpected += ingredient.getPrice() * ingredient.getQuantity();
		}

		assertEquals(totalExpected, order.getTotalPrice());
	}

	@Test(expected = Exception.class)
	public void creatingCustomOrderInvalidIngredients() throws Exception {
		List<Ingredient> ingredients = new ArrayList<>();
		for (String ingredientName : INVALID_INGREDIENTS) {
			Ingredient ingredient = validator.validateIngredient(ingredientName);
			ingredient.setQuantity(1);
			ingredients.add(ingredient);
		}
		
		new Order(ingredients);
	}
}

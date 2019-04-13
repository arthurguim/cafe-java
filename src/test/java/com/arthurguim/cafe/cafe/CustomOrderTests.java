package com.arthurguim.cafe.cafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.arthurguim.cafe.cafe.controller.IngredientValidator;
import com.arthurguim.cafe.cafe.controller.SalesController;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.model.Order;
import com.arthurguim.cafe.cafe.model.sales.ProportionalSale;
import com.arthurguim.cafe.cafe.model.sales.QuantitativeSale;
import com.arthurguim.cafe.cafe.repository.IngredientRepository;
import com.arthurguim.cafe.cafe.repository.ProportionalSaleRepository;
import com.arthurguim.cafe.cafe.repository.QuantitativeSaleRepository;

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
	private IngredientValidator ingredientValidator;

	@Autowired
	private QuantitativeSaleRepository quantitativeSaleRepository;

	@Autowired
	private ProportionalSaleRepository proportionalSaleRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private SalesController salesController;

	private final List<String> VALID_INGREDIENTS = Arrays.asList("queijo", "hamburguer de carne", "bacon");
	private final List<String> INVALID_INGREDIENTS = Arrays.asList("picles", "hamburguer", "bacon");

	// Custom order with valid ingredients
	@Test
	public void creatingCustomOrderValidIngredients() throws Exception {
		List<Ingredient> ingredients = new ArrayList<>();
		for (String ingredientName : VALID_INGREDIENTS) {
			Ingredient ingredient = ingredientValidator.validateIngredient(ingredientName);
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

	// Custom order with invalid ingredients
	@Test(expected = Exception.class)
	public void creatingCustomOrderInvalidIngredients() throws Exception {
		List<Ingredient> ingredients = new ArrayList<>();
		for (String ingredientName : INVALID_INGREDIENTS) {
			Ingredient ingredient = ingredientValidator.validateIngredient(ingredientName);
			ingredient.setQuantity(1);
			ingredients.add(ingredient);
		}

		new Order(ingredients);
	}

	// Custom with one quantitativeDiscount
	@Test
	public void creatingCustomOrderWithOneQuantitativeDiscount() throws Exception {
		List<Ingredient> ingredients = new ArrayList<>();

		// Get one quantitative sale
		Optional<QuantitativeSale> quantitativeSalesOpt = quantitativeSaleRepository.findById(1L);

		if(!quantitativeSalesOpt.isPresent()) {
			fail("There was no quantitative sales on test database");
		}

		QuantitativeSale quantitativeSale = quantitativeSalesOpt.get();

		// Get includant ingredient
		Optional<Ingredient> ingredientOpt = ingredientRepository.findById(quantitativeSale.getIncludantIngredientId());

		if(!ingredientOpt.isPresent()) {
			fail("There was no ingredients on test database");
		}

		Ingredient ingredient = ingredientOpt.get();
		ingredient.setQuantity(2);

		ingredients.add(ingredient);

		Order order = new Order(ingredients);
		order = salesController.applySaleIfValid(order);

		// Order original price
		Double originalPrice = ingredient.getPrice() * ingredient.getQuantity();

		// Order expectedPrice
		Double expectedPrice = originalPrice * (1 - quantitativeSale.getSalePercentage() / 100);

		// Order discount
		Double discount = originalPrice - expectedPrice;

		assertEquals(expectedPrice, order.getTotalPrice());
		assertEquals(discount, order.getDiscount());
		assertTrue(order.getSaleName().contains(quantitativeSale.getName()));
	}

	@Test
	public void creatingCustomOrderWithOneProportionalDiscount() throws Exception {
		List<Ingredient> ingredients = new ArrayList<>();

		// Get one proportional sale
		Optional<ProportionalSale> proportionalSaleOpt = proportionalSaleRepository.findById(1L);

		if(!proportionalSaleOpt.isPresent()) {
			fail("There was no proportional sales on test database");
		}

		ProportionalSale proportionalSale = proportionalSaleOpt.get();

		// Get proportional ingredient
		Optional<Ingredient> ingredientOpt = ingredientRepository.findById(proportionalSale.getIngredientId());

		if(!ingredientOpt.isPresent()) {
			fail("There was no ingredient on test database");
		}

		Ingredient ingredient = ingredientOpt.get();
		ingredient.setQuantity(proportionalSale.getProportion());

		ingredients.add(ingredient);

		Order order = new Order(ingredients);
		order = salesController.applySaleIfValid(order);

		// Ingredient original price
		Double ingredientOriginalPrice = ingredient.getPrice() * ingredient.getQuantity();

		// Ingredient expected price
		Double ingredientExpectedPrice = ingredient.getPrice() * (ingredient.getQuantity() - 1);

		// Ingredient discount
		Double discount = ingredientOriginalPrice - ingredientExpectedPrice;

		assertEquals(ingredientExpectedPrice, order.getTotalPrice());
		assertEquals(discount, order.getDiscount());
		assertTrue(order.getSaleName().contains(proportionalSale.getName()));
	}
}

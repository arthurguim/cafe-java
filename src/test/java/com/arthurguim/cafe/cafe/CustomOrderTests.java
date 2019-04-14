package com.arthurguim.cafe.cafe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.controller.SalesController;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.model.Order;
import com.arthurguim.cafe.cafe.model.sales.ProportionalSale;
import com.arthurguim.cafe.cafe.model.sales.QuantitativeSale;
import com.arthurguim.cafe.cafe.repository.IngredientRepository;
import com.arthurguim.cafe.cafe.repository.ProportionalSaleRepository;
import com.arthurguim.cafe.cafe.repository.QuantitativeSaleRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = CafeApplication.class)
public class CustomOrderTests {

	@Autowired
	private QuantitativeSaleRepository quantitativeSaleRepository;

	@Autowired
	private ProportionalSaleRepository proportionalSaleRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private SalesController salesController;

	// Custom order with valid ingredients
	@Test
	public void creatingCustomOrderIngredients() throws IngredientNotFoundException {

		// Get all ingredients in the database
		List<Ingredient> ingredients = ingredientRepository.findAll();

		// If there's no ingredients in the database, fail
		if(ingredients.size() == 0) {
			fail("There's no ingredients in the test database");
		}

		// Set quantity to 1 for every ingredient
		ingredients.stream().forEach(ingredient -> ingredient.setQuantity(1));

		// Create order
		Order order = new Order(ingredients);
		order = salesController.applySaleIfValid(order);

		// Calculate expected values
		Double totalExpected = .0;
		for (Ingredient ingredient : ingredients) {
			totalExpected += ingredient.getPrice() * ingredient.getQuantity();
		}
		totalExpected = Precision.round(totalExpected, 2);

		assertEquals((Double) .0, order.getDiscount());
		assertEquals(0, order.getSaleName().size());
		assertEquals(StringUtils.EMPTY, order.getHamburguerName());
		assertEquals(true, order.isCustomHamburguer());
		assertEquals(totalExpected, order.getTotalPrice());
	}

	// Custom with one quantitative discount
	@Test
	public void creatingCustomOrderWithOneQuantitativeDiscount() {
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
		expectedPrice = Precision.round(expectedPrice, 2);

		// Order discount
		Double discount = originalPrice - expectedPrice;
		discount = Precision.round(discount, 2);

		assertEquals(expectedPrice, order.getTotalPrice());
		assertEquals(discount, order.getDiscount());
		assertTrue(order.getSaleName().contains(quantitativeSale.getName()));
		assertEquals(StringUtils.EMPTY, order.getHamburguerName());
		assertEquals(true, order.isCustomHamburguer());
	}

	// Custom with one proportional discount
	@Test
	public void creatingCustomOrderWithOneProportionalDiscount() {
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
		assertEquals(StringUtils.EMPTY, order.getHamburguerName());
		assertEquals(true, order.isCustomHamburguer());
	}
}

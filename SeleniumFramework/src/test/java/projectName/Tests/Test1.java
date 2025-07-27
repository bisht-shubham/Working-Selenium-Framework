package projectName.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import projectName.TestComponents.BaseTest;

public class Test1 extends BaseTest {

	public void selectLocation() {

		WebElement LocationPopupButton = driver.findElement(By.id("select_location_popup"));

		LocationPopupButton.click();

		WebElement LocationSearchBox = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='searchin']")));
		LocationSearchBox.sendKeys("Virar");

		WebElement firstSuggestedLocation = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[@class='pac-matched'])[2]")));
		firstSuggestedLocation.click();

		WebElement confirmLocationButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[@aria-label='button Confirm Location']")));
		confirmLocationButton.click();
	}

	public void searchProductsAndAddToCart() {

		WebElement searchProductsBox = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search in JioMart']")));
		searchProductsBox.sendKeys("Hide and Seek");

		WebElement firstAutocompleteSuggestion = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("autocomplete-0-suggestions-item-0")));
		firstAutocompleteSuggestion.click();

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='plp-card-container'] //button")));

		int availableProductsCount = driver.findElements(By.xpath("//div[@class='plp-card-container'] //button"))
				.size();

		List<WebElement> productAddToCartButtons = driver
				.findElements(By.xpath("//div[@class='plp-card-container'] //button"));

		for (WebElement buttons : productAddToCartButtons) {
			buttons.click();
		}

		String cartIconCount = driver.findElement(By.xpath("//div[@class='header-main-user-cart mini-cart'] //div"))
				.getText().trim();
		int cartIconCountInt = Integer.parseInt(cartIconCount);

		Assert.assertEquals(cartIconCountInt, availableProductsCount);
	}

	public void goToCart() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/checkout/cart']")));
		WebElement viewCartButton = driver.findElement(By.xpath("//a[@href='/checkout/cart']"));
		viewCartButton.click();
	}
	
	public void verifyPricesOfItemsWithTotal() {
		List<WebElement> productPrices = driver.findElements(By.xpath("//div[@class='products ng-star-inserted'] //div[@class='colsprt'] //*[@classname='price'] //span"));
		for(WebElement productPrice : productPrices) {
			String prices = productPrice.getText();
			System.out.println(prices);
		}
	}

	@Test
	public void completeJioMartPurchaseFlow() throws InterruptedException {
		selectLocation();
		searchProductsAndAddToCart();
		goToCart();
		verifyPricesOfItemsWithTotal();
	}
}

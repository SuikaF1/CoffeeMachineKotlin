
private const val ESPRESSO_WATER = 250
private const val ESPRESSO_MILK = 0
private const val ESPRESSO_COFFEE_BEANS = 16
private const val ESPRESSO_COST = 4
private const val LATTE_WATER = 350
private const val LATTE_MILK = 75
private const val LATTE_COFFEE_BEANS = 20
private const val LATTE_COST = 7
private const val CAPPUCCINO_WATER = 200
private const val CAPPUCCINO_MILK = 100
private const val CAPPUCCINO_COFFEE_BEANS = 12
private const val CAPPUCCINO_COST = 6

enum class CoffeeDrink(val water: Int, val milk: Int = 0, val coffeeBeans: Int, val cost: Int) {
    ESPRESSO(ESPRESSO_WATER, ESPRESSO_MILK, ESPRESSO_COFFEE_BEANS, ESPRESSO_COST),
    LATTE(LATTE_WATER, LATTE_MILK, LATTE_COFFEE_BEANS, LATTE_COST),
    CAPPUCCINO(CAPPUCCINO_WATER, CAPPUCCINO_MILK, CAPPUCCINO_COFFEE_BEANS, CAPPUCCINO_COST)
}
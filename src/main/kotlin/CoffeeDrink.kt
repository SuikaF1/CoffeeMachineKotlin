
private const val ESPRESSO_WATER = 250 // Amount of water used by machine to make espresso in milliliters.
private const val ESPRESSO_MILK = 0 // Amount of milk used by machine to make espresso in milliliters.
private const val ESPRESSO_COFFEE_BEANS = 16 // Amount of coffee beans used by machine to make espresso in grams.
private const val ESPRESSO_COST = 4 // The cost of the espresso.
private const val LATTE_WATER = 350 // Amount of water used by machine to make a latte in milliliters.
private const val LATTE_MILK = 75 // Amount of milk used by machine to make a latte in milliliters.
private const val LATTE_COFFEE_BEANS = 20 // Amount of coffee beans used to make a latte in grams.
private const val LATTE_COST = 7 // The cost of the latte.
private const val CAPPUCCINO_WATER = 200 // Amount of water used by machine to make a cappuccino in milliliters.
private const val CAPPUCCINO_MILK = 100 // Amount of milk used by machine to make a cappuccino in milliliters.
private const val CAPPUCCINO_COFFEE_BEANS = 12 // Amount of coffee beans used to make a cappuccino in grams.
private const val CAPPUCCINO_COST = 6 // The cost of the cappuccino.

/**
 * The enum class CoffeeDrink will hold three constants which are the three drinks available
 * from the coffee machine (Espresso, Latte, and Cappuccino). Each constant will hold four
 * variables, three for the amount of each ingredient needed and one for the cost of the drink.
 */
enum class CoffeeDrink(val water: Int, val milk: Int = 0, val coffeeBeans: Int, val cost: Int) {
    ESPRESSO(ESPRESSO_WATER, ESPRESSO_MILK, ESPRESSO_COFFEE_BEANS, ESPRESSO_COST),
    LATTE(LATTE_WATER, LATTE_MILK, LATTE_COFFEE_BEANS, LATTE_COST),
    CAPPUCCINO(CAPPUCCINO_WATER, CAPPUCCINO_MILK, CAPPUCCINO_COFFEE_BEANS, CAPPUCCINO_COST)
}
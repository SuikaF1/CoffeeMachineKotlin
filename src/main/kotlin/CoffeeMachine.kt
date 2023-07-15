const val DEFAULT_WATER = 400 // Default amount of water the machine has upon startup.
const val DEFAULT_MILK = 540 // Default amount of milk the machine has upon startup.
const val DEFAULT_COFFEE_BEANS = 120 // Default amount of coffee beans the machine has upon startup.
const val DEFAULT_DISPOSABLE_CUPS = 9 // Default amount of disposable cups the machine has upon startup.
const val DEFAULT_MONEY = 550 // Default amount of money the machine has upon startup.
const val GOOD_BYE = "Good-Bye!" // Upon exit of the program the machine will say good-byw!
const val COFFEE_MACHINE_HAS = "The coffee machine has:" // For the summary.
const val ML_OF_WATER = " ml of water" // used to print out how much water the machine has.
const val ML_OF_MILK = " ml of milk" // used to print out how much milk the machine has.
const val G_OF_COFFEE_BEANS = " g of coffee beans" // used to print out how much coffee beans the machine has.
const val AMOUNT_OF_DISPOSABLE_CUPS = " disposable cups" // used to print out how much disposable cups the machine has.
const val OF_MONEY = "$## of money" // used to print out how much money the machine has.
const val ACTION_START = 0 // To track the flow of the program
const val STEP_START = 0 // To track the flow of adding resources to the machine.
const val ACTION_QUESTION =
    "Write action (buy, fill, take, remaining, exit):" // Main question asking user what they would like to do.
const val DRINK_QUESTION =
    "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:" // Asks to choose the drink they want.
const val ADD_WATER_QUESTION = "Write how many ml of water you want to add:" // Question used to add an amount of water.
const val ADD_MILK_QUESTION = "Write how many ml of milk you want to add:" // Question used to add an amount of milk.
const val ADD_COFFEE_BEANS_QUESTION =
    "Write how many grams of coffee beans you want to add:" // Question used to add an amount of coffee beans.
const val ADD_DISPOSABLE_CUPS_QUESTION =
    "Write how many disposable cups you want to add:" // Question used to add an amount of disposable cups.
const val ENOUGH_RESOURCES =
    "I have enough resources, making you a coffee!" // Feedback given to user is machine has enough resources.
const val NOT_ENOUGH_WATER = "Sorry, not enough water!" // Feedback given to user when not enough water.
const val NOT_ENOUGH_MILK = "Sorry, not enough milk!" // Feedback given to user when not enough milk.
const val NOT_ENOUGH_COFFEE_BEANS =
    "Sorry, not enough coffee beans!" // Feedback given to user when not enough coffee beans.
const val NOT_ENOUGH_DISPOSABLE_CUPS =
    "Sorry, not enough disposable cups!" // Feedback given to user when not enough disposable cups.
const val TEMP_STRING: String = "##" // Some strings have this as a placeholder for actual values.
const val BACK = "back" // To go back to main menu from drink selection.
const val EXIT = "exit" // To exit program from main menu.
const val FILL = "fill" // To fill machine of resources.
const val BUY = "buy" // To buy a drink.
const val REMAINING = "remaining" // To print state of the machine.
const val TAKE = "take" // To withdraw the money from machine.
const val MONEY_GIVEN = "I gave you $##" // States the amount of money given.
const val STEP_ACTION_RESET = 0 // To reset step and action state to 0.
const val START_ACTION = 0 // Used for the main menu when statement.
const val BUY_ACTION = 1 // Used if the user selected to buy a drink.
const val FILL_ACTION = 2 // Used if the user selected to fill the machine.
const val ADD_WATER_STEP = 0 // Used to add water.
const val ADD_MILK_STEP = 1 // Used to add milk.
const val ADD_COFFEE_BEANS_STEP = 2 // Used to add coffee beans.
const val ADD_DISPOSABLE_CUPS_STEP = 3 // Used to add disposable cups.
const val ESPRESSO_SELECTION = 1 // Used to select drink.
const val LATTE_SELECTION = 2 // Used to select drink.
const val CAPPUCCINO_SELECTION = 3 // Used to select drink.
const val RESOURCE_MINIMUM = 0 // Used to check if there are enough resources.
/**
 * This coffee machine class simulates a coffee machine with
 * a certain amount of resources. The goal is to allow the user
 * a couple of different ways to interact with the machine.
 * The user can buy a coffee, refill the machines resources,
 * print the state of the machine, withdraw the money from the
 * machine, and exit the machine. The machine starts already
 * having a default number of water, milk, coffee beans,
 * disposable cups, and money.
 */
class CoffeeMachine(
    private var water: Int = DEFAULT_WATER,
    private var milk: Int = DEFAULT_MILK,
    private var coffeeBeans: Int = DEFAULT_COFFEE_BEANS,
    private var disposableCups: Int = DEFAULT_DISPOSABLE_CUPS,
    private var money: Int = DEFAULT_MONEY,
    private var action: Int = ACTION_START,
    private var step: Int = STEP_START
) {
    init {
        println(ACTION_QUESTION)
    }

    /**
     * Controls the entire flow of interacting with the coffee machine.
     * The entire machine is controlled through just one function.
     * First the user is asked what they would like to do either,
     * buy, refill machine, print state of machine, withdraw money, or exit
     * program. after that they are instructed to do it again, or do the next
     * step of the action they decided to go with.
     */
    fun console(input: String): Boolean {
        when (action) {
            START_ACTION -> {
                when (input) {
                    EXIT -> {
                        println(GOOD_BYE)
                        return true
                    }
                    REMAINING -> {
                        println()
                        printCoffeeMachineSummary()
                        println(ACTION_QUESTION)
                    }

                    TAKE -> {
                        println()
                        take()
                        println(ACTION_QUESTION)
                    }

                    BUY -> {
                        println()
                        println(DRINK_QUESTION)
                        action++
                    }

                    FILL -> {
                        println()
                        println(ADD_WATER_QUESTION)
                        action = FILL_ACTION
                    }
                }
            }

            BUY_ACTION -> {
                buy(input)
                println(ACTION_QUESTION)
                action--
            }

            FILL_ACTION -> {
                when (step) {
                    ADD_WATER_STEP -> {
                        addWater(input.toInt())
                        println(ADD_MILK_QUESTION)
                        step++
                    }

                    ADD_MILK_STEP -> {
                        addMilk(input.toInt())
                        println(ADD_COFFEE_BEANS_QUESTION)
                        step++
                    }

                    ADD_COFFEE_BEANS_STEP -> {
                        addCoffeeBeans(input.toInt())
                        println(ADD_DISPOSABLE_CUPS_QUESTION)
                        step++
                    }

                    ADD_DISPOSABLE_CUPS_STEP -> {
                        addDisposableCups(input.toInt())
                        println()
                        println(ACTION_QUESTION)
                        action = STEP_ACTION_RESET
                        step = STEP_ACTION_RESET
                    }
                }
            }
        }
        return false
    }

    /**
     * This function prints out the state of the coffee
     * machine including the amount of resources as well
     * as the amount of money available to withdraw.
     */
    private fun printCoffeeMachineSummary() {
        println(COFFEE_MACHINE_HAS)
        println(water.toString() + ML_OF_WATER)
        println(milk.toString() + ML_OF_MILK)
        println(coffeeBeans.toString() + G_OF_COFFEE_BEANS)
        println(disposableCups.toString() + AMOUNT_OF_DISPOSABLE_CUPS)
        println(OF_MONEY.replace(TEMP_STRING, money.toString()))
        println()
    }

    /**
     * This function is used to determine which drink the user
     * would like to buy. It also allows the user to go back to
     * main menu if so desired.
     */
    private fun buy(input: String) {
        if (input == BACK) {
            return
        }
        when (input.toInt()) {
            ESPRESSO_SELECTION -> buyDrink(CoffeeDrink.ESPRESSO)
            LATTE_SELECTION -> buyDrink(CoffeeDrink.LATTE)
            CAPPUCCINO_SELECTION -> buyDrink(CoffeeDrink.CAPPUCCINO)
        }
        println()
    }

    /**
     * This function prints the amount of money given to the
     * user for withdraw.
     */
    private fun take() {
        println(MONEY_GIVEN.replace(TEMP_STRING, money.toString()))
        takeMoney()
        println()
    }

    /**
     * Checks to see whether there is enough resources to make a drink
     * if so the drink gets made and the resources used to make the
     * drink get subtracted from the amount available.
     */
    private fun buyDrink(coffeeDrink: CoffeeDrink) {
        if (enoughResources(coffeeDrink)) {
            water -= coffeeDrink.water
            milk -= coffeeDrink.milk
            coffeeBeans -= coffeeDrink.coffeeBeans
            disposableCups--
            money += coffeeDrink.cost
        }
    }

    /**
     * The purpose of this function is tp check whether there is
     * enough of a resource to make a certain drink. If the
     * amount required is more than the machine has, it prints
     * out a statement saying what it needs.
     */
    private fun enoughResources(coffeeDrink: CoffeeDrink): Boolean {
        when {
            water - coffeeDrink.water < RESOURCE_MINIMUM -> {
                println(NOT_ENOUGH_WATER)
                return false
            }

            milk - coffeeDrink.milk < RESOURCE_MINIMUM -> {
                println(NOT_ENOUGH_MILK)
                return false
            }

            coffeeBeans - coffeeDrink.coffeeBeans < RESOURCE_MINIMUM -> {
                println(NOT_ENOUGH_COFFEE_BEANS)
                return false
            }

            disposableCups - 1 < RESOURCE_MINIMUM -> {
                println(NOT_ENOUGH_DISPOSABLE_CUPS)
                return false
            }

            else -> {
                println(ENOUGH_RESOURCES)
                return true
            }
        }
    }

    /**
     * The amount of water the user added gets passed to
     * the amount of water in the machine.
     */
    private fun addWater(addWater: Int) {
        water += addWater
    }

    /**
     * The amount of milk the user added gets passed to
     * the amount of milk in the machine.
     */
    private fun addMilk(addMilk: Int) {
        milk += addMilk
    }

    /**
     * The amount of coffee beans the user added gets passed to
     * the amount of coffee beans in the machine.
     */
    private fun addCoffeeBeans(addCoffeeBeans: Int) {
        coffeeBeans += addCoffeeBeans
    }

    /**
     * The amount of cups the user added gets passed the amount of
     * cups in the machine.
     */
    private fun addDisposableCups(addCups: Int) {
        disposableCups += addCups
    }

    /**
     * Takes the money from the coffee machine and sets the variable
     * money to 0
     */
    private fun takeMoney() {
        money -= this.money
    }
}
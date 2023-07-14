const val DEFAULT_WATER = 400
const val DEFAULT_MILK = 540
const val DEFAULT_COFFEE_BEANS = 120
const val DEFAULT_DISPOSABLE_CUPS = 9
const val DEFAULT_MONEY = 550
const val GOOD_BYE = "Good-Bye!"
const val COFFEE_MACHINE_HAS = "The coffee machine has:"
const val ML_OF_WATER = " ml of water"
const val ML_OF_MILK = " ml of milk"
const val G_OF_COFFEE_BEANS = " g of coffee beans"
const val AMOUNT_OF_DISPOSABLE_CUPS = " disposable cups"
const val OF_MONEY = "$## of money"
const val ACTION_START = 0
const val STEP_START = 0
const val ACTION_QUESTION = "Write action (buy, fill, take, remaining, exit):"
const val DRINK_QUESTION = "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:"
const val ADD_WATER_QUESTION = "Write how many ml of water you want to add:"
const val ADD_MILK_QUESTION = "Write how many ml of milk you want to add:"
const val ADD_COFFEE_BEANS_QUESTION = "Write how many grams of coffee beans you want to add:"
const val ADD_DISPOSABLE_CUPS_QUESTION = "Write how many disposable cups you want to add:"
const val ENOUGH_RESOURCES = "I have enough resources, making you a coffee!"
const val NOT_ENOUGH_WATER = "Sorry, not enough water!"
const val NOT_ENOUGH_MILK = "Sorry, not enough milk!"
const val NOT_ENOUGH_COFFEE_BEANS = "Sorry, not enough coffee beans!"
const val NOT_ENOUGH_DISPOSABLE_CUPS = "Sorry, not enough disposable cups!"
const val TEMP_STRING: String = "##"
const val BACK = "back"
const val EXIT = "exit"
const val FILL = "fill"
const val BUY = "buy"
const val REMAINING = "remaining"
const val TAKE = "take"
const val MONEY_GIVEN = "I gave you $##"

/**
 * This coffee machine class simulates a coffee machine with
 * a certain amount of resources. The goal is to allow the user
 * a couple of differant ways to interact with the machine.
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
            0 -> {
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
                        action = 2
                    }
                }
            }

            1 -> {
                buy(input)
                println(ACTION_QUESTION)
                action--
            }

            2 -> {
                when (step) {
                    0 -> {
                        addWater(input.toInt())
                        println(ADD_MILK_QUESTION)
                        step++
                    }

                    1 -> {
                        addMilk(input.toInt())
                        println(ADD_COFFEE_BEANS_QUESTION)
                        step++
                    }

                    2 -> {
                        addCoffeeBeans(input.toInt())
                        println(ADD_DISPOSABLE_CUPS_QUESTION)
                        step++
                    }

                    3 -> {
                        addDisposableCups(input.toInt())
                        println()
                        println(ACTION_QUESTION)
                        action = 0
                        step = 0
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
            1 -> buyDrink(CoffeeDrink.ESPRESSO)
            2 -> buyDrink(CoffeeDrink.LATTE)
            3 -> buyDrink(CoffeeDrink.CAPPUCCINO)
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
     * amount required is more than the machine has it prints
     * out a statement saying what it needs.
     */
    private fun enoughResources(coffeeDrink: CoffeeDrink): Boolean {
        when {
            water - coffeeDrink.water < 0 -> {
                println(NOT_ENOUGH_WATER)
                return false
            }

            milk - coffeeDrink.milk < 0 -> {
                println(NOT_ENOUGH_MILK)
                return false
            }

            coffeeBeans - coffeeDrink.coffeeBeans < 0 -> {
                println(NOT_ENOUGH_COFFEE_BEANS)
                return false
            }

            disposableCups - 1 < 0 -> {
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
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

    private fun printCoffeeMachineSummary() {
        println(COFFEE_MACHINE_HAS)
        println(water.toString() + ML_OF_WATER)
        println(milk.toString() + ML_OF_MILK)
        println(coffeeBeans.toString() + G_OF_COFFEE_BEANS)
        println(disposableCups.toString() + AMOUNT_OF_DISPOSABLE_CUPS)
        println(OF_MONEY.replace(TEMP_STRING, money.toString()))
        println()
    }

    private fun buy(input: String) {
        if (input == BACK) {
            return
        }
        when (input.toInt()) {
            1 -> buyEspresso()
            2 -> buyLatte()
            3 -> buyCappuccino()
        }
        println()
    }

    private fun take() {
        println(MONEY_GIVEN.replace(TEMP_STRING, money.toString()))
        takeMoney()
        println()
    }

    private fun buyEspresso() {
        if (enoughResources(CoffeeDrink.ESPRESSO)) {
            water -= CoffeeDrink.ESPRESSO.water
            coffeeBeans -= CoffeeDrink.ESPRESSO.coffeeBeans
            disposableCups--
            money += CoffeeDrink.ESPRESSO.cost
        }
    }

    private fun buyLatte() {
        if (enoughResources(CoffeeDrink.LATTE)) {
            water -= CoffeeDrink.LATTE.water
            milk -= CoffeeDrink.LATTE.milk
            coffeeBeans -= CoffeeDrink.LATTE.coffeeBeans
            disposableCups--
            money += CoffeeDrink.LATTE.cost
        }
    }

    private fun buyCappuccino() {
        if (enoughResources(CoffeeDrink.CAPPUCCINO)) {
            water -= CoffeeDrink.CAPPUCCINO.water
            milk -= CoffeeDrink.CAPPUCCINO.milk
            coffeeBeans -= CoffeeDrink.CAPPUCCINO.coffeeBeans
            disposableCups--
            money += CoffeeDrink.CAPPUCCINO.cost
        }
    }

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

    private fun addWater(addWater: Int) {
        water += addWater
    }

    private fun addMilk(addMilk: Int) {
        milk += addMilk
    }

    private fun addCoffeeBeans(addCoffeeBeans: Int) {
        coffeeBeans += addCoffeeBeans
    }

    private fun addDisposableCups(addCups: Int) {
        disposableCups += addCups
    }

    private fun takeMoney() {
        money -= this.money
    }
}
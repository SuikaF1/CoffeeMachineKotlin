

const val DEFAULT_WATER: Int = 400
const val DEFAULT_MILK: Int = 540
const val DEFAULT_COFFEE_BEANS: Int = 120
const val DEFAULT_DISPOSABLE_CUPS: Int = 9
const val DEFAULT_MONEY: Int = 550
const val COFFEE_MACHINE_HAS: String = "The coffee machine has:"
const val ML_OF_WATER: String = " ml of water"
const val ML_OF_MILK: String = " ml of milk"
const val G_OF_COFFEE_BEANS: String = " g of coffee beans"
const val AMOUNT_OF_DISPOSABLE_CUPS: String = " disposable cups"
const val OF_MONEY: String = "$## of money"
const val ACTION_START = 0
const val STEP_START = 0

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
        println("Write action (buy, fill, take, remaining, exit):")
    }

    fun console(input: String): Boolean {
        when (action) {
            0 -> {
                when (input) {
                    "exit" -> return true
                    "remaining" -> {
                        println()
                        printCoffeeMachineSummary()
                        println("Write action (buy, fill, take, remaining, exit):")
                    }

                    "take" -> {
                        println()
                        take()
                        println("Write action (buy, fill, take, remaining, exit):")
                    }

                    "buy" -> {
                        println()
                        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:")
                        action++
                    }

                    "fill" -> {
                        println()
                        println("Write how many ml of water you want to add:")
                        action = 2
                    }
                }
            }

            1 -> {
                buy(input)
                println("Write action (buy, fill, take, remaining, exit):")
                action--
            }

            2 -> {
                when (step) {
                    0 -> {
                        addWater(input.toInt())
                        println("Write how many ml of milk you want to add:")
                        step++
                    }

                    1 -> {
                        addMilk(input.toInt())
                        println("Write how many grams of coffee beans you want to add:")
                        step++
                    }

                    2 -> {
                        addCoffeeBeans(input.toInt())
                        println("Write how many disposable cups you want to add:")
                        step++
                    }

                    3 -> {
                        addDisposableCups(input.toInt())
                        println()
                        println("Write action (buy, fill, take, remaining, exit):")
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
        println(OF_MONEY.replace("##", money.toString()))
        println()
    }

    private fun buy(input: String) {
        if (input == "back") {
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
        println("I gave you $$money")
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
                println("Sorry, not enough water!")
                return false
            }

            milk - coffeeDrink.milk < 0 -> {
                println("Sorry, not enough milk!")
                return false
            }

            coffeeBeans - coffeeDrink.coffeeBeans < 0 -> {
                println("Sorry, not enough coffee beans!")
                return false
            }

            disposableCups - 1 < 0 -> {
                println("Sorry, not enough disposable cups!")
                return false
            }

            else -> {
                println("I have enough resources, making you a coffee!")
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
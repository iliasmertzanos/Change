class ChangeCalculator(private val coins: List<Int>) {
	
    fun computeMostEfficientChange(amount: Int): List<Int> {
        require(amount >= 0) { "Negative totals are not allowed." }

        return when (amount) {
            0 -> emptyList()
            else ->
                requireNotNull(coins
                    .filter { it <= amount }
                    .reversed()
                    .fold(MutableList<List<Int>?>(amount + 1) { null }) { change, coin ->
                        if (change[0] == null) change[0] = emptyList()
                        (coin..amount).forEach {
                            if (change[it - coin] != null) {
                                if (change[it] == null || change[it]!!.size > change[it - coin]!!.size + 1) {
                                    change[it] = listOf(coin) + change[it - coin]!!
                                }
                            }
                        }
                        change
                    }.last()
                ) { "The total $amount cannot be represented in the given currency." }
        }
    }
	
    fun computeMostEfficientChange2(amount: Int): List<Int> {
        require(amount >= 0) { "Negative totals are not allowed." }

        return requireNotNull((1..amount).fold(listOf<List<Int>?>(listOf())) { acc, idx ->
            coins
                .filter { acc.getOrNull(idx - it) != null }
                .map { listOf(it) + acc[idx - it]!! }
                .sortedBy { it.size }
                .firstOrNull()
                .let { acc.plusElement(it) }
        }.lastOrNull()) { "The total $amount cannot be represented in the given currency." }
    }

}
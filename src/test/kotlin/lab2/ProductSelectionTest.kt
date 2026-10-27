package lab2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductSelectionTest {
    @Test
    fun selectsAvailableProductsInRecommendationOrder() {
        val keyboard = Product(1, "Keyboard", true)
        val mouse = Product(2, "Mouse", false)
        val monitor = Product(3, "Monitor", true)
        val webcam = Product(4, "Webcam", true)
        val products = listOf(keyboard, mouse, monitor, webcam)

        val result = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L),
            listOf(1L)
        )

        assertEquals(listOf(monitor, webcam), result)
    }

    @Test
    fun returnsEmptyListWhenThereAreNoRecommendations() {
        val products = listOf(Product(1, "Keyboard", true))

        val result = selectProducts(products, emptyList(), emptyList())

        assertEquals(emptyList<Product>(), result)
    }
    @Test
    fun preservesRecommendationOrderRegardlessOfCatalogOrder() {
        val first = Product(1, "Keyboard", true)
        val second = Product(2, "Mouse", true)
        val third = Product(3, "Monitor", true)

        val result = selectProducts(
            listOf(second, third, first),
            listOf(3L, 1L, 2L),
            emptyList()
        )

        assertEquals(listOf(third, first, second), result)
    }

    @Test
    fun handlesDuplicateRecommendationsAndExclusions() {
        val first = Product(1, "Keyboard", true)
        val second = Product(2, "Mouse", true)

        val result = selectProducts(
            listOf(first, second),
            listOf(1L, 1L, 2L, 2L, 1L),
            listOf(2L, 2L, 99L)
        )

        assertEquals(listOf(first), result)
    }

    @Test
    fun returnsEmptyListWhenNoProductsMatch() {
        val unavailable = Product(1, "Keyboard", false)
        val excluded = Product(2, "Mouse", true)

        assertEquals(
            emptyList<Product>(),
            selectProducts(
                listOf(unavailable, excluded),
                listOf(1L, 2L, 99L),
                listOf(2L)
            )
        )

        assertEquals(
            emptyList<Product>(),
            selectProducts(
                emptyList(),
                listOf(1L, 2L),
                emptyList()
            )
        )
    }

    @Test
    fun doesNotModifyInputsAndRepeatedCallsAreIndependent() {
        val first = Product(1, "Keyboard", true)
        val second = Product(2, "Mouse", true)

        val products = mutableListOf(first, second)
        val recommendations = mutableListOf(2L, 1L, 2L)
        val exclusions = mutableListOf(1L)

        val firstResult = selectProducts(products, recommendations, exclusions)
        val secondResult = selectProducts(products, recommendations, emptyList())
        val thirdResult = selectProducts(products, recommendations, exclusions)

        assertEquals(listOf(second), firstResult)
        assertEquals(listOf(second, first), secondResult)
        assertEquals(listOf(second), thirdResult)

        assertEquals(listOf(first, second), products)
        assertEquals(listOf(2L, 1L, 2L), recommendations)
        assertEquals(listOf(1L), exclusions)
    }
}

package lab2

fun selectProducts(
    products: List<Product>,
    recommendedIds: List<Long>,
    excludedIds: List<Long>
): List<Product> {

    val productsById = products.associateBy { it.id }
    val excludedSet = excludedIds.toHashSet()
    val seenIds = mutableSetOf<Long>()
    val result = mutableListOf<Product>()

    for (id in recommendedIds) {
        val product = productsById[id]

        if (product != null && product.available) {
            if (id  !in excludedSet){
                if (seenIds.add(id)) {
                    result.add(product)
                }

            }

        }
    }

    return result
}

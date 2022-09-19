package com.github.hugovallada.starwars

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeExactly
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CalculatorJUnitTest {

    @Test
    fun `given a = 2 and b = 2, when sum is called then the response should be 4`() {
        val response = Calculator().sum(2,2)
        Assertions.assertThat(response).isEqualTo(4)
    }
}


class CalculatorTest: BehaviorSpec({
    given("a = 2  and b = 2") {
        `when`("Calculator.sum is called") {
            val response = Calculator().sum(2, 2)
            then("response should be 4") {
                response shouldBeExactly 4
            }
        }
    }
})
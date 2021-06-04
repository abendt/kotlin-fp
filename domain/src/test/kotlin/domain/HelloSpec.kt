package domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class HelloSpec : StringSpec({
    "hello" {
        checkAll(Arb.string()) { name: String ->
            val greeting = sayHello(name)

            assert(greeting.startsWith("Hello"))
            assert(greeting.endsWith(name))
        }
    }
})

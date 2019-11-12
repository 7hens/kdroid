package cn.thens.kdroid.core.codec.testbean

data class Person(
        val firstName: String,
        val lastName: String,
        val age: Int
) {
    fun equals(other: Person): Boolean {
        return firstName == other.firstName && lastName == other.lastName && age == other.age
    }

    companion object {
        const val MOCK_JSON = """{ "firstName": "James", "lastName": "Green", "age": 12 }"""

        val instance by lazy { Person("James", "Green", 12) }
    }
}
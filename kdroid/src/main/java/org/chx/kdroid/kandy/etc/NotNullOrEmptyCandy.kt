package org.chx.kdroid.kandy.etc

fun <T : Collection<*>> T?.notNull(func: (T) -> Unit = {}): Boolean {
    if (this != null) {
        func(this)
        return true
    }
    return false
}

fun <T : Collection<*>> T?.notNullOrEmpty(func: (T) -> Unit = {}): Boolean {
    if (this != null && !this.isEmpty()) {
        func(this)
        return true
    }
    return false
}

fun <T : CharSequence> T?.notNull(func: (T) -> Unit = {}): Boolean {
    if (this != null) {
        func(this)
        return true
    }
    return false
}

fun <T : CharSequence> T?.notNullOrEmpty(func: (T) -> Unit = {}): Boolean {
    if (this != null && !this.isEmpty()) {
        func(this)
        return true
    }
    return false
}
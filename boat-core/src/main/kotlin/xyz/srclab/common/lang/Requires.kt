@file:JvmName("Requires")

package xyz.srclab.common.lang

@Throws(NullPointerException::class)
fun <T : Any> T?.notNull(): T {
    checkNull(this !== null)
    return this.asNotNull()
}

@Throws(NullPointerException::class)
fun <T : Any> T?.notNull(message: String?): T {
    checkNull(this !== null, message)
    return this.asNotNull()
}

@Throws(NullPointerException::class)
fun <T : Any> T?.notNull(messagePattern: String?, vararg messageArgs: Any?): T {
    checkNull(this !== null, messagePattern, *messageArgs)
    return this.asNotNull()
}

@Throws(NoSuchElementException::class)
fun <T : Any> T?.notNullElement(): T {
    checkElement(this !== null)
    return this.asNotNull()
}

@Throws(NoSuchElementException::class)
fun <T : Any> T?.notNullElement(message: String?): T {
    checkElement(this !== null, message)
    return this.asNotNull()
}

@Throws(NoSuchElementException::class)
fun <T : Any> T?.notNullElement(messagePattern: String?, vararg messageArgs: Any?): T {
    checkElement(this !== null, messagePattern, *messageArgs)
    return this.asNotNull()
}

@Throws(NoSuchElementException::class)
fun <T : Any> T?.notNullElementByKey(key: Any?): T {
    return notNullElement("Key: {}.", key)
}
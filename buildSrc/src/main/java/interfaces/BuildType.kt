package interfaces

interface BuildType {
    val name: Type
    val isMinifyEnabled: Boolean
    val isDebuggable: Boolean
    val isShrinkResources: Boolean
    val isJniDebuggable: Boolean

    enum class Type(val value: String) {
        RELEASE("release"),
        DEBUG("debug");
    }
}
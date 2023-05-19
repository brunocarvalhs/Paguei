package flavor

import interfaces.BuildType

object BuildTypeRelease : BuildType {
    override val name: BuildType.Type
        get() = BuildType.Type.RELEASE
    override val isMinifyEnabled = false
    override val isDebuggable = false
    override val isShrinkResources = true
    override val isJniDebuggable = false
}
package flavor

import interfaces.BuildType

object BuildTypeDebug : BuildType {
    override val name: BuildType.Type
        get() = BuildType.Type.DEBUG
    override val isMinifyEnabled = false
    override val isDebuggable = true
    override val isShrinkResources = true
    override val isJniDebuggable = true
}
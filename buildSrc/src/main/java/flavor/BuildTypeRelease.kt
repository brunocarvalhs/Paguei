package flavor

import interfaces.BuildType

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
    override val isDebuggable = false
    override val isShrinkResources = true
}
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }


}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }


}

rootProject.name = "BlueNiva2"
include(":app")
include(":bt_def")

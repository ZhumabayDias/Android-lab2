import java.util.Properties

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
val localProperties = Properties().apply {
    load(File(rootProject.projectDir, "local.properties").inputStream())
}

val mavenUsername: String = localProperties["mavenUsername"] as String
val mavenPassword: String = localProperties["mavenPassword"] as String

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/ZhumabayDias/Andoird-lab2")
            credentials {
                username = mavenUsername
                password = mavenPassword
            }
        }
    }
}

rootProject.name = "module"
include(":app")
include(":chatlibrary")

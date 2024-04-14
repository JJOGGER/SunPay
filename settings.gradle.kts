import java.net.URI

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
        maven {
            isAllowInsecureProtocol= true
            url = URI("http://maven.aliyun.com/nexus/content/groups/public/")
        }
        maven {
            isAllowInsecureProtocol = true
            url = URI("http://maven.aliyun.com/nexus/content/repositories/jcenter")
        }
        maven {
            isAllowInsecureProtocol = true
            url = URI("http://maven.aliyun.com/nexus/content/repositories/google")
        }
        maven {
            isAllowInsecureProtocol = true
            url = URI("http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        }
        google()
        mavenCentral()

        maven { url = URI("https://jitpack.io")  }
    }
}

rootProject.name = "SunPay"
include(":app")
 
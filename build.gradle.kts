plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false

    alias(libs.plugins.detekt)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.kover)
}

val exclusions = project.properties["testExclusions"].toString()
val buildDir: String = project.layout.buildDirectory.get().asFile.path

dependencies {
    detektPlugins(libs.detekt.ktlint)
}

detekt {
    config.from(files("$rootDir/detekt-config.yml"))
    source.from("shared", "native", "server", "composeApp")
    reportsDir = file("$buildDir/reports/detekt")
}

configurations.matching { it.name == "detekt" }.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
        }
    }
}

kover {
    useJacoco()
    reports {
        verify {
            rule {
                minBound(0)
            }
        }
        total {
            filters {
                excludes {
                    classes(
                        exclusions
                            .replace("/", ".")
                            .split(",")
                    )
                }
            }

            xml {
                onCheck = true
            }
            html {
                onCheck = true
            }
        }
    }
}

sonar {
    properties {
        property("sonar.qualitygate.wait", "true")
        property("sonar.gradle.skipCompile", "true")
        property("sonar.core.codeCoveragePlugin", "jacoco")
        property("sonar.kotlin.detekt.reportPaths", "$buildDir/reports/detekt/detekt.xml")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/kover/report.xml")
        property("sonar.cpd.exclusions", exclusions)
        property("sonar.jacoco.excludes", exclusions)
        property("sonar.coverage.exclusions", exclusions)
    }
}
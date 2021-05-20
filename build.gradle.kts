import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    war
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
}

group = "ru.ifmo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Exclude Tomcat
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    providedRuntime("org.springframework.boot:spring-boot-starter-logging")

    implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.1")
    implementation("org.javassist:javassist:3.25.0-GA")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    testCompileOnly("org.projectlombok:lombok:1.18.20")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.20")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

configurations.all {
    exclude(module = "spring-boot-starter-logging")
    exclude(module = "logback-classic")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

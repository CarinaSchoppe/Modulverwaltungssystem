
plugins{
    id("java")
    id("com.github.johnrengelman.shadow") version "+"
    id("org.openjfx.javafxplugin") version "+"
    id("application")
    id("idea")
}

dependencies{
    implementation("org.projectlombok:lombok:+")
    annotationProcessor("org.projectlombok:lombok:+")
    testCompileOnly("org.projectlombok:lombok:+")
    testAnnotationProcessor("org.projectlombok:lombok:+")
    implementation("mysql:mysql-connector-java:+")
    implementation("com.jfoenix:jfoenix:9.0.10")
    implementation("com.google.code.gson:gson:+")
    implementation("org.apache.pdfbox:pdfbox:2.0.24")
    implementation("org.jsoup:jsoup:+")
}

repositories{
    mavenCentral()
    gradlePluginPortal()
}

group = "de.lisa"
version = "1.0.0"
description = "Studiumsorganisation"

tasks {
compileJava {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    options.release.set(17)
}
javadoc {
    options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
}
processResources {
    filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
}
}
    
javafx{
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

application {
    mainClass.set("de.lisa.studiumsorganisation.Main")
}

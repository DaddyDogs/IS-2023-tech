plugins {
    id("java")
}

group = "ru.ermolaayyyyyyy"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":banks"))
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
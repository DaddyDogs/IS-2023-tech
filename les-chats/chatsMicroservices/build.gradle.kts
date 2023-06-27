plugins {
    id("java-library")
}

group = "ru.ermolaayyyyyyy"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    api(project(":les-chats:base"))
    api("org.springframework.boot:spring-boot:3.0.5")
    api("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
    api("org.springframework.boot:spring-boot-starter-validation:3.0.5")
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    implementation("org.springframework.amqp:spring-rabbit:3.0.4")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.1")
    testImplementation("org.hsqldb:hsqldb:2.7.1")
    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    testImplementation("org.testcontainers:rabbitmq:1.18.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
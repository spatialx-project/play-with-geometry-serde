plugins {
    id("java")
    id("me.champeau.jmh") version "0.6.8"
}

group = "com.spatialx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.spark:spark-sql_2.12:3.3.0")
    implementation("org.locationtech.jts:jts-core:1.19.0")
    implementation("org.locationtech.jts.io:jts-io-common:1.19.0")
    implementation("org.geotools:gt-shapefile:24.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

jmh {
    zip64.set(true)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

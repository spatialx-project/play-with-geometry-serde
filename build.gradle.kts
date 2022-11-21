plugins {
    id("java")
    id("me.champeau.jmh") version "0.6.8"
    id("com.diffplug.spotless") version "6.12.0"
}

group = "com.spatialx"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.sedona:sedona-core-3.0_2.12:1.2.1-incubating")
    implementation("com.esotericsoftware:kryo-shaded:4.0.2")
    implementation("org.locationtech.jts:jts-core:1.19.0")
    implementation("org.locationtech.jts.io:jts-io-common:1.19.0")
    implementation("org.geotools:gt-shapefile:24.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

jmh {
    zip64.set(true)
}

spotless {
    java {
        googleJavaFormat("1.7")
        removeUnusedImports()
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

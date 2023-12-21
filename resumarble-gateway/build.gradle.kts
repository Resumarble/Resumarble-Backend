tasks.getByName<Jar>("bootJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

extra["springCloudVersion"] = "2022.0.4"

dependencies {

    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    implementation("io.projectreactor.tools:blockhound:1.0.8.RELEASE")

    developmentOnly("io.netty:netty-resolver-dns-native-macos:4.1.75.Final") {
        artifact { classifier = "osx-aarch_64" }
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

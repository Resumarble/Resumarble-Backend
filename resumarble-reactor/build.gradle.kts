tasks.getByName<Jar>("bootJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

extra["springCloudVersion"] = "2022.0.4"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.github.openfeign:feign-jackson:13.1")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("io.asyncer:r2dbc-mysql:1.0.4")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.4")

    developmentOnly("io.netty:netty-resolver-dns-native-macos:4.1.75.Final") {
        artifact { classifier = "osx-aarch_64" }
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

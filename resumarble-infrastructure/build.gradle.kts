val jar: Jar by tasks
val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    // add spring data jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.0.0")

    runtimeOnly("com.mysql:mysql-connector-j")

    implementation(project(":resumarble-core"))
}

plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
}

group = "com.stock"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.1.2")
	implementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.apache.commons:commons-math3:3.6.1")
	implementation("com.h2database:h2")
	implementation("org.springframework.data:spring-data-r2dbc:3.2.1")

	// Uncomment if using MongoDB and Spring Security
	// implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	// implementation("org.springframework.boot:spring-boot-starter-security")

	// Uncomment if using MySQL
	// runtimeOnly("mysql:mysql-connector-java")

	// Uncomment for Thymeleaf + Spring Security integration
	// implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

	// Lombok Dependencies
	annotationProcessor("org.projectlombok:lombok")
	compileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
	testCompileOnly("org.projectlombok:lombok")

	// Testing Dependencies
	testImplementation("org.projectlombok:lombok:1.18.26")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.2")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("io.projectreactor:reactor-test")
}


tasks.withType<Test> {
	useJUnitPlatform()
}

package com.unq.viendasya.archTest


import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.junit.Test
import javax.persistence.Entity


class ArchTest {
    @Test
    fun some_architecture_rule() {
         val importedClasses: JavaClasses = ClassFileImporter()
                 .withImportOption(ImportOption.Predefined.DONT_INCLUDE_TESTS)
                 .importPackages("com.unq")

        val  rule : ArchRule = classes().that().resideInAPackage("..service..")

                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..")

        rule.check(importedClasses)
    }

    @Test
    fun repositoryClassesShouldHaveSpringRepositoryAnnotation() {
        val classes = ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DONT_INCLUDE_TESTS)
                .importPackages("example")
        classes()
                .that().resideInAPackage("com.unq.viendasya.model")
                .should().beAnnotatedWith(Entity::class.java)
                .check(classes)
    }

}

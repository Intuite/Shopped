package com.intuite.shopped;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.intuite.shopped");

        noClasses()
            .that()
                .resideInAnyPackage("com.intuite.shopped.service..")
            .or()
                .resideInAnyPackage("com.intuite.shopped.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.intuite.shopped.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}

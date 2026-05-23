package westmeijer.oskar.weatherapi.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
    packages = "westmeijer.oskar.weatherapi",
    importOptions = ImportOption.DoNotIncludeTests.class
)

public class ArchUnitHexagonalTest {


  static class Layers {

    static final String DOMAIN = "Domain";
    static final String APPLICATION = "Application";
    static final String INFRASTRUCTURE = "Infrastructure";
  }

  static class Packages {

    static final String DOMAIN = "..domain..";
    static final String APPLICATION = "..application..";
    static final String INFRASTRUCTURE = "..infrastructure..";
  }

  @ArchTest
  static final ArchRule access_to_layers =
      layeredArchitecture()
          .consideringAllDependencies()

          .layer(Layers.DOMAIN)
          .definedBy(Packages.DOMAIN)

          .layer(Layers.APPLICATION)
          .definedBy(Packages.APPLICATION)

          .layer(Layers.INFRASTRUCTURE)
          .definedBy(Packages.INFRASTRUCTURE)

          .whereLayer(Layers.DOMAIN)
          .mayOnlyBeAccessedByLayers(
              Layers.APPLICATION,
              Layers.INFRASTRUCTURE
          )

          .whereLayer(Layers.APPLICATION)
          .mayOnlyBeAccessedByLayers(
              Layers.INFRASTRUCTURE
          )

          .whereLayer(Layers.INFRASTRUCTURE)
          .mayNotBeAccessedByAnyLayer();

  @ArchTest
  static final ArchRule domain_should_not_depend_on_other_layers =
      noClasses()
          .that()
          .resideInAPackage(Packages.DOMAIN)
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(
              Packages.APPLICATION,
              Packages.INFRASTRUCTURE
          );

  @ArchTest
  static final ArchRule application_should_only_depend_on_domain =
      noClasses()
          .that()
          .resideInAPackage(Packages.APPLICATION)
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(
              Packages.INFRASTRUCTURE
          );

  @ArchTest
  static final ArchRule infrastructure_should_only_access_application_ports =
      noClasses()
          .that()
          .resideInAPackage(Packages.INFRASTRUCTURE)
          .should()
          .dependOnClassesThat()
          .resideInAnyPackage(
              "..application.services.."
          );

  @ArchTest
  static final ArchRule application_ports_should_only_contain_interfaces =
      classes()
          .that()
          .resideInAPackage("..application.ports..")
          .should()
          .beInterfaces();

}

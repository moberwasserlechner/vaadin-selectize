# Vaadin Selectize.js [![Travis](https://img.shields.io/travis/moberwasserlechner/vaadin-selectize/master.svg?maxAge=2592000)](https://travis-ci.org/moberwasserlechner/vaadin-selectize) [![Bintray](https://img.shields.io/bintray/v/moberwasserlechner/maven/vaadin-selectize.svg)](https://bintray.com/moberwasserlechner/maven/vaadin-selectize/_latestVersion) [![License](https://img.shields.io/badge/license-Apache_2.0-B34ED4.svg)](https://github.com/moberwasserlechner/vaadin-selectize/blob/master/LICENSE) [![PayPal](https://img.shields.io/badge/%24-donate-0CB3EB.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=N8VS2P9233NJQ)

Vaadin 7 wrapper for the Selectize.js jquery plugin. https://github.com/selectize/selectize.js

## Features

* Easy fluent api
* Annotations to figure out value, label, sorting and search properties
 * @SelectizeOptionLabel
 * @SelectizeOptionSearch
 * @SelectizeOptionSort
 * @SelectizeOptionValue
* Typed fields for both multi and single select
 * SelectizeDropDownField ... Single select
 * SelectizeTokenField ... Multi select, Token field
* Both fields are fitting nicely into Valo themed form layout with light styles
* Readonly supported
* Buildin themes

## Demo

### Vaadin Addon

* http://vaadin-demos.qqjtxeeuih.eu-central-1.elasticbeanstalk.com:5500

If you want to run the demo application locally, see the [Contribution Section](#run-the-demo-local)

### selectize.js

* http://selectize.github.io/selectize.js/
* https://github.com/selectize/selectize.js/releases/latest

## Installation

### Download

[![Bintray](https://img.shields.io/bintray/v/moberwasserlechner/maven/vaadin-selectize.svg)](https://bintray.com/moberwasserlechner/maven/vaadin-selectize/_latestVersion)

### Vaadin Directory

Get the addon from 
https://vaadin.com/directory#!addon/selectize-add-on.

You can download the addon there as well, but you will need to create a free vaadin account first. For Maven style dependencies please use below settings.

### Maven

Repository

    <repositories>
      <repository>
        <snapshots>
          <enabled>false</enabled>
        </snapshots>
        <id>central</id>
        <name>bintray</name>
        <url>http://jcenter.bintray.com</url>
      </repository>
    </repositories>
    
Dependency

    <dependencies>
      <dependency>
        <groupId>com.byteowls</groupId>
        <artifactId>vaadin-selectize</artifactId>
        <version>0.2.0</version>
      </dependency>
    </dependencies>


### Gradle

Repository

    repositories {
      jcenter()
    }
     
Dependency

    dependencies {
      compile ("com.byteowls:vaadin-selectize:0.2.0")
    }
## Usage

For more examples please see the [demo app](#vaadin-addon)

```

    public void buildField() {
        SelectizeTokenField<PersonEntity> tokenField = new SelectizeTokenField<>(PersonEntity.class, "Tokens");
        tokenField.config()
            .plugins(Plugin.REMOVE_BUTTON)
            .placeholder("Choose multiple items")
            .optionLabelGenerator(c -> { return c.getFirstname() + " (" + c.getEmail() + ")"; })
            .options(getRandomOptions(10));
    }

    public abstract class AbstractIdEntity {

        @SelectizeOptionValue
        private String id;

        public AbstractIdEntity(String id) {
            super();
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public class PersonEntity extends AbstractIdEntity {
        @SelectizeOptionSearch
        @SelectizeOptionSort(order = 2)
        private String firstname;
        @SelectizeOptionSort(asc = false, order = 1)
        private String lastname;
        @SelectizeOptionLabel
        private String email;

        public PersonEntity(String id, String firstname, String lastname, String email) {
            super(id);
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
        }
        public String getFirstname() {
            return firstname;
        }
        public String getLastname() {
            return lastname;
        }
        public String getEmail() {
            return email;
        }
        @Override
        public String toString() {
            return "PersonEntity [firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
        }
    }

```

### Options

You will see that every fluent api method under `selectize.config()` has a counterpart in the javascript json config. The javadoc is basically the description of those options.

## Prerequisite

### Addon
* JDK 8
* Vaadin 7.4 or higher

### Demo
* JDK 8
* Vaadin 7.7.+


## Missing something?

This addon is only a wrapper. So if you have any feature requests or found any bugs in the javascript lib please use Selectize.js's issue tracker https://github.com/selectize/selectize.js/issues

In all other cases please create a issue at https://github.com/moberwasserlechner/vaadin-selectize/issues or contribute to the project yourself. For contribution see the next section.

## Contribute

### Setup Eclipse

1. Fork repo
2. Open command line
3. Clone your fork `git@github.com:USERNAME/vaadin-selectize.git`
4. `cd vaadin-selectize`
5. Build eclipse meta data `./gradlew cleanEclipse eclipse`
6. Open Eclipse
7. File -> Import... -> General -> Existing Projects into Workspace
8. Browse to your git repository
9. Check the option "Search for nested projects"
10. Check all 3 projects
11. Press finish

This should take not more than 1-2 minutes. You does not need to use any gradle eclipse plugins. 

### Fix a bug or create a new feature

Please do not mix more than one issue in a feature branch. Each feature/bugfix should have its own branch and its own Pull Request (PR).

1. Create a issue and describe what you want to do at [Issue Tracker](https://github.com/moberwasserlechner/vaadin-selectize/issues)
2. Create your feature branch (`git checkout -b feature/my-feature` or `git checkout -b bugfix/my-bugfix`)
3. Test your changes to the best of your ability.
4. Add a demo view to the demo application 
5. Commit your changes (`git commit -m 'Describe feature or bug'`)
6. Push to the branch (`git push origin feature/my-feature`)
7. Create a Github Pull Request

### Run the demo local

The demo application is based on Spring Boot. So its possible to run the Demo as Java Application right out of Eclipse, there is not servlet container needed as Spring Boot has a embedded Tomcat 8 included.

1. Open "Debug Configurations..." dialog
2. Create a new "Java Application"
3. Choose the "vaadin-selectize-demo" project
4. Use "com.byteowls.vaadin.selectize.demo.AddonDemoApplication" as Main class
5. Set `-Dprofile=dev` as VM argument. This ensures that source code panel in the demo is correctly filled while developing.
6. Browse to `http://localhost:8080/`

### Code Style

Please use the sun coding convention. Please do not use tabs at all!

## License

Apache-2.0. Please see [LICENSE](https://github.com/moberwasserlechner/vaadin-selectize/blob/master/LICENSE).

## Change Log

Please see [Releases](https://github.com/moberwasserlechner/vaadin-selectize/releases).
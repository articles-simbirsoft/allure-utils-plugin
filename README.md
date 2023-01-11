# Allure-utils-plugin

###### this repository was created for an article

* [Allure-utils-plugin](#allure-utils-plugin)
    * [allure-utils-plugin is a plugin for maven, witch can](#allure-utils-plugin-is-a-plugin-for-maven-witch-can)
  * [Get Started](#get-started)
  * [Mojos](#mojos)
    * [prepare-history](#prepare-history)
      * [parameters](#parameters)
      * [algorithm](#algorithm)
    * [remove-old](#remove-old)
      * [parameters](#parameters-1)
      * [algorithm](#algorithm-1)

### allure-utils-plugin is a plugin for maven, witch can

* manage history folder for allure report generation
* manage allure reports (keep multiple instances and manage link in them)

## Get Started

1. download allure-utils-plugin folder from this repository
2. add it to dependencies of your project
    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>com.simbirsoft</groupId>
                <artifactId>allure-utils-plugin</artifactId>
                <version>1.0</version>
            </plugin>
        </plugins>
    </build>
    ```
3. change allure-maven-plugin report path
   ```xml
   <reporting>
        <excludeDefaults>true</excludeDefaults>
        <plugins>
            <plugin>
                <groupId>io.qameta.allure</groupId>
                <artifactId>allure-maven</artifactId>
                <version>2.10.0</version>
                <configuration>
                    <reportDirectory>${project.reporting.outputDirectory}/results/last</reportDirectory>
                </configuration>
            </plugin>
        </plugins>
    </reporting>
    ```
4. install it before using
   ```shell
      mvn -f .\allure-utils-plugin clean install
    ```
5. run your tests
   ```shell
    mvn test
    ```
6. prepare history
    ```shell
    mvn allure-utils:prepare-history
    ```
7. generate report
    ```shell
    mvn site
    ```
8. remove old reports
    ```shell
    mvn allure-utils:remove-old
    ```
one line:
```shell
mvn test allure-utils:prepare-history site allure-utils:remove-old
```
## Mojos

* [prepare-history](#prepare-history)
* [remove-old](#remove-old)

### prepare-history

#### parameters

| parameter        | property name                         | default value                                | description                                                   |
|------------------|---------------------------------------|----------------------------------------------|---------------------------------------------------------------|
| resultsDirectory | allure-utils-plugin.results.directory | target/allure-results                        | path to allure-results                                        |
| baseUrl          | allure-utils-plugin.report.baseurl    | ..                                           | start of the link to previous report                          |
| outputDirectory  | allure-utils-plugin.report.base-path  | ${project.reporting.outputDirectory}/results | directory, witch contains, or will contains generated reports |
| lastDirName      | allure-utils-plugin.report.lastDir    | last                                         | relative path from outputDirectory to last report             |

#### algorithm

1. generate uniq name (random UUID)
2. process all history files in last report
    1. generate relative (absolute) url to new report location (the uniq name from previous step)
    2. add this url to:
        1. history-trend.json
        2. history.json
        3. categories-trend.json
        4. duration-trend.json
        5. retry-trend.json
3. move processed history directory to allure-results
4. rename directory with last update to uniq name (name from 1 step)

### remove-old

#### parameters

| parameter       | property name                         | default value                                | description                                              |
|-----------------|---------------------------------------|----------------------------------------------|----------------------------------------------------------|
| outputDirectory | allure-utils-plugin.report.baseurl    | ${project.reporting.outputDirectory}/results | path to directory that contains generated allure reports |
| maxReports      | allure-utils-plugin.report.maxReports | 21                                           | max count of reports                                     |

#### algorithm

1. get sorted by creation date directories in reports directory
2. deletes last report while reports count grader than maxReports parameter

# bundledev

The BundleDEV project is a tool to collect information of many posts from several technical blogs.

The project imports, normalizes and stores this information from different RSS declared in an OPML file: [engineering_blogs.opml](.src/main/resources/opml/engineering_blogs.opml)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- [Java8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Maven 3.5.0](https://maven.apache.org/download.cgi)

You have to install Docker and Docker Compose:

- [Docker](https://docs.docker.com/installation/#installation)
- [Docker Compose](https://docs.docker.com/compose/install)

### Installing

* Clone source code from git

```
$ git clone git@bitbucket.org:bundledevteam/bundledev-feed-collector.git
```

* Build project with Maven

```
$ mvn clean install
```

* Run Docker images with Docker Compose from [docker-compose.yml](https://bitbucket.org/bundledevteam/docker/src/a27edab41b913933c0cb50ae6f846051d9bf4671/docker-compose.yml?at=master&fileviewer=file-view-default)

```
$ docker-compose up
```

* Run the application from the command line using:

```
$ ./mvnw spring-boot:run
```

## Built With

* [Spring Framework](https://spring.io/) - The framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds
* [PostgreSQL](https://www.postgresql.org/) - Object-relational database system
* [Elasticsearch](https://www.elastic.co/products/elasticsearch) - Search engine based on [Lucene](https://lucene.apache.org/core/)

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://bitbucket.org/bundledevteam/bundledev-feed-collector/downloads/?tab=tags).

## Authors

* **Iván Gutiérrez** - *Initial work* - [ivangrod](https://github.com/ivangrod)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used

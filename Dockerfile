FROM openjdk:8-jre

RUN apt-get update \
    && apt-get install --no-install-recommends -y \
	openjfx \
	unzip \
    && apt-get clean \
    && rm -f /var/lib/apt/lists/*_dists_*


ADD target/Think-1.0-SNAPSHOT.jar app.jar
EXPOSE 8010
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

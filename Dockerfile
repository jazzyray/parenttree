FROM centos
RUN \
  yum update -y && \
  yum install -y java-1.8.0-openjdk && \
  yum install -y wget && \
  mkdir -p /data/parent-tree-api \
  mkdir -p /data/parent-tree-api/work \
  mkdir -p /data/parent-tree-api/target

WORKDIR data/parent-tree-api

COPY start.sh /data/parent-tree-api
COPY target/parent-tree-0.0.1-SNAPSHOT.jar /data/parent-tree-api/target
COPY parent-tree-configuration.yml /data/parent-tree-api

EXPOSE 9101
EXPOSE 9103

ENV JAVA_HOME /usr/lib/jvm/jre-1.8.0-openjdk
CMD /data/parent-tree-api/start.sh

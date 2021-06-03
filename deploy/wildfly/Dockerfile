FROM jboss/wildfly
ADD ./*.war /opt/jboss/wildfly/standalone/deployments/
ARG PROFILE="default"
ENV JAVA_OPTS="-Dspring.profiles.active=$PROFILE"

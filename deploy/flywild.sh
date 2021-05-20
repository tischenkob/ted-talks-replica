USER=${1:-265063}
DIRECTORY=wildfly/standalone/deployments
WAR="../build/libs/*.war"
scp -P 2222 $WAR s"${USER}"@se.ifmo.ru:~/$DIRECTORY/

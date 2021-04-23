USER=${1:-265063}
PORT=${2:-9094}
DIRECTORY=ted
FILES=(../build/libs/*.jar)
JAR=$(basename "${FILES[0]}")
mv ../build/libs/"${JAR}" ./
echo "java18 -Dspring.profiles.active=helios -jar ${JAR}" >>start.sh
scp -P 2222 ./* s"${USER}"@se.ifmo.ru:~/$DIRECTORY/
rm "$JAR" start.sh
ssh -p 2222 s"$USER"@se.ifmo.ru -L "$PORT":localPORT:"$PORT"

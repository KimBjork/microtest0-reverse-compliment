#!/bin/sh
rm build/libs/function.zip
rm build/libs/reverseCompliment-1.0-SNAPSHOT
rm build/libs/reverseCompliment-1.0-SNAPSHOT.jar

./gradlew shadowJar
/home/kimbjork/Downloads/graalvm-ce-java8-19.3.0/bin/native-image --no-server --no-fallback -jar build/libs/reverseCompliment-1.0-SNAPSHOT.jar
./reverseCompliment-1.0-SNAPSHOT
mv reverseCompliment-1.0-SNAPSHOT build/libs/reverseCompliment-1.0-SNAPSHOT
zip -j build/libs/function.zip build/libs/bootstrap build/libs/reverseCompliment-1.0-SNAPSHOT build/libs/fasta-1000000
aws lambda update-function-code --function-name microtest0-reverse-compliment-graal --zip-file fileb://build/libs/function.zip
aws lambda invoke --function-name microtest0-reverse-compliment-graal output.txt
cat output.txt

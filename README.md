# aws-kotlin-jvm-gradle

AWS Lambda with Kotlin, Java 17, and Gradle 8.7.

## build

`./gradlew clean build`

## deploy

`./gradlew deploy`

## compile tailwind styles

`npx tailwindcss build -i src\public\styles\tailwind.css -o dist\styles\styles.css`

`npx tailwindcss build -i src\public\styles\tailwind.css -o src\public\styles\styles.css`
test

create projectt

curl https://start.spring.io/starter.tgz \
       -d bootVersion=2.0.1.RELEASE \
       -d groupId=com.example \
       -d artifactId=hello-tkg \
       -d name=hello-tkg \
       -d dependencies=webflux,thymeleaf,actuator \
       -d baseDir=hello-tkg \
       -d packageName=com.example.hellotkg \
       -d applicationName=HelloTkgApplication | tar -xzvf -

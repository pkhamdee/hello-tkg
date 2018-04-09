curl https://start.spring.io/starter.tgz \
       -d bootVersion=2.0.1.RELEASE \
       -d groupId=com.example \
       -d artifactId=hello-pks \
       -d name=hello-pks \
       -d dependencies=webflux,thymeleaf,actuator \
       -d baseDir=hello-pks \
       -d packageName=com.example.hellopks \
       -d applicationName=HelloPksApplication | tar -xzvf -

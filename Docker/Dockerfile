# Stratux Military Tracker - Generalized Container 


FROM arm32v7/openjdk:11-jre-slim AS Build 

# Address issues with headless Docker installs of apt-get componments
RUN echo 'debconf debconf/frontend select Noninteractive' | debconf-set-selections
RUN apt-get update && apt-get install -y \
  git \
  maven \
  && rm -rf /var/lib/apt/lists/* && apt autoremove -y

RUN mkdir -p /app

# Build the project
RUN git clone https://github.com/IngeniiCode/Military-Aviation-Tracker.git
WORKDIR /Military-Aviation-Tracker
RUN mvn install -Dmaven.assembly.skip=false  
# copy the tar to app directory from where the Run stage will copy to it's context
RUN mv /Military-Aviation-Tracker/target/StratuxMilTracker*SNAPSHOT-jar-with-dependencies.jar /app/Military-Aviation-Tracker.jar 

#
# RESET and copy just the build jar from our first stage
#
FROM arm32v7/openjdk:11-jre-slim AS Run 
COPY --from=Build /app/Military-Aviation-Tracker.jar .

#
#  -ToDo-  setup configuration information into env. params for App
#

EXPOSE 9101-9105

CMD ["java","-jar","/Military-Aviation-Tracker.jar"]


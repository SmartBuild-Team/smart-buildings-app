path="${M2_REPO}/edu/episen/si/ing1/pds/backend-service/1.0-SNAPSHOT"
filejar="${path}/backend-service-1.0-SNAPSHOT-jar-with-dependencies.jar"
mainClass="edu.episen.si.ing1.pds.backend.serveur.BackendService"
exec java -classpath ${filejar} ${mainClass} ${*}
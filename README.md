# Create another Apace Beam project

mvn archetype:generate -DarchetypeGroupId=org.apache.beam -DarchetypeArtifactId=beam-sdks-java-maven-archetypes-examples
-DarchetypeVersion=2.46.0 -DgroupId=com.ursolutions.dataflow.beam -DartifactId=mike-dataflow-beam
-Dversion=\"0.1\" -Dpackage=com.ursolutions.dataflow.beam -DinteractiveMode=false
#!/bin/sh
mvn -U clean install 
$JBOSS_HOME/bin/jboss-cli.sh -c --file=target/scripts/removemodule.cli
$JBOSS_HOME/bin/jboss-cli.sh -c --file=target/scripts/installmodule.cli


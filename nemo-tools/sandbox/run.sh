#!/bin/sh -x

CONFDIR="./conf"
parse_args() {
    while test $# -gt 0; do
        case "$1" in
            "--confdir" ) shift; CONFDIR=$1 ;;
            "-c" ) shift; CONFDIR=$1 ;;
        esac
        shift
    done
}

parse_args $@

# Set paths
CTRL_HOME=`pwd`/`dirname $0`
LAUNCHER_PATH="${CTRL_HOME}"
LAUNCHER_MAIN="org.opendaylight.nemo.tool.sandbox.Main"

# Set JVM options
JVM_OPTS=""
#JVM_OPTS="$JVM_OPTS -server -d64"
#JVM_OPTS="$JVM_OPTS -Xmx2g -Xms2g -Xmn800m"
#JVM_OPTS="$JVM_OPTS -XX:+UseParallelGC -XX:+AggressiveOpts -XX:+UseFastAccessorMethods"
#JVM_OPTS="$JVM_OPTS -XX:MaxInlineSize=8192 -XX:FreqInlineSize=8192"
#JVM_OPTS="$JVM_OPTS -XX:CompileThreshold=1500 -XX:PreBlockSpin=8"
#JVM_OPTS="$JVM_OPTS -Dpython.security.respectJavaAccessibility=false"

CLASSPATH="$CONFDIR:.:$LAUNCHER_PATH/target/classes:$LAUNCHER_PATH/target/dependency/*"
java ${JVM_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n -cp $CLASSPATH $LAUNCHER_MAIN $@
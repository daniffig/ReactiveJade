SCRIPT_FILE=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT_FILE")
LIB_PATH="$SCRIPT_PATH/../lib"
JADE_JAR="$LIB_PATH/jade.jar"
REACTIVEJADE_JAR="$LIB_PATH/ReactiveJade.jar"
HARDWARESNIFFER_JAR="$LIB_PATH/HardwareSniffer.jar"

java -cp $JADE_JAR:$REACTIVEJADE_JAR:$HARDWARESNIFFER_JAR jade.Boot -gui -agents hsa0:hardwaresniffer.HardwareSnifferAgent

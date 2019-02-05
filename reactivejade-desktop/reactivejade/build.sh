SCRIPT_FILE=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT_FILE")
LIB_PATH="$SCRIPT_PATH/../lib"
JADE_JAR="$LIB_PATH/jade.jar"
CLASSES_PATH="$SCRIPT_PATH/classes"
SOURCE_PATH="$SCRIPT_PATH/src"
SOURCE_FILES="$SOURCE_PATH/*.java"

rm -rf $CLASSES_PATH
mkdir $CLASSES_PATH
javac -cp $JADE_JAR -d $CLASSES_PATH $SOURCE_FILES
jar -cvf $LIB_PATH/ReactiveJade.jar -C $CLASSES_PATH .

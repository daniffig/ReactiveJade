SCRIPT_FILE=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT_FILE")
LIB_PATH="$SCRIPT_PATH/../lib"
JADE_JAR="$LIB_PATH/jade.jar"
CLASSES_PATH="$SCRIPT_PATH/classes"
SOURCE_PATH="$SCRIPT_PATH/src"
SOURCE_FILES="$SOURCE_PATH/*.java"

ANDROID_LIB_PATH="$SCRIPT_PATH/../../android/app/libs"

REACTIVEJADE_JAR="$LIB_PATH/ReactiveJade.jar"
HARDWARESNIFFER_JAR="$LIB_PATH/HardwareSniffer.jar"

JVM_CLASSES_PATH="$CLASSES_PATH/jvm"
JVM_SOURCE_FILES="$SOURCE_PATH/jvm/*.java"
DVM_CLASSES_PATH="$CLASSES_PATH/dvm"
DVM_SOURCE_FILES="$SOURCE_PATH/dvm/*.java"

ANDROID_PATH="$SCRIPT_PATH/../../android/app"
ANDROID_SRC_HARDWARESNIFFER_PATH="$ANDROID_PATH/src/main/java/com/reactivejade/hardwaresniffer"
ANDROID_SRC_HARDWARESNIFFER_FILES="$ANDROID_SRC_HARDWARESNIFFER_PATH/*.java"

rm -rf $CLASSES_PATH
mkdir $CLASSES_PATH

mkdir $JVM_CLASSES_PATH
javac -cp $JADE_JAR:$REACTIVEJADE_JAR -d $JVM_CLASSES_PATH $SOURCE_FILES $JVM_SOURCE_FILES
jar -cf $HARDWARESNIFFER_JAR -C $JVM_CLASSES_PATH .

if ! [ -d $ANDROID_SRC_HARDWARESNIFFER_PATH ]
then
  mkdir $ANDROID_SRC_HARDWARESNIFFER_PATH
fi

rm -f $ANDROID_SRC_HARDWARESNIFFER_FILES
cp $SOURCE_FILES $ANDROID_SRC_HARDWARESNIFFER_PATH

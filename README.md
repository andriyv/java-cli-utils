# java-cli-utils
java command line utilities library


## The Options utility
The Options allows to handle java programm arguments. It parses String[] args, and allows access with has/get/list methods which also support automatic conversion to Boolean, Integer, Long, Float and String values.

~~~~
public void static main(String[] args) {
  Options options = new Options(args);
  
  if(option.has("A")) {
  }
  
  String argumentB = option.get("B", String.value);
  List<Integer> argumentsC = option.list("C", Integer.value);
}
~~~~

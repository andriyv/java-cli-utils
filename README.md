# java-cli-utils
java command line utilities library


## The Options utility
The Options allows to handle java programm arguments. It parses String[] args, and allows access with has/get/list methods which also support automatic conversion to Boolean, Integer, Long, Float and String values, as well as all types with public string constructor for example java.io.File

~~~~
public void static main(String[] args) {
  Options options = new Options(args);
  
  if(options.has("A")) {
  }
  
  String argumentB = options.get("B", String.value);
  List<Integer> argumentsC = options.list("C", Integer.value);
}
~~~~

### Using predicates
By query methods one can use predicates to filter results, for example to get only existing files

~~~~
public void static main(String[] args) {
  Options options = new Options(args);
  
  List<File> existingFiles = options.list("inputFile", File.class, (f) -> f.exists());
}
~~~~

### Using aliases
The option names could have aliases or short name, by using simple syntax it's possible to get values of multiple option names. For example consider option inputFile with aliases "input", "i"

~~~~
public void static main(String[] args) {
  Options options = new Options(args);
  
  List<File> files = options.list("i?inputFile?input", File.class);
}
~~~~
s
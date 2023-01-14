import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;

import static org.python.Python_h.*;

import org.python.*;

/**
 * Run a Python Script in a Java Application
 *
 * Run the following command to generate Java bindings for Python.h:
 *
 * jextract -l <absolute path of Python shared library> \
 *   --output classes \
 *   -I <directory containing Python header files> \
 *   -t org.python <absolute path of Python.h>
 *
 * compile:
 * javac --enable-preview -source 19 -classpath classes PythonMain.java
 * <p>
 * run:
 * java -cp classes:. -Djava.library.path=<location of Python shared library> PythonMain
 */
public class PythonMain {

    public static void main(String[] args) {
        String myString = "Hello world!";
        String script = """
                string = "%s"
                print(string, ': ', len(string), sep='')
                """.formatted(myString).stripIndent();

        Py_Initialize();

        try (MemorySession session = MemorySession.openConfined()) {
            MemorySegment nativeString = session.allocateUtf8String(script);
            PyRun_SimpleStringFlags(
                    nativeString,
                    MemoryAddress.NULL);
            Py_Finalize();
        }
        Py_Exit(0);
    }
}
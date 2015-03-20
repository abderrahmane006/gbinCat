package adam.gaia;

import gaia.cu1.mdb.cu3.auxdata.igsl.dm.IgslSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hal on 19/03/15.
 */
public class ExtractGetter {
    public static void main(String... args) {
        List<String> methodNames = new ArrayList<>();
        Method[] allMethods = IgslSource.class.getDeclaredMethods();
        for (Method m : allMethods) {
            String methodName = m.getName();
            if (methodName.startsWith("get")) {
                String attribute = methodName.substring(3);
                methodNames.add(attribute);
            }
        }
        Collections.sort(methodNames);
//        for (String methodName : methodNames) {
//            System.out.println(" * " + methodName);
//        }
        for (String methodName : methodNames) {
            System.out.println("if (attribute.equals(\"" + methodName + "\")) {");
            System.out.println("    outputTuple.setAttribute(attributeIdx, String.valueOf(igslData.get" + methodName + "()));");
            System.out.println("}");
            System.out.println("else");
        }
    }
}
